const express = require('express');
const Query = require('../database/Tickets');

const router = express.Router();

router.get('/', (req, res) => {
    const { id } = req.body;
    Query.getTicket(id, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.post('/buy_ticket', (req, res) => {
    const { user, show, place } = req.body;
    Query.buyTicket(user, show, place, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

module.exports = router;