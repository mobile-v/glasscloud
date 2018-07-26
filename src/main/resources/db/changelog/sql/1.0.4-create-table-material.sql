--liquibase formatted sql
--changeset minikh:2018-07-26_08-02 objectQuotingStrategy="QUOTE_ALL_OBJECTS" failOnError: true

create table material_color
(
  id         uuid    not null constraint material_color_pkey primary key DEFAULT public.uuid_generate_v1(),
  name       varchar not null,
  company_id uuid    not null references company (id),
  deleted    boolean not null                                            default false
);

create table material_type
(
  id         uuid    not null constraint material_type_pkey primary key DEFAULT public.uuid_generate_v1(),
  name       varchar not null,
  company_id uuid    not null references company (id),
  deleted    boolean not null                                           default false
);


create table material
(
  id         uuid    not null constraint material_pkey primary key DEFAULT public.uuid_generate_v1(),
  depth      REAL not null,
  length     REAL    not null,
  width      REAL    not null,
  price      DECIMAL not null,
  desc       varchar,
  color_id   uuid    not null references material_color (id),
  type_id    uuid    not null references material_type (id),
  company_id uuid    not null references company (id),
  deleted    boolean not null                                      default false
);

