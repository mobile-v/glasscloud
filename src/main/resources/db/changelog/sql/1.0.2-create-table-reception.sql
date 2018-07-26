--liquibase formatted sql
--changeset minikh:2018-07-26_23-40 objectQuotingStrategy="QUOTE_ALL_OBJECTS" failOnError: true

create table reception
(
  id               uuid         not null constraint reception_pkey primary key DEFAULT public.uuid_generate_v1(),
  name             varchar(100) not null,
  order_num_prefix varchar(15)  not null,
  description      varchar,
  address          varchar      not null,
  company_id       uuid         not null references company (id),
  phone            varchar(18)  not null,
  deleted          boolean      not null                                       default false
);

INSERT INTO glass.reception (id, name, order_num_prefix, description, address, company_id, phone, deleted) VALUES ('e94e077c-910a-11e8-a735-0242ac110002', 'Савеловская-1', 'САВА', 'Савеловская', 'м. Савеловская', 'da2e914c-9099-11e8-ba1b-0242ac110002', '911', false);
INSERT INTO glass.reception (id, name, order_num_prefix, description, address, company_id, phone, deleted) VALUES ('e94e29d2-910a-11e8-a735-0242ac110002', 'Савеловская-2', 'САВА', 'Савеловская', 'м. Савеловская', 'da2e914c-9099-11e8-ba1b-0242ac110002', '911', false);
INSERT INTO glass.reception (id, name, order_num_prefix, description, address, company_id, phone, deleted) VALUES ('e94e411a-910a-11e8-a735-0242ac110002', 'Марьино', 'М', '', 'м.Марьино', 'da2e914c-9099-11e8-ba1b-0242ac110002', '100', false);