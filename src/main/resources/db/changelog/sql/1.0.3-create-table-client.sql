--liquibase formatted sql
--changeset minikh:2018-07-26_08-01 objectQuotingStrategy="QUOTE_ALL_OBJECTS" failOnError: true

create table client_type
(
  id      uuid        not null constraint client_type_pkey primary key DEFAULT public.uuid_generate_v1(),
  name    varchar(64) not null,
  deleted boolean     not null                                         default false
);

INSERT INTO client_type (id, name, deleted) VALUES ('f2f658fe-9111-11e8-a758-0242ac110002', 'Юридическое лицо', false);
INSERT INTO client_type (id, name, deleted) VALUES ('fa43dc44-9111-11e8-a758-0242ac110002', 'Физическое лицо', false);

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

INSERT INTO glass.client (id, name, inn, account, phone, email, description, discount, client_type_id, company_id, deleted) VALUES ('e0a8afcf-2a54-4b31-80c9-445121377855', 'Иванов Иван Иванович', 111, 'dgdg', '911', 'minikh@mail.ru', 'fhf', 0.5, 'fa43dc44-9111-11e8-a758-0242ac110002', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);