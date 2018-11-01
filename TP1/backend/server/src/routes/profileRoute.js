const express = require('express');
const Query = require('../database/Profile');

const router = express.Router();

router.get('/', (req, res) => {
    const { id } = req.body;
    Query.getProfileInfo(id, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.get('/credit_card', (req, res) => {
    const { id } = req.body;
    Query.getCreditCard(id, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.put('/', (req, res) => {
    const { id, name, username, nif, password } = req.body;
    Query.setMyProfile(id, name, username, nif, password, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.put('/credit_card', (req, res) => {
    const { id, card_type, card_number, card_validity } = req.body;
    Query.setCreditCard(id, card_type, card_number, card_validity, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

module.exports = router;