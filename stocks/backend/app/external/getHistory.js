const { apiKey, apiHost } = require('./config');

const basePath = `${apiHost}getHistory.json?apikey=YOUR_API_KEY=${apiKey}`;

module.exports = {
  //required: symbol, type
  //optional needed: maxRecords, order
};
