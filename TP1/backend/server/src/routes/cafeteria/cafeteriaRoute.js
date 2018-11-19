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

router.get('/orders_not_validated', (req, res) => {
  Query.getOrdersNotValidated((result, err) => {
    if (result) {
      res.status(200).send(`{orders:${JSON.stringify(result.rows)}}`);
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

router.put('/verify', (req, res) => {
  const { obj, signature } = req.body;
  console.log(`verify: ${JSON.stringify(obj)}`);

  Query.verifyOrderSignature(signature, obj, (result, err) => {
    if (result) {
      res.status(200).send(`{cost:${result}}`);
    } else {
      res.status(400).send(err);
    }
  });
});


module.exports = router;
