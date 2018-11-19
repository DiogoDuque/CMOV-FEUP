const express = require('express');

const cafeteriaRoute = require('./cafeteriaRoute');
const showRoute = require('./showRoute');
const profileRoute = require('./profileRoute');
const ticketsRoute = require('./ticketsRoute');
const vouchersRoute = require('./voucherRoute');

const router = express.Router();

router.use('/cafeteria', cafeteriaRoute);
router.use('/show', showRoute);
router.use('/tickets', ticketsRoute);
router.use('/vouchers', vouchersRoute);
router.use('/profile', profileRoute);

module.exports = router;
