const express = require('express');
const Query = require('../database/Vouchers');

const router = express.Router();

router.get('/my_vouchers', (req, res) => {
    const { customer_id } = req.query;
    Query.getMyVouchers(customer_id, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.get('/my_vouchers_by_status', (req, res) => {
    const { customer_id, status } = req.query;
    Query.getMyVouchersByStatus(customer_id, status, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.get('/voucher_info', (req, res) => {
    const { id } = req.query;
    Query.getVoucherInfo(id, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.post('/create_voucher', (req, res) => {
    const { ticket, type, product } = req.body;
    Query.createVoucher(ticket, type, product, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.put('/change_voucher', (req, res) => {
    const { id } = req.body;
    Query.changeStatus(id, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

module.exports = router;