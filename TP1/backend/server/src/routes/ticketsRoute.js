const express = require('express');
const Query = require('../database/Tickets');

const router = express.Router();

router.get('/', (req, res) => {
  const { id } = req.query;
  Query.getTicket(id, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

router.get('/used_tickets', (req, res) => {
  const { userId } = req.session;
  Query.getUsedTickets(userId, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

router.get('/not_used_tickets', (req, res) => {
  const { userId } = req.session;
  Query.getNotUsedTickets(userId, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});


router.get('/all_tickets', (req, res) => {
  const { userId } = req.session;
  Query.getAllTickets(userId, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

router.get('/check_tickets', (req, res) => {
    const { userId, showName, showDate, quantity } = req.session;
    Query.checkTicket(userId, showName, showDate, quantity (result, err) => {
        if (result) {
            res.status(200).send(result);
        } else {
            res.status(400).send(err);
        }
    });
});

router.post('/', (req, res) => {
  const { userId } = req.session;
  const { showId, quantity } = req.body;
  Query.buyTickets(userId, showId, quantity, (result, err) => {
    if (result) {
      res.status(200).send(`{result:${JSON.stringify(result)}}`);
    } else {
      res.status(400).send(err);
    }
  });
});

module.exports = router;
