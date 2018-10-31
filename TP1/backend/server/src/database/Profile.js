const execute = require('./DB');

module.exports = {

    getProfileInfo(id, callback){
        const baseQuery = 'SELECT id, name, username, nif, password FROM costumer WHERE costumer.id = ?';
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
        const baseQuery = 'SELECT card_type, card_number, card_validity FROM costumer WHERE costumer.id = ?';
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
        const baseQuery = 'UPDATE costumer SET name = ?, username = ?, nif = ?, password = ? WHERE id = ?';
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
        const baseQuery = 'UPDATE costumer SET card_type = ?, card_number = ?, card_validity = ? WHERE id = ?';
        execute(baseQuery, [card_type, card_number, card_validity, id], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(response);
            }
        });
    },

};