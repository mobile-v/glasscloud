--liquibase formatted sql
--changeset minikh:2018-07-26_08-03 objectQuotingStrategy="QUOTE_ALL_OBJECTS" failOnError: true

create table process_type
(
  id         uuid    not null constraint process_type_pkey primary key DEFAULT public.uuid_generate_v1(),
  name       varchar not null,
  desc       varchar,
  company_id uuid    not null references company (id),
  deleted    boolean not null                                      default false
);

create table process
(
  id         uuid    not null constraint process_pkey primary key DEFAULT public.uuid_generate_v1(),
  depth      real   not null,
  price      DECIMAL not null,
  desc       varchar,
  type_id    uuid    not null references process_type (id),
  company_id uuid    not null references company (id),
  deleted    boolean not null                                      default false
);


create table process_material_type
(
--   id              uuid not null constraint process_material_type_pkey primary key DEFAULT public.uuid_generate_v1(),
  process_id      uuid not null references process (id),
  materialtype_id uuid not null references material_type (id)
);


