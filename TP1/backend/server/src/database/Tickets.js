const execute = require('./DB');
const Profile = require('./Profile');
const Vouchers = require('./Vouchers');

module.exports = {

  getTicket(id, callback) {
    const baseQuery = 'SELECT ticket.is_used, ticket.place, customer.name, event.name, event.price, event.date'
      + ' FROM ticket, customer, event WHERE ticket.id = $1 AND event.id = ticket.event_id AND customer.id = ticket.customer_id';
    execute(baseQuery, [id], (response, err) => {
      if (err) {
        callback(null, err);
      }
      else {
        callback(response);
      }
    });
  },

  getUsedTickets(user_id, callback) {
    const baseQuery = 'SELECT ticket.place, customer.name, event.name, event.price, event.date'
      + ' FROM ticket, customer, event WHERE ticket.customer_id = $1 AND ticket.is_used = TRUE AND event.id = ticket.event_id AND customer.id = ticket.customer_id';
    execute(baseQuery, [user_id], (response, err) => {
      if (err) {
        callback(null, err);
      }
      else {
        callback(response);
      }
    });
  },

  getNotUsedTickets(user_id, callback) {
    const baseQuery = 'SELECT ticket.place, customer.name, event.name, event.price, event.date'
      + ' FROM ticket, customer, event WHERE ticket.customer_id = $1 AND ticket.is_used = FALSE AND event.id = ticket.event_id AND customer.id = ticket.customer_id';
    execute(baseQuery, [user_id], (response, err) => {
      if (err) {
        callback(null, err);
      }
      else {
        callback(response);
      }
    });
  },

  getAllTickets(user_id, callback) {
    const baseQuery = 'SELECT ticket.is_used, ticket.place, customer.name, event.name, event.price, event.date'
      + ' FROM ticket, customer, event WHERE ticket.customer_id = $1 AND event.id = ticket.event_id AND customer.id = ticket.customer_id';
    execute(baseQuery, [user_id], (response, err) => {
      if (err) {
        callback(null, err);
      }
      else {
        callback(response);
      }
    });
  },

  buyTickets(userId, showId, count, callback, prevTickets) {
    let tickets = prevTickets;
    if (!tickets) {
      tickets = [];
    }
    const place = Math.floor(Math.random() * 100) + 1;
    this.buyTicket(userId, showId, place, (res, err) => {
      if (err) {
        callback(null, err);
      } else {
        tickets.push(res);
        if (count === 1) {
          callback(tickets);
        } else {
          this.buyTickets(userId, showId, count - 1, callback, tickets);
        }
      }
    });
  },

  buyTicket(userId, showId, place, callback) {
    let baseQuery = 'INSERT INTO ticket(place, customer_id, event_id) VALUES ($1, $2, $3) RETURNING id';
    execute(baseQuery, [place, userId, showId], (response, err) => {
      if (err) {
        callback(null, err);
      } else {
        const ticketId = response.rows[0].id;
        baseQuery = 'SELECT price FROM event WHERE id = $1';
        execute(baseQuery, [showId], (response2, err2) => {
          if (err2) {
            callback(null, err2);
          } else {
            Profile.setBalance(userId, response2.rows[0].price, (balanceRes, balanceErr) => {
              console.log('entered balance callback');
              if (balanceErr) {
                callback(null, balanceErr);
              } else {
                console.log(`balanceRes = ${JSON.stringify(balanceRes)}`);
                const { balance } = balanceRes.rows[0];
                Vouchers.createVoucher(userId, ticketId, 'Free Product',
                  (voucherRes, voucherErr) => {
                    console.log('Tickets here again?');
                    if (voucherErr) {
                      callback(null, voucherErr);
                    } else {
                      const obj = {
                        balance,
                        ...voucherRes,
                      };
                      console.log('yello');
                      callback(obj);
                    }
                  });
              }
            });
          }
        });
      }
    });
  },
};
