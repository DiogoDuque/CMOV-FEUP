const express = require('express');
const Query = require('../../database/Cafeteria');

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

router.get('/verify', (req, res) => {
  const { obj, signature } = req.query;

  Query.verifyOrderSignature(signature, obj, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});


module.exports = router;
