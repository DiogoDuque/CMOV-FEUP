const execute = require('./DB');

module.exports = {

    makeOrder(date, costumer, callback){
        const baseQuery = 'INSERT cafeteria_order(date, costumer_id) VALUES(?, ?)';
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
        const baseQuery = 'INSERT cafeteria_order_product(order_id, product_id, voucher_id) VALUES(?, ?, ?)';
        execute(baseQuery, [order, product, vocuher], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(res);
            }
        });
    },

    getOrder(id, callback){
        const baseQuery = 'SELECT id, date FROM cafeteria_order WHERE id = ?';
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
        const baseQuery = 'SELECT cafeteria_order.id, cafeteria_product.name, cafeteria_order_produtc.voucher_id'
            + ' FROM cafeteria_order, cafeteria_product, cafeteria_order_product'
            + ' WHERE cafeteria_order.id = ? AND cafeteria_order_product.order_id = cafeteria_order.id'
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