const express = require('express');

const router = express.Router();

router.put('/', (req, res) => {
    console.log('Cannot edit profile (yet)');
    res.status(501).send();
});

module.exports = router;