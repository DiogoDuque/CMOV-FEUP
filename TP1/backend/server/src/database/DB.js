const { Pool } = require('pg');

function createConfiguration() {
  const connectionString = process.env.DATABASE_URL;

  const config = {
    connectionString,
  };

  const pool = new Pool(config);

  return { pool };
}

module.exports = function execute(text, values, callback) {
  const config = createConfiguration();

  const { pool } = config;
  const p = pool.connect();

  p.then(client => client.query(text, values)
    .then((result, error) => {
      client.end();
      pool.end();
      callback(result, error);
    })).catch((error) => {
    pool.end();
    callback(undefined, error);
  });
};
