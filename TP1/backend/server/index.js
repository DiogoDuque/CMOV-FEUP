const express = require('express');
const ejs = require('ejs');
const session = require('express-session');
const passport = require('passport');
const { Strategy } = require('passport-local');
const AuthQuery = require('./src/database/Auth');
const { cafeteriaRouteCustomer, showRoute, authRoute, profileRoute, ticketsRoute, vouchersRouteCustomer } = require('./src/routes/customer/index');
const { cafeteriaRoute, vouchersRoute } = require('./src/routes/cafeteria/index');
const { ticketsRouteTerminal } = require('./src/routes/terminal/index');

const PORT = 3000;
const app = express();
app.engine('html', ejs.renderFile);
app.use(express.json());

// Passport code
passport.use('local', new Strategy(
  { passReqToCallback: true },
  (req, username, password, done) => {
    if(!username || !password)
      done("No username or password found");
      AuthQuery.checkIfLoginExists(username, password, (result, err) => {
      if(err) {
        done(err);
      } else if(result) {
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
  if (req.isAuthenticated())
      return next();

  // if they aren't send UNAUTHORIZED
  res.status(401).send('Not logged in');
}

// Root
app.get('/', (req, res) => {
  res.render('help.html');
});

// Routes
app.use('/customer/auth',      authRoute);
app.use('/customer/cafeteria', isLoggedIn, cafeteriaRouteCustomer);
app.use('/customer/show',      isLoggedIn, showRoute);
app.use('/customer/tickets',   isLoggedIn, ticketsRoute);
app.use('/customer/vouchers',  isLoggedIn, vouchersRouteCustomer);
app.use('/customer/profile',   isLoggedIn, profileRoute);

app.use('/cafeteria', cafeteriaRoute);
app.use('/cafeteria/vouchers',  vouchersRoute);

app.use('/terminal',  ticketsRouteTerminal);


app.listen(PORT, () => console.log('Started Tickets and Payment System API...'));