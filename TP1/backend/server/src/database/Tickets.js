const execute = require('./DB');
const Profile = require('./Profile');

module.exports = {

    getTicket(id, callback){
        const baseQuery = 'SELECT ticket.is_used, ticket.place costumer.name, event.name, event.description, event.place, event.price, event.date'
            + ' FROM ticket, costumer, event WHERE ticket.id = $1 AND event.id = ticket.event_id AND costumer.name = ticket.costumer_id';
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
        const baseQuery = 'SELECT ticket.place costumer.name, event.name, event.description, event.place, event.price, event.date'
            + ' FROM ticket, costumer, event WHERE ticket.costumer_id = $1 AND ticket.is_used = TRUE AND event.id = ticket.event_id AND costumer.name = ticket.costumer_id';
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
        const baseQuery = 'SELECT ticket.place costumer.name, event.name, event.description, event.place, event.price, event.date'
            + ' FROM ticket, costumer, event WHERE ticket.costumer_id = $1 AND ticket.is_used = FALSE AND event.id = ticket.event_id AND costumer.name = ticket.costumer_id';
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
        const baseQuery = 'SELECT ticket.is_used, ticket.place costumer.name, event.name, event.description, event.place, event.price, event.date'
            + ' FROM ticket, costumer, event WHERE ticket.costumer_id = $1 AND event.id = ticket.event_id AND costumer.name = ticket.costumer_id';
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
        let baseQuery = 'INSERT INTO ticket(place, is_used, costumer_id, event_id) WHERE ($1, FALSE, $2, $3)';
        execute(baseQuery, [place, user_id, show_id], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                baseQuery = 'SELECT price FROM event WHERE id = $1';
                execute(baseQuery, [show_id], (response, err) => {
                    if (err) {
                        callback(null, err);
                    }
                    else {
                        Profile.setBalance(user_id, response.rows[0].price, callback);
                        callback(response);
                    }
                });
            }
        });
    },
};