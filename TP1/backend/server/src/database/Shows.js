const execute = require('./DB');

module.exports = {

    getNextShows(callback){
        const baseQuery = 'SELECT * FROM "event" ORDER by "date"';
        execute(baseQuery, [], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(response);
            }
        });
    },

    getMyShows(username, callback){
        const baseQuery = 'SELECT event.name, event.date, event.price FROM event ' +
            'INNER JOIN ticket ON event.id=ticket.event_id ' +
            'INNER JOIN customer ON ticket.customer_id=customer.id WHERE customer.username = $1';
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