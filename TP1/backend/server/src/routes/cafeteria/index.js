const express = require('express');

const cafeteriaRoute = require('./cafeteriaRoute');
const vouchersRoute = require('./voucherRoute');

const router = express.Router();

router.use('', cafeteriaRoute);
router.use('/vouchers', vouchersRoute);

module.exports = router;
