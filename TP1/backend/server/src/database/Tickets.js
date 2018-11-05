const execute = require('./DB');
const Profile = require('./Profile');
const Vouchers = require('./Vouchers');

module.exports = {

    getTicket(id, callback) {
        const baseQuery = 'SELECT ticket.is_used, ticket.place, customer.name, event.name, event.price, event.date'
            + ' FROM ticket, customer, event WHERE ticket.id = $1 AND event.id = ticket.event_id AND customer.id = ticket.customer_id';
        execute(baseQuery, [id], (response, err) => {
            if(err) {
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
            if(err) {
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
            if(err) {
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
            if(err) {
                callback(null, err);
            }
            else {
                callback(response);
            }
        });
    },

    buyTickets(userId, showId, count, callback, prevTickets) {
        if(!prevTickets) {
            prevTickets = [];
        }
        const place = Math.floor(Math.random() * 100) + 1;
        this.buyTicket(userId, showId, place, (res, err) => {
            if(err) {
                callback(null, err);
            } else {
                prevTickets.push(res);
                if(count == 1) {
                    callback(prevTickets);
                } else {
                    this.buyTickets(userId, showId, count - 1, callback, prevTickets);
                }
            }
        });
    },

    buyTicket(user_id, show_id, place, callback) {
        let baseQuery = 'INSERT INTO ticket(place, customer_id, event_id) VALUES ($1, $2, $3) RETURNING id';
        execute(baseQuery, [place, user_id, show_id], (response, err) => {
            if(err) {
                callback(null, err);
            }
            else {
                const ticketId = response.rows[0].id;
                baseQuery = 'SELECT price FROM event WHERE id = $1';
                execute(baseQuery, [show_id], (response, err) => {
                    if(err) {
                        callback(null, err);
                    }
                    else {
                        Profile.setBalance(user_id, response.rows[0].price, (balanceRes, balanceErr) => {
                            if(balanceErr) {
                                callback(null, balanceErr);
                            } else {
                                const balance = balanceRes.rows[0].balance;
                                Vouchers.createVoucher(user_id, ticketId, 'Free Product',
                                    (voucherRes, voucherErr) => {
                                        if(voucherErr) {
                                            callback(null, voucherErr);
                                        } else {
                                            const obj = {
                                                balance,
                                                ...voucherRes,
                                            };
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