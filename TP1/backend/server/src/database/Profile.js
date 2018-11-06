const execute = require('./DB');
const Vouchers = require('./Vouchers');

module.exports = {

  getProfileInfo(id, callback) {
    const baseQuery = 'SELECT id, name, username, nif, password, balance FROM customer WHERE username = $1';
    execute(baseQuery, [id], (response, err) => {
      if (err) {
        callback(null, err);
      }
      else {
        callback(response);
      }
    });
  },

  getProfileID(username, callback) {
    const baseQuery = 'SELECT id FROM customer WHERE username = $1';
    execute(baseQuery, [username], (response, err) => {
      if (err) {
        callback(null, err);
      }
      else {
        callback(response.rows[0]);
      }
    });
  },

  getCreditCard(id, callback) {
    const baseQuery = 'SELECT card_type, card_number, card_validity FROM customer WHERE id = $1';
    execute(baseQuery, [id], (response, err) => {
      if (err) {
        callback(null, err);
      }
      else {
        callback(response);
      }
    });
  },

  setMyProfile(id, name, username, nif, password, callback) {
    const baseQuery = 'UPDATE customer SET name = $1, username = $2, nif = $3, password = $4 WHERE id = $5';
    execute(baseQuery, [name, username, nif, password, id], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        callback(response);
      }
    });
  },

  setCreditCard(id, card_type, card_number, card_validity, callback) {
    const baseQuery = 'UPDATE customer SET card_type = $1, card_number = $2, card_validity = $3 WHERE id = $4';
    execute(baseQuery, [card_type, card_number, card_validity, id], (response, err) => {
      if (err) {
        callback(null, err);
      }
      else {
        callback(response);
      }
    });
  },

  setBalance(id, adding, callback) {
    const baseQuery = 'UPDATE customer SET balance = balance + $1 WHERE id = $2 RETURNING balance';
    execute(baseQuery, [adding, id], (response, err) => {
      if (err) {
        callback(null, err);
      } else if ((adding - (parseInt(response.rows[0].balance, 10) % 100)) > 0) {
        Vouchers.createVoucher(id, null, 'Discount', (resV, errV) => {
          if(errV) {
            callback(null, errV);
          } else {
            callback({
              balance: response.rows[0].balance,
              ...resV,
            });
          }
        });
      } else callback(response);
    });
  },
};
