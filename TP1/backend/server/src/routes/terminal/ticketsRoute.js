const express = require('express');
const Query = require('../../database/Tickets');

const router = express.Router();

router.get('/', (req, res) => {
  const { ticketId, showDate } = req.query;
  const { userId } = req.session;
  Query.checkTicket(userId, ticketId, showDate, (result, err) => {
    if (result) {
      res.status(200).send(result);
    } else {
      res.status(400).send(err);
    }
  });
});

module.exports = router;
