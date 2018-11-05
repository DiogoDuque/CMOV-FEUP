-----------
-- DROPS --
-----------

DROP TYPE IF EXISTS card_type CASCADE;
DROP TYPE IF EXISTS voucher_type CASCADE;

DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS ticket CASCADE;
DROP TABLE IF EXISTS "event" CASCADE;
DROP TABLE IF EXISTS cafeteria_order CASCADE;
DROP TABLE IF EXISTS cafeteria_product CASCADE;
DROP TABLE IF EXISTS voucher CASCADE;
DROP TABLE IF EXISTS voucher_ticket CASCADE;
DROP TABLE IF EXISTS cafeteria_order_product CASCADE;
DROP TABLE IF EXISTS encryption CASCADE;

-----------
-- ENUMS --
-----------

CREATE TYPE card_type AS ENUM
(
  'Visa',
  'MasterCard'
);

CREATE TYPE voucher_type AS ENUM
(
  'Discount',
  'Free Product'
);


------------
-- TABLES --
------------

CREATE TABLE customer
(
  id SERIAL PRIMARY KEY,
  "name" TEXT NOT NULL,
  nif TEXT UNIQUE NOT NULL,
  username TEXT UNIQUE NOT NULL,
  "password" TEXT NOT NULL,
  "card_type" card_type NOT NULL,
  card_number TEXT UNIQUE NOT NULL,
  card_validity TEXT NOT NULL,
  balance NUMERIC NOT NULL DEFAULT 0,
  public_key TEXT NOT NULL
);

CREATE TABLE "event"
(
  id SERIAL PRIMARY KEY,
  "name" TEXT NOT NULL,
  "date" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  price NUMERIC NOT NULL,
  UNIQUE("name", "date")
);

CREATE TABLE ticket
(
  id SERIAL PRIMARY KEY,
  place TEXT NOT NULL,
  is_used BOOLEAN NOT NULL DEFAULT FALSE,
  customer_id INTEGER REFERENCES customer(id) NOT NULL,
  event_id INTEGER REFERENCES "event"(id) NOT NULL
);

CREATE TABLE cafeteria_order
(
  id SERIAL PRIMARY KEY,
  "date" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  customer_id INTEGER REFERENCES customer(id) NOT NULL
);

CREATE TABLE cafeteria_product
(
  id SERIAL PRIMARY KEY,
  "name" TEXT UNIQUE NOT NULL,
  price NUMERIC NOT NULL
);

CREATE TABLE voucher
(
  id SERIAL PRIMARY KEY,
  is_used BOOLEAN NOT NULL DEFAULT FALSE,
  "type" voucher_type NOT NULL,
  customer_id INTEGER REFERENCES customer(id) NOT NULL,
  product_id INTEGER REFERENCES cafeteria_product(id)
);

CREATE TABLE voucher_ticket
(
  voucher_id INTEGER REFERENCES voucher(id) UNIQUE NOT NULL,
  ticket_id INTEGER REFERENCES ticket(id) UNIQUE NOT NULL,
  PRIMARY KEY(voucher_id, ticket_id)
);

CREATE TABLE cafeteria_order_product
(
  order_id INTEGER REFERENCES cafeteria_order(id) NOT NULL,
  product_id INTEGER REFERENCES cafeteria_product(id) NOT NULL,
  voucher_id INTEGER REFERENCES voucher(id) UNIQUE,
  PRIMARY KEY(order_id, product_id)
);

CREATE TABLE encryption
(
  uuid TEXT PRIMARY KEY,
  public_key TEXT NOT NULL
);


-------------
-- DEF INS --
-------------

INSERT INTO cafeteria_product("name", price) VALUES('Coffee', 0.6);
INSERT INTO cafeteria_product("name", price) VALUES('Soda Drink', 1);
INSERT INTO cafeteria_product("name", price) VALUES('Popcorn', 1);
INSERT INTO cafeteria_product("name", price) VALUES('Sandwich', 1.5);

INSERT INTO "event"("name", "date", price) VALUES('O Leão Vegetariano',           timestamp '2018-11-30 21:30', 15);
INSERT INTO "event"("name", "date", price) VALUES('A Preguiça morreu de Velha',   timestamp '2018-12-7 21:30', 20);
INSERT INTO "event"("name", "date", price) VALUES('A Dama - O Musical',           timestamp '2018-12-8 21:30', 10);
INSERT INTO "event"("name", "date", price) VALUES('SPAM a Preto e Branco',        timestamp '2018-12-15 21:00', 12);
INSERT INTO "event"("name", "date", price) VALUES('A Preguiça morreu de Velha',   timestamp '2018-12-16 16:00', 20);
INSERT INTO "event"("name", "date", price) VALUES('Música Parietal',              timestamp '2018-12-22 21:30', 17);
INSERT INTO "event"("name", "date", price) VALUES('Reveillon com o Gato Florento',timestamp '2018-12-31 22:30', 30);
INSERT INTO "event"("name", "date", price) VALUES('Música para os meus Olhos',    timestamp '2019-1-5 21:00', 15);
INSERT INTO "event"("name", "date", price) VALUES('Música para os meus Olhos',    timestamp '2019-1-13 16:00', 15);
INSERT INTO "event"("name", "date", price) VALUES('Quando o que falta é Inspirar',timestamp '2019-1-28 21:30', 20);