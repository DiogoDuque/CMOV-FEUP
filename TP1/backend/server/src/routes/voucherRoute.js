const express = require('express');
const Query = require('../database/Vouchers');

const router = express.Router();

router.get('/my_vouchers', (req, res) => {
    const { costumer_id } = req.body;
    Query.getMyVouchers(costumer_id, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.get('/my_vouchers_by_status', (req, res) => {
    const { costumer_id, status } = req.body;
    Query.getMyVouchersByStatus(costumer_id, status, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.get('/voucher_info', (req, res) => {
    const { id } = req.body;
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