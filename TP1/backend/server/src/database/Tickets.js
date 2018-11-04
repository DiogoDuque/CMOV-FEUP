const execute = require('./DB');
const Profile = require('./Profile');

module.exports = {

    getTicket(id, callback){
        const baseQuery = 'SELECT ticket.is_used, ticket.place, customer.name, event.name, event.price, event.date'
            + ' FROM ticket, customer, event WHERE ticket.id = $1 AND event.id = ticket.event_id AND customer.id = ticket.customer_id';
        execute(baseQuery, [id], (response, err) => {
              if(err){
                  callback(null, err);
              }
              else{
                  callback(response);
              }
        });
    },

    getUsedTickets(user_id, callback){
        const baseQuery = 'SELECT ticket.place, customer.name, event.name, event.price, event.date'
            + ' FROM ticket, customer, event WHERE ticket.customer_id = $1 AND ticket.is_used = TRUE AND event.id = ticket.event_id AND customer.id = ticket.customer_id';
        execute(baseQuery, [user_id], (response, err) => {
            if(err){
                callback(null, err);
            }
            else{
                callback(response);
            }
        });
    },

    getNotUsedTickets(user_id, callback){
        const baseQuery = 'SELECT ticket.place, customer.name, event.name, event.price, event.date'
            + ' FROM ticket, customer, event WHERE ticket.customer_id = $1 AND ticket.is_used = FALSE AND event.id = ticket.event_id AND customer.id = ticket.customer_id';
        execute(baseQuery, [user_id], (response, err) => {
            if(err){
                callback(null, err);
            }
            else{
                callback(response);
            }
        });
    },

    getAllTickets(user_id, callback){
        const baseQuery = 'SELECT ticket.is_used, ticket.place, customer.name, event.name, event.price, event.date'
            + ' FROM ticket, customer, event WHERE ticket.customer_id = $1 AND event.id = ticket.event_id AND customer.id = ticket.customer_id';
        execute(baseQuery, [user_id], (response, err) => {
            if(err){
                callback(null, err);
            }
            else{
                callback(response);
            }
        });
    },

    buyTicket(user_id, show_id, place, callback){
        execute("BEGIN", [], (resBegin, errBegin) => {
            if (errBegin) {
                callback(null, errBegin);
                return;
            }
            let baseQuery = 'INSERT INTO ticket(place, customer_id, event_id) VALUES ($1, $2, $3)';
            execute(baseQuery, [place, user_id, show_id], (response, err) => {
                if (err) {
                    execute("ROLLBACK", [], () => callback(null, err));
                }
                else {
                    baseQuery = 'SELECT price FROM event WHERE id = $1';
                    execute(baseQuery, [show_id], (response, err) => {
                        if (err) {
                            execute("ROLLBACK", [], () => callback(null, err));
                        }
                        else {
                            Profile.setBalance(user_id, response.rows[0].price, callback);
                        }
                    });
                }
            });
        });
    },
};