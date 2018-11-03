const express = require('express');
const Query = require('../database/Shows');

const router = express.Router();

router.get('/next_shows', (req, res) => {
    Query.getNextShows((result, err) => {
        if(result) {
            res.status(200).send(`{${JSON.stringify(result.rows)}}`);
        } else {
            res.status(400).send(err);
        }
    });
});

router.get('/my_shows', (req, res) => {
    const { username } = req.body;
    Query.getMyShows(username, (result, err) => {
        if(result) {
            res.status(200).send(result.rows);
        } else {
            res.status(400).send(err);
        }
    });
});

module.exports = router;