const execute = require('./DB');

module.exports = {

  createVoucher(userId, ticketId, type, callback) {
    let baseQuery = '';
    let argsQuery = [];
    let product = '';
    let productId = -1;
    if (type === 'Discount') {
      baseQuery = 'INSERT INTO voucher(type, customer_id) VALUES($1, $2) RETURNING id';
      argsQuery = [type, userId];
    } else {
      baseQuery = 'INSERT INTO voucher(type, product_id, customer_id) VALUES ($1, $2, $3) RETURNING id';
      product = Math.floor(Math.random() * 2) === 1 ? 'Coffee' : 'Popcorn';
      productId = product === 'Coffee' ? 1 : 3;
      argsQuery = [type, productId, userId];
    }
    execute(baseQuery, argsQuery, (response, err) => {
      if (err) {
        callback(null, err);
      } else if (type === 'Discount') {
        callback({ type });
      } else {
        baseQuery = 'INSERT INTO voucher_ticket(voucher_id, ticket_id) VALUES ($1, $2)';
        execute(baseQuery, [response.rows[0].id, ticketId], (res, err2) => {
          if (err2) {
            callback(null, err2);
          } else {
            callback({ type, product });
          }
        });
      }
    });
  },

  getMyVouchers(customerId, callback) {
    const baseQuery = 'SELECT voucher.id, voucher.type, voucher.is_used, cafeteria_product.name AS product '
      + 'FROM voucher LEFT JOIN cafeteria_product ON voucher.product_id = cafeteria_product.id '
      + 'WHERE voucher.customer_id = $1';
    execute(baseQuery, [customerId], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        callback(response);
      }
    });
  },

  getMyVouchersByStatus(customerId, status, callback) {
    const baseQuery = 'SELECT voucher.id, voucher.type, voucher.is_used, cafeteria_product.name AS product '
      + 'FROM voucher LEFT JOIN cafeteria_product ON voucher.product_id = cafeteria_product.id '
      + 'WHERE voucher.customer_id = $1 AND voucher.is_used = $2';
    execute(baseQuery, [customerId, status], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        callback(response);
      }
    });
  },

  getVoucherInfo(id, callback) {
    execute('BEGIN', [], (resBegin, errBegin) => {
      if (errBegin) {
        callback(null, errBegin);
        return;
      }
      let baseQuery = 'SELECT type FROM voucher WHERE id = $1';
      execute(baseQuery, [id], (response, err) => {
        if (err) {
          execute('ROLLBACK', [], () => callback(null, err));
        } else {
          if (response.rows[0].type === 'Free Product') {
            baseQuery = 'SELECT cafeteria_product.name, cafeteria_product.price FROM voucher, cafeteria_product'
              + ' WHERE voucher.id = $1 AND cafeteria_product.id = voucher.product_id';
            execute(baseQuery, [], (response, err) => {
              if (err) {
                execute('ROLLBACK', [], () => callback(null, err));
              } else {
                callback(response);
              }
            });
          }
          callback(response);
        }
      });
    });
  },

  changeStatus(id, callback) {
    const baseQuery = 'UPDATE voucher SET is_used = TRUE WHERE id = $1';
    execute(baseQuery, [id], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        callback(response);
      }
    });
  },

};
