const express = require('express');

const router = express.Router();

router.post('/login', (req, res) => {
    console.log(JSON.stringify(req.body));
    console.log('Cannot login (yet)');
    res.status(501).send();
});

router.post('/register', (req, res) => {
    console.log(JSON.stringify(req.body));
    console.log('Cannot register (yet)');
    res.status(501).send();
});

module.exports = router;