const express = require('express');
const passport = require('passport');
const bcrypt = require('bcrypt-nodejs');
const Query = require('../database/AuthQuery');

const router = express.Router();

// Just for checking if logged in
router.get('/', (req, res) => {
    if(req.isAuthenticated()) {
      res.status(200).send();
    } else res.status(401).send();
  });

router.post('/login', passport.authenticate('local'), (req, res) => {
    const { username, password } = req.body;
    Query.checkIfLoginExists(username, password, (result, err) => {
        if(result === true) {
            res.send("{result: true}");
        } else if(result === false) {
            res.status(401).send();
        } else {
            res.status(500).send();
        }
    });
});

router.post('/register', (req, res) => {
    const { name, nif, username, password, cardType, cardNumber, cardValidity } = req.body;
    if(!name) {
        res.status(400).send('Name not found in request');
        return;
    }
    if(!nif) {
        res.status(400).send('NIF not found in request');
        return;
    }
    if(!username) {
        res.status(400).send('Username not found in request');
        return;
    }
    if(!password) {
        res.status(400).send('password not found in request');
        return;
    }
    if(!cardType) {
        res.status(400).send('cardType not found in request');
        return;
    }
    if(!cardNumber) {
        res.status(400).send('cardNumber not found in request');
        return;
    }
    if(!cardValidity) {
        res.status(400).send('cardValidity not found in request');
        return;
    }

    const publicKey = 'sadljfsf'; //TODO

    bcrypt.genSalt(10, (saltErr, salt) => {
        if (saltErr) {
          res.status(500).send(saltErr);
        } else bcrypt.hash(password, salt, null, (err, hash) => {
          if(err) {
            res.status(500).send(err);
          } else {
              Query.register(name, nif, username, hash, cardType, cardNumber,
                            cardValidity, publicKey, (result, err) => {
                  if(result) {
                      res.status(200).send(result);
                  } else {
                      res.status(400).send(err);
                  }
              });
          }
        });
      });
});

module.exports = router;