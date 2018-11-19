const express = require('express');
const Query = require('../../database/Tickets');

const router = express.Router();

router.put('/', (req, res) => {
  const { userId, ticketId, showDate } = req.body;
  Query.checkTicket(userId, ticketId, showDate, (result, err) => {
    if (result) {
      res.status(200).send(`{result: ${result}}`);
    } else {
      res.status(400).send(err);
    }
  });
});

module.exports = router;
