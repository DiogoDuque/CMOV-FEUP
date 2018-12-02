const request = require('request');
const { apiKey, apiHost } = require('./config');

const basePath = `${apiHost}getHistory.json?apikey=${apiKey}`;

function getFinalUrl(args) {
  console.log(`args: ${JSON.stringify(args)}`);
  let url = `${basePath}`;
  for (const key in args) {
    url += `&${key}=${args[key]}`;
  }
  return url;
}

module.exports = {
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

    console.log(`url: ${url}`);
    request(url, { json: true }, (err, res, body) => callback(err, body.results));
  },
};
