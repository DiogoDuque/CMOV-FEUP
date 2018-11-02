const execute = require('./DB');

module.exports = {

    createVoucher(ticket_id, type, product, callback){
        let baseQuery = 'INSERT INTO voucher(is_used, type, product_id) WHERE (FALSE, ?, ?)';
        execute(baseQuery, [type, product], (response, err) => {
            if(err) {
                callback(null, err);
            }
            else {
                baseQuery = 'SELECT id FROM voucher ORDER BY id DESC LIMIT 1';
                execute(baseQuery, [], (response, err) => {
                    if(err) {
                        callback(null, err);
                    }
                    else {
                        baseQuery = 'INSERT INTO voucher_ticket(voucher_id, ticket_id) WHERE (?, ?)';
                        execute(baseQuery, [response[0].id, ticket_id], (response, err) => {
                            if(err) {
                                callback(null, err);
                            }
                            else {
                                callback(response);
                            }
                        });
                    }
                });
            }
        });
    },

    getMyVouchers(username, callback){
        const baseQuery = 'SELECT voucher.id, voucher.type, voucher.is_used FROM costumer, ticket, voucher, voucher_ticket'
            + ' WHERE costumer.username = ? AND ticket.costumer_id = costumer.id AND voucher_ticket.ticket_id = ticket.id'
            + ' AND voucher.id = voucher_ticket.voucher.id';
        execute(baseQuery, [username], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(response);
            }
        });
    },

    getMyVouchersByStatus(username, status, callback){
        const baseQuery = 'SELECT voucher.id, voucher.type, voucher.is_used FROM costumer, ticket, voucher, voucher_ticket'
            + ' WHERE costumer.username = ? AND ticket.costumer_id = costumer.id AND voucher_ticket.ticket_id = ticket.id'
            + ' AND voucher.id = voucher_ticket.voucher.id AND voucher.is_used = ?';
        execute(baseQuery, [username, status], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(response);
            }
        });
    },

    getVoucherInfo(id, callback){
        const baseQuery = 'SELECT cafeteria_product.name, cafeteria_product.price FROM voucher, cafeteria_product'
            + ' WHERE voucher.id = ? AND cafeteria_product.id = voucher.product_id';
        execute(baseQuery, [id], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(response);
            }
        });
    },

    changeStatus(id, callback){
        const baseQuery = 'UPDATE voucher SET is_used = TRUE WHERE id = ?' ;
        execute(baseQuery, [id], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(response);
            }
        });
    },

};