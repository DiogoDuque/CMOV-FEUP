const express = require('express');
const Query = require('../database/Profile');

const router = express.Router();

router.get('/', (req, res) => {
  const { userId } = req.session;
  Query.getProfileInfo(userId, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

router.get('/user_id', (req, res) => {
  const { username } = req.query;
  Query.getProfileID(username, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

router.get('/credit_card', (req, res) => {
  const { userId } = req.session;
  Query.getCreditCard(userId, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

router.put('/', (req, res) => {
  const {
    name, username, nif, password,
  } = req.body;
  const { userId } = req.session;
  Query.setMyProfile(userId, name, username, nif, password, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

router.put('/credit_card', (req, res) => {
  const {
    cardType, cardNumber, cardValidity,
  } = req.body;
  const { userId } = req.session;
  Query.setCreditCard(userId, cardType, cardNumber, cardValidity, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

module.exports = router;
