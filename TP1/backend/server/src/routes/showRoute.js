const express = require('express');

const router = express.Router();

router.get('/', (req, res) => {
    console.log('Cannot see shows (yet)');
    res.status(501).send();
});

module.exports = router;