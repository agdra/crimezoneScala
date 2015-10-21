# --- !Ups

CREATE TABLE jenis_kejahatan (
  id serial PRIMARY KEY,
  nama varchar(30) UNIQUE,
  foto varchar(254)
);

CREATE TABLE lokasi_kejahatan (
  id serial PRIMARY KEY,
  nama varchar(30) UNIQUE
);


# --- !Downs

DROP TABLE IF EXISTS jenis_kejahatan;
DROP TABLE IF EXISTS lokasi_kejahatan;
