const express = require('express');
const ejs = require('ejs');
const { userRoute, cafeteriaRoute, showRoute, authRoute } = require('./src/routes');

const PORT = 3000;
const app = express();
app.engine('html', ejs.renderFile);
app.use(express.json());

// Root
app.get('/', (req, res) => {
  res.render('help.html');
});

// Routes
app.use('/auth', authRoute);
app.use('/user', userRoute);
app.use('/cafeteria', cafeteriaRoute);
app.use('/show', showRoute);

app.listen(PORT, () => console.log('Started Tickets and Payment System API...'));