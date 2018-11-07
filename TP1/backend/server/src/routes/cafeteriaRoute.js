const express = require('express');
const Query = require('../database/Cafeteria');

const router = express.Router();

router.get('/orders', (req, res) => {
  Query.getOrders((result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

router.get('/orders_costumer', (req, res) => {
  const { userId } = req.session;
  Query.getOrdersCostumer(userId, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

router.get('/order', (req, res) => {
  const { id } = req.query;
  Query.getOrder(id, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

router.get('/order_products', (req, res) => {
  const { id } = req.query;
  Query.getOrderProducts(id, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

router.get('/order_vouchers', (req, res) => {
  const { id } = req.query;
  Query.getOrderVouchers(id, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

router.get('/product_price', (req, res) => {
  const { product } = req.query;
  Query.getProductPrice(product, (result, err) => {
    if (result) {
      res.status(200).send(JSON.stringify(result));
    } else {
      res.status(400).send(err);
    }
  });
});

router.post('/make_order', (req, res) => {
  const { date } = req.body;
  const { userId } = req.session;
  Query.makeOrder(date, userId, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

router.post('/add_products', (req, res) => {
  const { order, product, voucher } = req.body;
  Query.addProductToOrder(order, product, voucher, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

module.exports = router;