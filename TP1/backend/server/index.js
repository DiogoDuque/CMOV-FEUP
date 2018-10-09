const express = require('express');
const ejs = require('ejs');

const PORT = 3000;
const app = express();
app.engine('html', ejs.renderFile);
app.use(express.json());

// Root
app.get('/', (req, res) => {
  res.render('help.html');
});

// Routes
// app.use('/person', PersonRoute);

app.listen(PORT, () => console.log('Started Tickets and Payment System API...'));