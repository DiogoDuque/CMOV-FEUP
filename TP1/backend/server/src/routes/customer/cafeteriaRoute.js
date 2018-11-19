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

router.get('/orders_customer_validated', (req, res) => {
  const { userId } = req.session;
  Query.getOrdersValidated(userId, (result, err) => {
    if (result) {
      res.status(200).send(`{orders:${JSON.stringify(result.rows)}}`);
    } else {
      res.status(400).send(err);
    }
  });
});

router.get('/orders_costumer_not_validated', (req, res) => {
  const { userId } = req.session;
  Query.getOrdersNotValidated(userId, (result, err) => {
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
      res.status(200).send(JSON.stringify(result));
    } else {
      res.status(400).send(err);
    }
  });
});



router.get('is_order_validated', (req, res) => {
  const { orderId } = req.query;
  Query.isOrderValidated(orderId, (qRes, qErr) => {
    if(qErr) {
      res.status(400).send(qErr);
    } else {
      res.status(200).send(`{result: ${qRes}}`);
    }
  });
});

module.exports = router;
