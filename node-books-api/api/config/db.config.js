const Pool = require("pg").Pool;
const env = process.env;

const config = {
  db: {
    host: env.DB_HOST || 'otto.db.elephantsql.com',
    port: env.DB_PORT || '5432',
    user: env.DB_USER || 'cklijfef',
    password: env.DB_PASSWORD || 'V1qidES5k3DSJICDRgXtyT8qeu2SPCZp',
    database: env.DB_NAME || 'cklijfef',
  }
};

const pool = new Pool({
    user:'book',
    host:'192.168.2.49',
    database:'books_database',
    password:'book',
    port:'5432'
});

module.exports = pool;