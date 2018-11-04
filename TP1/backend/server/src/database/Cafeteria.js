const execute = require('./DB');

module.exports = {

    makeOrder(date, costumer, callback){
        const baseQuery = 'INSERT cafeteria_order(date, costumer_id) VALUES($1,$2)';
        execute(baseQuery, [date, costumer], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(res);
            }
        });
    },

    addProductToOrder(order, product, voucher, callback){
        const baseQuery = 'INSERT cafeteria_order_product(order_id, product_id, voucher_id) VALUES($1,$2,$3)';
        execute(baseQuery, [order, product, vocuher], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(res);
            }
        });
    },

    getOrders(date, callback){
        let baseQuery = "";
        if(date){
            baseQuery = 'SELECT cafeteria_order.id, cafeteria_order.date FROM cafeteria_order, cafeteria_order_product, voucher'
                + ' WHERE voucher.is_used = FALSE AND cafeteria_order_product.voucher_id = voucher.id AND cafeteria_order_product.order_id = cafeteria.id'
                + ' ORDER BY date';
            execute(baseQuery, [date], (response, err) => {
                if (err) {
                    callback(null, err);
                }
                else {
                    callback(res);
                }
            });
        }
        else{
            baseQuery = 'SELECT cafeteria_order.id, cafeteria_order.date FROM cafeteria_order, cafeteria_order_product, voucher'
                + ' WHERE voucher.is_used = FALSE AND cafeteria_order_product.voucher_id = voucher.id AND cafeteria_order_product.order_id = cafeteria.id';
            execute(baseQuery, [], (response, err) => {
                if (err) {
                    callback(null, err);
                }
                else {
                    callback(res);
                }
            });
        }
    },

    getOrdersCostumer(id, is_used, callback){
        const baseQuery = 'SELECT cafeteria_order.id, cafeteria_order.date FROM cafeteria_order, cafeteria_order_product, voucher'
                + ' WHERE cafeteria.customer_id = $1 AND voucher.is_used = $2 AND cafeteria_order_product.voucher_id = voucher.id'
                + ' AND cafeteria_order_product.order_id = cafeteria.id ORDER BY date';
        execute(baseQuery, [id, is_used], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(res);
            }
        });
    },

    getOrder(id, callback){
        const baseQuery = 'SELECT id, date FROM cafeteria_order WHERE id = $1';
        execute(baseQuery, [id], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(res);
            }
        });
    },

    getOrderProducts(id, callback){
        const baseQuery = 'SELECT cafeteria_order.id, cafeteria_product.name, cafeteria_order, product.voucher_id'
            + ' FROM cafeteria_order, cafeteria_product, cafeteria_order_product'
            + ' WHERE cafeteria_order.id = $1 AND cafeteria_order_product.order_id = cafeteria_order.id'
            + ' AND cafeteria_product = cafeteria_order_product.product.id' ;
        execute(baseQuery, [id], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(res);
            }
        });
    },
};