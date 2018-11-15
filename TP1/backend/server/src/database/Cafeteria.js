const NodeRSA = require('node-rsa');
const execute = require('./DB');

function validateVoucher(voucherId, productsOld, accumulatedDiscount, callback) {
  let discount = accumulatedDiscount;
  const products = productsOld;

  const getVouchersQuery = 'SELECT voucher.type, cafeteria_product.name FROM voucher LEFT JOIN '
    + 'cafeteria_product ON cafeteria_product.id = voucher.product_id WHERE voucher.id = $1';
  execute(getVouchersQuery, [voucherId], (voucherQRes, voucherQErr) => {
    if (voucherQErr) {
      callback(null, voucherQErr);
    } else {
      const { type, name } = voucherQRes.rows[0];
      if (type === 'Discount') {
        discount += 0.05;
      } else {
        for (let i = 0; i < products.length; i++) {
          const product = products[i];
          if (product.name === name) {
            if (!product.vouchersUsed) {
              product.vouchersUsed = [];
            }
            product.vouchersUsed.push(voucherId);
            break;
          }
        }
      }
      const useVouchersQuery = 'UPDATE voucher SET is_used = TRUE WHERE id = $1';
      execute(useVouchersQuery, [voucherId], (voucherUpdRes, voucherUpdErr) => {
        if (voucherUpdErr) {
          callback(null, voucherUpdErr);
        } else {
          callback({ discount, products });
        }
      });
    }
  });
}

function validateProduct(orderId, product, cost = 0, callback) {
  const hasVoucher = product.vouchersUsed && (product.vouchersUsed.length > 0);
  const insertQuery = `INSERT INTO cafeteria_order_product (order_id, product_id${hasVoucher ? ', voucher_id' : ''}) `
    + `(SELECT $1, name${hasVoucher ? ', $3' : ''} FROM cafeteria_product WHERE name = $2)`;
  const insArgs = hasVoucher ? [orderId, product.name, product.vouchersUsed.pop()] : [orderId, product.name];
  execute(insertQuery, insArgs, (insRes, insErr) => {
    if (insErr) {
      callback(insErr);
    } else {
      const newProd = product;
      newProd.quantity--;
      if (hasVoucher) {
        if (newProd.quantity > 0) {
          validateProduct(orderId, newProd, cost, callback);
        } else callback(cost);
      } else {
        const selQuery = 'SELECT price from cafeteria_product WHERE name = $1';
        execute(selQuery, [product.name], (selRes, selErr) => {
          if (selErr) {
            callback(null, selErr);
          } else if (newProd.quantity > 0) {
            validateProduct(orderId, newProd, cost + selRes.rows[0].price, callback);
          } else callback(cost);
        });
      }
    }
  });
}

function calculateAndValidateOrder(orderId, productsOld, vouchersIds, accumulatedDiscount = 0, accumulatedCost = 0, callback) {

  console.log('1. calcAndVal');
  if (vouchersIds && vouchersIds.length > 0) {
    console.log('2. calcAndVal - vouchers');
    const voucherId = vouchersIds.pop();
    validateVoucher(voucherId, productsOld, (voucherRes, voucherErr) => {
      console.log('2.9 calcAndVal - vouchers final');
      if (voucherErr) {
        callback(null, voucherErr);
      } else {
        const { discount, products } = voucherRes.rows[0];
        calculateAndValidateOrder(orderId, products, vouchersIds, accumulatedDiscount + discount, callback);
      }
    });
  }
  console.log('3. passed vouchers');

  if (productsOld.length > 0) {
    console.log('4. calcAndVal - prod');
    const product = productsOld.pop();
    validateProduct(orderId, product, (productRes, productErr) => {
      console.log('4.9 calcAndVal - prod final');
      if (productErr) {
        callback(null, productErr);
      } else {
        const cost = productRes;
        calculateAndValidateOrder(orderId, productsOld, vouchersIds, accumulatedDiscount, accumulatedCost + cost, callback);
      }
    });
  }
  console.log('5. passed prod');

  // make final calculations and validate order
  const finalCost = accumulatedCost * (1 - accumulatedDiscount);
  const validateQuery = 'UPDATE cafeteria_order SET is_validated = TRUE WHERE cafeteria_order = $1';
  execute(validateQuery, [orderId], (validRes, validErr) => {
    if (validErr) {
      callback(null, validErr);
    } else callback(finalCost);
  });
}

module.exports = {

  makeOrder(date, costumer, callback) {
    const baseQuery = 'INSERT INTO cafeteria_order(date, customer_id) VALUES($1,$2) RETURNING id';
    execute(baseQuery, [date, costumer], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        callback(response.rows[0]);
      }
    });
  },

  addProductToOrder(order, product, voucher, callback) {
    const baseQuery = 'INSERT INTO cafeteria_order_product(order_id, product_id, voucher_id) VALUES($1,$2,$3)';
    execute(baseQuery, [order, product, voucher], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        callback(response);
      }
    });
  },

  getOrders(callback) {
    const baseQuery = 'SELECT id, date FROM cafeteria_order ORDER BY date';
    execute(baseQuery, [], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        callback(response);
      }
    });
  },

  getOrdersNotValidated(callback) {
    const baseQuery = 'SELECT id, date, price FROM cafeteria_order WHERE is_validated = FALSE ORDER BY date DESC';
    execute(baseQuery, [], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        callback(response);
      }
    });
  },

  getOrdersValidated(id, callback) {
    const baseQuery = 'SELECT id, date, price FROM cafeteria_order WHERE customer_id = $1 AND is_validated = TRUE ORDER BY date DESC';
    execute(baseQuery, [id], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        callback(response);
      }
    });
  },

  getOrdersNotValidated(id, callback) {
    const baseQuery = 'SELECT id, date FROM cafeteria_order WHERE customer_id = $1 AND is_validated = FALSE ORDER BY date';
    execute(baseQuery, [id], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        callback(response);
      }
    });
  },

  getOrdersCostumer(id, callback) {
    const baseQuery = 'SELECT id, date FROM cafeteria_order'
      + ' WHERE customer_id = $1 ORDER BY date';
    execute(baseQuery, [id], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        callback(response);
      }
    });
  },

  getOrder(id, callback) {
    const baseQuery = 'SELECT id, date FROM cafeteria_order WHERE id = $1';
    execute(baseQuery, [id], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        callback(response);
      }
    });
  },

  getOrderProducts(id, callback) {
    const baseQuery = 'SELECT cafeteria_order.id, cafeteria_product.name'
      + ' FROM cafeteria_order, cafeteria_product, cafeteria_order_product'
      + ' WHERE cafeteria_order.id = $1 AND cafeteria_order_product.order_id = cafeteria_order.id'
      + ' AND cafeteria_product.id = cafeteria_order_product.product_id';
    execute(baseQuery, [id], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        callback(response);
      }
    });
  },

  getOrderVouchers(id, callback) {
    const baseQuery = 'SELECT voucher_id FROM cafeteria_order_product WHERE order_id = $1';
    execute(baseQuery, [id], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        callback(response);
      }
    });
  },

  getProductPrice(id, callback) {
    const baseQuery = 'SELECT price FROM cafeteria_product WHERE name = $1';
    execute(baseQuery, [id], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        callback(response.rows[0]);
      }
    });
  },

  verifyOrderSignature(signature, message, callback) {
    const { userId, orderId, products, vouchersIds } = message;
    const baseQuery = 'SELECT public_key FROM customer WHERE id = $1';
    execute(baseQuery, [userId], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        const publicKey = response.rows[0].public_key;
        console.log(`0. finished query. key=${publicKey}`);

        const key = new NodeRSA();
        key.importKey(publicKey, 'public');
        console.log('0. everything ok here 2');
        const buffer = Buffer.from(message, 'base64');
        console.log('0. everything ok here 3');
        const res = key.verify(buffer, signature, 'buffer', 'base64');
        console.log(res);
        if (res) {
          console.log('0. signature verified');
          calculateAndValidateOrder(orderId, products, vouchersIds, callback);
        } else {
          console.log('0. FAIL');
          callback(false);
        }
      }
    });
  },
};
