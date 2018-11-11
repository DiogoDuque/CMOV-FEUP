const bcrypt = require('bcrypt-nodejs');
const uuidv4 = require('uuid/v4');
const execute = require('./DB');

function createUUID(public_key, callback) {
  const uuid = uuidv4();
  const baseQuery = 'INSERT INTO encryption(uuid, public_key) VALUES($1,$2)';
  execute(baseQuery, [uuid, public_key], (response, err) => {
    if (err) {
      execute('ROLLBACK', [], () => callback(null, err));
    } else {
      execute('COMMIT', [], () => callback(uuid));
    }
  });
}

// function used for debugging, and a starting point for create login
function showHash(password, callback) {
  bcrypt.genSalt(10, (saltErr, salt) => {
    if (saltErr) {
      callback(null);
    } else {
      bcrypt.hash(password, salt, null, (err, res) => {
        console.log('Password mismatch!');
        console.log(`Error: ${err}`);
        console.log(`Hash for the wrong password is ${res}`);
        console.log();
        callback(false);
      });
    }
  });
}

module.exports = {
  register(name, nif, username, password, cardType, cardNumber, cardValidity, publicKey, callback) {
    execute('BEGIN', [], (resBegin, errBegin) => {
      if (errBegin) {
        callback(null, errBegin);
        return;
      }
      const baseQuery = 'INSERT INTO customer(name, nif, username, password, card_type, '
        + 'card_number, card_validity, public_key) VALUES($1, $2, $3, $4, $5, $6, $7, $8)';
      execute(baseQuery, [name, nif, username, password, cardType, cardNumber, cardValidity, publicKey],
        (response, err) => {
          if (err) {
            execute('ROLLBACK', [], () => callback(null, err));
          } else {
            createUUID(publicKey, callback);
          }
        });
    });
  },


  /**
   * Looks for an existing pair user-pass.
   * @param {object} username username.
   * @param {Function} password password.
   * @param {function} callback callback for the executed command.
   */
  checkIfLoginExists(username, password, callback) {
    const baseQuery = 'SELECT password, id FROM customer WHERE username = $1';
    execute(baseQuery, [username], (response, err) => {
      if (err) {
        callback(null, err);
      } else if (response && response.rows.length === 1) {
        bcrypt.compare(password, response.rows[0].password, (err, res) => {
          if (err) {
            callback(null, err);
          } else if (res) {
            callback(response.rows[0].id);
          } else {
            showHash(password, callback);
          }
        });
      } else {
        showHash(password, callback);
      }
    });
  },
};
