const express = require('express');
const Query = require('../../database/Vouchers');

const router = express.Router();

router.get('/my_vouchers', (req, res) => {
  const { userId } = req.session;
  Query.getMyVouchers(userId, (result, err) => {
    if (result) {
      res.status(200).send(`{vouchers: ${JSON.stringify(result.rows)}}`);
    } else {
      res.status(400).send(err);
    }
  });
});

router.get('/my_vouchers_by_status', (req, res) => {
  const { status } = req.query;
  const { userId } = req.session;
  Query.getMyVouchersByStatus(userId, status, (result, err) => {
    if (result) {
      res.status(200).send(`{vouchers: ${JSON.stringify(result.rows)}}`);
    } else {
      res.status(400).send(err);
    }
  });
});

router.get('/voucher_info', (req, res) => {
  const { id } = req.query;
  Query.getVoucherInfo(id, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

router.post('/create_voucher', (req, res) => {
  const { ticket, type, product } = req.body;
  const { userId } = req.session;
  Query.createVoucher(userId, ticket, type, product, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

module.exports = router;
