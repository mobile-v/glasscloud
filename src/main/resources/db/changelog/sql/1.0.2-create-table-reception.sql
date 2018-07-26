--liquibase formatted sql
--changeset minikh:2018-07-26_23-40 objectQuotingStrategy="QUOTE_ALL_OBJECTS" failOnError: true

create table reception
(
  id               uuid         not null constraint reception_pkey primary key DEFAULT public.uuid_generate_v1(),
  name             varchar(100) not null,
  order_num_prefix varchar(15)  not null,
  desc             varchar,
  address          varchar      not null,
  company_id       uuid         not null references company (id),
  phone            varchar(18)  not null,
  deleted boolean not null default false
);
