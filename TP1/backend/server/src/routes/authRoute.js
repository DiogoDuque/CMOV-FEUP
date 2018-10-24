const express = require('express');
const passport = require('passport');
const Query = require('../database/AuthQuery');

const router = express.Router();

// Just for checking if logged in
router.get('/', (req, res) => {
    if(req.isAuthenticated()) {
      res.status(200).send();
    } else res.status(401).send();
  });

router.post('/login', passport.authenticate('local'), (req, res) => {
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