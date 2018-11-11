const express = require('express');

const cafeteriaRoute = require('./cafeteriaRoute');
const vouchersRoute = require('./voucherRoute');

const router = express.Router();

router.use('/vouchers', vouchersRoute);
router.use('', cafeteriaRoute);

module.exports = router;
