const execute = require('./DB');

module.exports = {

    createVoucher(ticket_id, type, callback){
        let baseQuery = '';
        let argsQuery = [];
        let product = '';
        let productId=-1;
        if(type == 'Discount') {
            baseQuery = 'INSERT INTO voucher(type) VALUES($1) RETURNING id';
            argsQuery = [type];
        } else {
            baseQuery = 'INSERT INTO voucher(type, product_id) VALUES ($1, $2) RETURNING id';
            product = Math.floor(Math.random() * 2) == 1 ? 'Coffee' : 'Popcorn';
            productId = product=='Coffee' ? 1 : 3;
            argsQuery = [type, productId];
        }
        execute(baseQuery, argsQuery, (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                baseQuery = 'INSERT INTO voucher_ticket(voucher_id, ticket_id) VALUES ($1, $2)';
                execute(baseQuery, [response.rows[0].id, ticket_id], (response, err) => {
                    if (err) {
                        callback(null, err);
                    } else {
                        const obj = {
                            type,
                            product,
                        };
                        callback(obj);
                    }
                });
            }
        });
    },

    getMyVouchers(customer_id, callback){
        const baseQuery = 'SELECT voucher.id, voucher.type, voucher.is_used FROM customer, ticket, voucher, voucher_ticket'
            + ' WHERE customer.id = $1 AND ticket.customer_id = customer.id AND voucher_ticket.ticket_id = ticket.id'
            + ' AND voucher.id = voucher_ticket.voucher_id';
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
            + ' AND voucher.id = voucher_ticket.voucher_id AND voucher.is_used = $2';
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
        execute("BEGIN", [], (resBegin, errBegin) => {
            if (errBegin) {
                callback(null, errBegin);
                return;
            }
            let baseQuery = 'SELECT type FROM voucher WHERE id = $1';
            execute(baseQuery, [id], (response, err) => {
                if (err) {
                    execute("ROLLBACK", [], () => callback(null, err));
                }
                else {
                    if(response.rows[0].type === "Free Product"){
                        baseQuery = 'SELECT cafeteria_product.name, cafeteria_product.price FROM voucher, cafeteria_product'
                            + ' WHERE voucher.id = $1 AND cafeteria_product.id = voucher.product_id';
                        execute(baseQuery, [], (response, err) => {
                            if (err) {
                                execute("ROLLBACK", [], () => callback(null, err));
                            }
                            else {
                                callback(response);

                            }
                        });
                    }
                    callback(response);
                }
            });
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