const request = require('request');
const { apiKey, apiHost } = require('./config');

const historyBasePath = `${apiHost}getHistory.json?apikey=${apiKey}`;
const quoteBasePath = `${apiHost}getQuote.json?apikey=${apiKey}`;

function getFinalUrl(basePath, args) {
  let url = `${basePath}`;
  for (const key in args) {
    url += `&${key}=${args[key]}`;
  }
  return url;
}

module.exports = {
  getQuotesHistoryTwoCompanies(companies, period, callback) {
    this.getQuotesHistory(companies[0], period, (err1, res1) => {
      if (err1) {
        callback(err1);
      } else {
        this.getQuotesHistory(companies[1], period, (err2, res2) => {
          callback(err2, [res1, res2]);
        });
      }
    });
  },

  getQuotesHistory(companySymbol, period, callback) {
    const url = getFinalUrl(historyBasePath, {
      symbol: companySymbol,
      maxRecords: period,
      type: 'daily',
      startDate: '20100101',
      // interval: 60,
      order: 'asc',
      dividends: true,
      // volume: 'sum',
    });
    request(url, { json: true }, (err, res, body) => {
      if (err) {
        callback(err);
      } else {
        const history = [];
        body.results.forEach((elem) => {
          const { tradingDay, close } = elem;
          history.push({ tradingDay, close });
        });
        callback(null, history);
      }
    });
  },

  getCurrentQuotes(callback) {
    const url = getFinalUrl(quoteBasePath, {
      symbols: 'AAPL%2CAMD%2CFB%2CGOOG%2CHPE%2CIBM%2CINTC%2CMSFT%2CORCL%2CTWTR',
      fields: 'previousClose',
    });
    request(url, { json: true }, (err, res, body) => {
      if (err) {
        callback(err);
      } else {
        const quotes = [];
        body.results.forEach((elem) => {
          const {
            symbol, netChange, percentChange,
          } = elem;
          quotes.push({
            symbol, netChange: netChange.toFixed(2), percentChange: percentChange.toFixed(2),
          });
        });
        callback(null, quotes);
      }
    });
  },
};
