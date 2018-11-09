const express = require('express');
const Query = require('../../database/Vouchers');

const router = express.Router();

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