const NodeRSA = require('node-rsa');
const execute = require('./DB');
const Profile = require('./Profile');

function validateVoucher(voucherId, productsOld, accumulatedDiscount = 0, callback) {
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
    + `(SELECT $1, id${hasVoucher ? `, ${product.vouchersUsed.pop()}` : ''} FROM cafeteria_product WHERE name = $2)`;
  const insArgs = [orderId, product.name];
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
          let { price } = selRes.rows[0];
          price = parseFloat(price);
          if (selErr) {
            callback(null, selErr);
          } else if (newProd.quantity > 0) {
            validateProduct(orderId, newProd, cost + price, callback);
          } else callback(cost + price);
        });
      }
    }
  });
}

function calculateAndValidateOrder(orderId, productsOld, vouchersIds, accumulatedDiscount = 0, accumulatedCost = 0, callback) {
  // console.log(`cost: ${accumulatedCost}, discount: ${accumulatedDiscount}, products: ${JSON.stringify(productsOld)}`);
  if (vouchersIds && vouchersIds.length > 0) {
    const voucherId = vouchersIds.pop();
    validateVoucher(voucherId, productsOld, 0, (voucherRes, voucherErr) => {
      if (voucherErr) {
        callback(null, voucherErr);
      } else {
        const { discount, products } = voucherRes;
        calculateAndValidateOrder(orderId, products, vouchersIds, accumulatedDiscount + discount, accumulatedCost, callback);
      }
    });
    return;
  }

  if (productsOld.length > 0) {
    const product = productsOld.pop();
    validateProduct(orderId, product, 0, (productRes, productErr) => {
      if (productErr) {
        callback(null, productErr);
      } else {
        const cost = productRes;
        calculateAndValidateOrder(orderId, productsOld, vouchersIds, accumulatedDiscount, accumulatedCost + cost, callback);
      }
    });
    return;
  }

  // make final calculations and validate order
  const finalCost = accumulatedCost * (1 - accumulatedDiscount);
  const validateQuery = 'UPDATE cafeteria_order SET is_validated = TRUE, price = $2 WHERE id = $1';
  execute(validateQuery, [orderId, finalCost], (validRes, validErr) => {
    if (validErr) {
      callback(null, validErr);
    } else {
      callback(finalCost);
    }
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

        const key = new NodeRSA();
        key.importKey(publicKey, 'public');
        const buffer = Buffer.from(signature, 'base64');
        const res = key.verify(message, buffer, 'binary');
        if (res) {
          calculateAndValidateOrder(orderId, products, vouchersIds, 0, 0, (calcRes, calcErr) => {
            if(calcErr) {
              callback(null, calcErr);
            } else {
              Profile.setBalance(userId, calcRes, (balanceRes, balanceErr) => {
                if (balanceErr) {
                  callback(null, balanceErr);
                } else callback(calcRes);
              });
            }
          });
        } else {
          callback(false);
        }
      }
    });
  },

  isOrderValidated(orderId, callback) {
    const baseQuery = 'SELECT is_validated FROM cafeteria_order WHERE id = $1';
    execute(baseQuery, [orderId], (res, err) => {
      if(err) {
        callback(null, err);
      } else {
        callback(res.rows[0].is_validated);
      }
    });
  },
};
