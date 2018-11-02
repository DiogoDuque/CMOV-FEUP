const execute = require('./DB');
const Profile = require('./Profile');

module.exports = {

    getTicket(id, callback){
        const baseQuery = 'SELECT ticket.is_used, ticket.place costumer.name, event.name, event.description, event.place, event.price, event.date'
            + ' FROM ticket, costumer, event WHERE ticket.id = ? AND event.id = ticket.event_id AND costumer.name = ticket.costumer_id';
        execute(baseQuery, [id], (response, err) => {
              if(err){
                  callback(null, err);
              }
              else{
                  callback(response);
              }
        });
    },

    buyTicket(user_id, show_id, place, callback){
        let baseQuery = 'INSERT INTO ticket(place, is_used, costumer_id, event_id) WHERE (?, FALSE, ?, ?)';
        execute(baseQuery, [place, user_id, show_id], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                baseQuery = 'SELECT price FROM event WHERE id = ?';
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