const request = require('request');
const { apiKey, apiHost } = require('./config');

const basePath = `${apiHost}getHistory.json?apikey=${apiKey}`;

function getFinalUrl(args) {
  let url = `${basePath}`;
  for (const key in args) {
    url += `&${key}=${args[key]}`;
  }
  return url;
}

module.exports = {
  getLastQuotesTwoCompanies(companies, period, callback) {
    this.getLastQuotes(companies[0], period, (err1, res1) => {
      if (err1) {
        callback(err1);
      } else {
        this.getLastQuotes(companies[1], period, (err2, res2) => {
          callback(err2, [res1, res2]);
        });
      }
    });
  },

  getLastQuotes(company, period, callback) {
    const url = getFinalUrl({
      symbol: company,
      maxRecords: period,
      type: 'daily',
      startDate: '20100101',
      interval: 60,
      order: 'asc',
      dividends: true,
      volume: 'sum',
    });

    request(url, { json: true }, (err, res, body) => callback(err, body.results));
  },
};
