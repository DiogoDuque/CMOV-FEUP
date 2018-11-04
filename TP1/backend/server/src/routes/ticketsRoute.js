const express = require('express');
const Query = require('../database/Tickets');

const router = express.Router();

router.get('/', (req, res) => {
    const { id } = req.query;
    Query.getTicket(id, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.get('/used_tickets', (req, res) => {
    const { customer_id } = req.query;
    Query.getUsedTickets(customer_id, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.get('/not_used_tickets', (req, res) => {
    const { customer_id } = req.query;
    Query.getNotUsedTickets(customer_id, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});


router.get('/all_tickets', (req, res) => {
    const { customer_id } = req.query;
    Query.getAllTickets(customer_id, (result, err) => {
        if(result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.post('/', (req, res) => {
    const { userId } = req.session;
    const { showId } = req.body;
    place = Math.floor(Math.random() * 100) + 1;
    Query.buyTicket(userId, showId, place, (result, err) => {
        if(result) {
            res.status(200).send(JSON.stringify(result.rows[0]));
        } else {
            res.status(400).send(err);
        }
    });
});

module.exports = router;