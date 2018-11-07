const crypto = require('crypto');

const verify = crypto.createVerify('RSA-SHA256');
const execute = require('./DB');


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
      }
      else {
        callback(response);
      }
    });
  },

  getOrders(callback) {
    const baseQuery = 'SELECT id, date FROM cafeteria_order ORDER BY date';
    execute(baseQuery, [], (response, err) => {
      if (err) {
        callback(null, err);
      }
      else {
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
      }
      else {
        callback(response);
      }
    });
  },

  getOrder(id, callback) {
    const baseQuery = 'SELECT id, date FROM cafeteria_order WHERE id = $1';
    execute(baseQuery, [id], (response, err) => {
      if (err) {
        callback(null, err);
      }
      else {
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
      }
      else {
        callback(response);
      }
    });
  },

  getOrderVouchers(id, callback) {
    const baseQuery = 'SELECT voucher_id FROM cafeteria_order_product WHERE order_id = $1';
    execute(baseQuery, [id], (response, err) => {
      if (err) {
        callback(null, err);
      }
      else {
        callback(response);
      }
    });
  },

  getProductPrice(id, callback) {
    const baseQuery = 'SELECT price FROM cafeteria_product WHERE name = $1';
    execute(baseQuery, [id], (response, err) => {
      if (err) {
        callback(null, err);
      }
      else {
        callback(response.rows[0]);
      }
    });
  },

  verifyOrderSignature(user_id, signature, message, callback) {
    const baseQuery = 'SELECT public_key FROM customer WHERE id = $1';
    execute(baseQuery, [user_id], (response, err) => {
      if (err) {
        callback(null, err);
      }
      else {
        const publicKey = response.rows[0].public_key;
        verify.write(message);
        verify.end();

        callback(verify.verify(publicKey, signature));
      }
    });
  }
};