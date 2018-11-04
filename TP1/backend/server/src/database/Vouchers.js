const execute = require('./DB');

module.exports = {

    createVoucher(ticket_id, type, product, callback){
        execute("BEGIN", [], (resBegin, errBegin) => {
            if (errBegin) {
                callback(null, errBegin);
                return;
            }
            let baseQuery = 'INSERT INTO voucher(is_used, type, product_id) WHERE (FALSE, $1, $2)';
            execute(baseQuery, [type, product], (response, err) => {
                if (err) {
                    execute("ROLLBACK", [], () => callback(null, err));
                }
                else {
                    baseQuery = 'SELECT id FROM voucher ORDER BY id DESC LIMIT 1';
                    execute(baseQuery, [], (response, err) => {
                        if (err) {
                            execute("ROLLBACK", [], () => callback(null, err));
                        }
                        else {
                            baseQuery = 'INSERT INTO voucher_ticket(voucher_id, ticket_id) WHERE ($1, $2)';
                            execute(baseQuery, [response.rows[0].id, ticket_id], (response, err) => {
                                if (err) {
                                    execute("ROLLBACK", [], () => callback(null, err));
                                }
                                else{
                                    callback(response);
                                }
                            });
                        }
                    });
                }
            });
        });
    },

    getMyVouchers(customer_id, callback){
        const baseQuery = 'SELECT voucher.id, voucher.type, voucher.is_used FROM customer, ticket, voucher, voucher_ticket'
            + ' WHERE customer.id = $1 AND ticket.customer_id = customer.id AND voucher_ticket.ticket_id = ticket.id'
            + ' AND voucher.id = voucher_ticket.voucher.id';
        execute(baseQuery, [customer_id], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(response);
            }
        });
    },

    getMyVouchersByStatus(customer_id, status, callback){
        const baseQuery = 'SELECT voucher.id, voucher.type, voucher.is_used FROM customer, ticket, voucher, voucher_ticket'
            + ' WHERE customer.id = $1 AND ticket.customer_id = customer.id AND voucher_ticket.ticket_id = ticket.id'
            + ' AND voucher.id = voucher_ticket.voucher.id AND voucher.is_used = ?';
        execute(baseQuery, [customer_id, status], (response, err) => {
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
            + ' WHERE voucher.id = $1 AND cafeteria_product.id = voucher.product_id';
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
        const baseQuery = 'UPDATE voucher SET is_used = TRUE WHERE id = $1' ;
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