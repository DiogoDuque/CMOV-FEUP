const express = require('express');
const ejs = require('ejs');
const Joi = require('joi');
const Api = require('./app/externalApi');

const PORT = 8080;
const app = express();

app.engine('html', ejs.renderFile);
app.use(express.json());

// Root
app.get('/', (req, res) => {
  res.render('help.html');
});


// API

function joiHandler(joiErr, joiRes, res, done) {
  if (joiErr) {
    res.status(400).send(joiErr.details);
  } else {
    done();
  }
}

function externalApiHandler(err, body, res) {
  if (err) {
    res.status(400).send(err);
  } else {
    res.send(body);
  }
}

const historySchema = Joi.object().keys({
  company: [Joi.string().required(), Joi.array().length(2).required()],
  period: Joi.number().integer().valid(7, 30).required(),
});


app.get('/history', (req, res) => {
  const { query } = req;
  Joi.validate(query, historySchema,
    (joiErr, joiRes) => joiHandler(joiErr, joiRes, res, () => {
      const { company, period } = query;
      if (Array.isArray(company)) {
        Api.getQuotesHistoryTwoCompanies(company, period, (err, body) => externalApiHandler(err, body, res));
      } else {
        Api.getQuotesHistory(company, period, (err, body) => externalApiHandler(err, body, res));
      }
    }));
});

app.get('/quote', (req, res) => {
  Api.getCurrentQuotes((err, body) => externalApiHandler(err, body, res));
});


app.listen(PORT, () => console.log('Started Stocks Analysis API...'));
