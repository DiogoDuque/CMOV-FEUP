const express = require('express');
const ejs = require('ejs');

const PORT = 80;
const app = express();

app.engine('html', ejs.renderFile);
app.use(express.json());

// Root
app.get('/', (req, res) => {
  res.render('help.html');
});

// TODO routes

app.listen(PORT, () => console.log('Started Stocks Analysis API...'));
