const execute = require('./DB');

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
    }

    buyTicket(user_id, show_id, place, callback){
        const baseQuery = 'INSERT INTO ticket(place, is_used, costumer_id, event_id) WHERE (?, FALSE, ?, ?)';
        execute(baseQuery, [place, user_id, show_id], (response, err) => {
            if (err) {
                callback(null, err);
            }
            else {
                callback(response);
            }
        });
    },

    createVoucher(ticket_id, type, product, callback){
        const baseQuery = 'INSERT INTO voucher(is_used, type, product_id) WHERE (FALSE, ?, ?)';
        execute(baseQuery, [type, product], (response, err) => {
            if(err) {
                callback(null, err);
            }
            else {
                baseQuery = 'SELECT id FROM voucher ORDER BY id DESC LIMIT 1';
                execute(baseQuery, [], (response, err) => {
                    if(err) {
                        callback(null, err);
                    }
                    else {
                        baseQuery = 'INSERT INTO voucher_ticket(voucher_id, ticket_id) WHERE (?, ?)';
                        execute(baseQuery, [response[0].id, ticket_id], (response, err) => {
                            if(err) {
                                callback(null, err);
                            }
                            else {
                                 callback(response);
                            }
                        });
                    }
                });
            }
        });
    },
};