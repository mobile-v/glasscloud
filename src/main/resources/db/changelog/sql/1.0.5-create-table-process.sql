--liquibase formatted sql
--changeset minikh:2018-07-26_08-03 objectQuotingStrategy="QUOTE_ALL_OBJECTS" failOnError: true

create table process_type
(
  id          uuid    not null constraint process_type_pkey primary key DEFAULT public.uuid_generate_v1(),
  name        varchar not null,
  description varchar,
  company_id  uuid    not null references company (id),
  deleted     boolean not null                                          default false
);

create table process
(
  id          uuid    not null constraint process_pkey primary key  DEFAULT public.uuid_generate_v1(),
  depth       real    not null,
  price       DECIMAL not null,
  description varchar,
  type_id     uuid    not null references process_type (id),
  company_id  uuid    not null references company (id),
  deleted     boolean not null                                      default false
);


-- create table process_material_type
-- (
--   --   id              uuid not null constraint process_material_type_pkey primary key DEFAULT public.uuid_generate_v1(),
--   process_id      uuid not null references process (id),
--   materialtype_id uuid not null references material_type (id)
-- );

create table process_material
(
  process_id  uuid not null references process (id),
  material_id uuid not null references material (id)
);

INSERT INTO glass.process_type (id, name, description, company_id, deleted) VALUES ('c0690046-90fb-11e8-b6d5-0242ac110002', 'Шлифовка прямолинейная', '', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.process_type (id, name, description, company_id, deleted) VALUES ('c0692ef4-90fb-11e8-b6d5-0242ac110002', 'Шлифовка криволинейная', '', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.process_type (id, name, description, company_id, deleted) VALUES ('c0694a38-90fb-11e8-b6d5-0242ac110002', 'Полировка прямолинейная', '', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.process_type (id, name, description, company_id, deleted) VALUES ('c06966da-90fb-11e8-b6d5-0242ac110002', 'Полировка криволинейная', '', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.process_type (id, name, description, company_id, deleted) VALUES ('c069819c-90fb-11e8-b6d5-0242ac110002', 'Шлифовка кромки с изменением угла  45-89 градусов', '', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.process_type (id, name, description, company_id, deleted) VALUES ('c0699b64-90fb-11e8-b6d5-0242ac110002', 'Полировка кромки с изменением угла  45-89 градусов', '', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);