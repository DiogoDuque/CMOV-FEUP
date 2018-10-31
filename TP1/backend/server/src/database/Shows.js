const execute = require('./DB');

module.exports = {

    getNextShows(username, callback){
        const baseQuery = 'SELECT event.name, event.date, event.price, event.place FROM costumer, event, ticket WHERE costumer.username = ?'
         + ' AND ticket.costumer_id = costumer.id AND event.id NOT IN ticket.event_id';
        execute(baseQuery, [username], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(response);
            }
        });
    },

    getMyShows(username, callback){
        const baseQuery = 'SELECT event.name, event.date, event.price, event.place FROM costumer, event, ticket WHERE costumer.username = ?'
            + ' AND ticket.costumer_id = costumer.id AND event.id = ticket.event_id';
        execute(baseQuery, [username], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(response);
            }
        });
    },
};