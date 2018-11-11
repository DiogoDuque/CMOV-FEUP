const express = require('express');
const ejs = require('ejs');
const session = require('express-session');
const passport = require('passport');
const { Strategy } = require('passport-local');
const AuthQuery = require('./src/database/Auth');

const customerRoutes = require('./src/routes/customer');
const customerAuthRoute = require('./src/routes/customer/authRoute');
const cafeteriaRoutes = require('./src/routes/cafeteria');
const terminalRoutes = require('./src/routes/terminal');

const PORT = 3000;
const app = express();
app.engine('html', ejs.renderFile);
app.use(express.json());

// Passport code
passport.use('local', new Strategy(
  { passReqToCallback: true },
  (req, username, password, done) => {
    if (!username || !password) done('No username or password found');
    AuthQuery.checkIfLoginExists(username, password, (result, err) => {
      if (err) {
        done(err);
      } else if (result) {
        done(null, true);
      } else done(null, false);
    });
  },
));
passport.serializeUser((user, done) => {
  done(null, user);
});
passport.deserializeUser((user, done) => {
  done(null, user);
});

// Session
app.use(session({
  secret: 'lalala cmov tp1',
  resave: false,
  saveUninitialized: true,
})); // session secret

app.use(passport.initialize());
app.use(passport.session()); // persistent login sessions

function isLoggedIn(req, res, next) {
  // if user is authenticated in the session, carry on
  if (req.isAuthenticated()) {
    next();
  } else res.status(401).send('Not logged in');
}

// Root
app.get('/', (req, res) => {
  res.render('help.html');
});

// Routes
app.use('/customer', isLoggedIn, customerRoutes);
app.use('/customer/auth', customerAuthRoute);

app.use('/cafeteria', cafeteriaRoutes);


app.use('/terminal', terminalRoutes);


app.listen(PORT, () => console.log('Started Tickets and Payment System API...'));
