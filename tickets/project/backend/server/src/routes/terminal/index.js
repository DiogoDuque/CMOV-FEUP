const express = require('express');
const ticketsRoute = require('./ticketsRoute');

const router = express.Router();

router.use('', ticketsRoute);

module.exports = router;
