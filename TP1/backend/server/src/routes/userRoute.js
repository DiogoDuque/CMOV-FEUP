const express = require('express');

const router = express.Router();

router.post('/register', (req, res) => {
    console.log('Cannot register (yet)');
    res.status(501).send();
});

router.post('/login', (req, res) => {
    console.log('Cannot login (yet)');
    res.status(501).send();
});

router.put('/', (req, res) => {
    console.log('Cannot edit profile (yet)');
    res.status(501).send();
});

module.exports = router;