const execute = require('./DB');
const uuidv4 = require('uuid/v4');

function createUUID(public_key){
   let uuid = uuidv4();
    const baseQuery = 'INSERT encryption(uuid, public_key) VALUES(?,?)';
        + ' VALUES(?, ?, ?, ?, ?, ?, ?)';
    execute(baseQuery, [uuid, public_key], (response, err) => {
        if (err) {
            return "error";
        }
        else {
            return uuid;
        }
    });
}

module.exports = {
    registerCostumer(name, username, password, nif, ccType, ccNumber, ccValidity, balance, pubKey, callback){
        const baseQuery = 'INSERT costumer(name, nif, username, password, card_type, card_number, card_validity, balance, public_key)'
            + ' VALUES(?, ?, ?, ?, ?, ?, 0, ?)';
        execute(baseQuery, [name, nif, username, password, ccType, ccNumber, ccValidity, pubKey], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                let uuid = createUUID(pubKey);
                callback(uuid);
            }
        });
    },

};