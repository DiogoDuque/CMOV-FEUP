const execute = require('./DB');

module.exports = {

    getProfileInfo(id, callback){
        const baseQuery = 'SELECT id, name, username, nif, password, balance FROM customer WHERE id = $1';
        execute(baseQuery, [id], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(res);
            }
        });
    },

    getCreditCard(id, callback){
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

    setMyProfile(id, name, username, nif, password, callback){
        const baseQuery = 'UPDATE customer SET name = ?, username = ?, nif = ?, password = ? WHERE id = $1';
        execute(baseQuery, [name, username, nif, password, id], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(response);
            }
        });
    },

    setCreditCard(id, card_type, card_number, card_validity, callback){
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

    setBalance(id, adding, callback){
        const baseQuery = 'UPDATE customer SET balance = balance + $1 WHERE id = $2';
        execute(baseQuery, [adding, id], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(response);
            }
        });
    }

};