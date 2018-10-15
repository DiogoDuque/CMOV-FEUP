const express = require('express');

const router = express.Router();

router.get('/', (req, res) => {
    console.log('Cannot see orders (yet)');
    res.status(501).send();
});

router.post('/', (req, res) => {
    console.log('Cannot issue orders (yet)');
    res.status(501).send();
});

module.exports = router;