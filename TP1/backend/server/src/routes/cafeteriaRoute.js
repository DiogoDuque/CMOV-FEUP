const express = require('express');
const Query = require('../database/Cafeteria');

const router = express.Router();

router.get('/orders', (req, res) => {
    const { date} = req.body;
    Query.getOrders(date, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.get('/orders_costumer', (req, res) => {
    const { id, is_used} = req.body;
    Query.getOrdersCostumer(id, is_used, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.get('/order', (req, res) => {
    const { id } = req.body;
    Query.getOrderProducts(id, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.get('/order_products', (req, res) => {
    const { id } = req.body;
    Query.getOrder(id, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.post('/make_order', (req, res) => {
    const { date, costumer } = req.body;
    Query.makeOrder(date, costumer, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.post('/add_products', (req, res) => {
    const { order, product, voucher } = req.body;
    Query.addProductToOrder(order, product, voucher, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

module.exports = router;