--liquibase formatted sql
--changeset minikh:2018-07-26_08-00 objectQuotingStrategy="QUOTE_ALL_OBJECTS" failOnError: true

create table IF NOT EXISTS company
(
  id   uuid    not null constraint company_pkey primary key DEFAULT public.uuid_generate_v1(),
  name varchar not null,
  deleted boolean not null default false
);

INSERT INTO company (id, name)
VALUES ('da2e914c-9099-11e8-ba1b-0242ac110002', 'ВМ системы');
INSERT INTO company (id, name)
VALUES ('da2eb140-9099-11e8-ba1b-0242ac110002', 'Рога и копыта');
