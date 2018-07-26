--liquibase formatted sql
--changeset minikh:2018-07-26_08-01 objectQuotingStrategy="QUOTE_ALL_OBJECTS" failOnError: true

create table client_type
(
  id      uuid        not null constraint client_type_pkey primary key DEFAULT public.uuid_generate_v1(),
  name    varchar(64) not null,
  deleted boolean     not null                                         default false
);

create table client
(
  id             uuid        not null constraint client_pkey primary key      DEFAULT public.uuid_generate_v1(),
  name           varchar(64) not null,
  inn            integer,
  account        varchar(50),
  phone          varchar(18) not null unique,
  email          varchar(50),
  description    varchar,
  discount       decimal     not null,
  client_type_id uuid        not null references client_type (id),
  company_id     uuid        not null references company (id),
  deleted        boolean     not null                                         default false
);
