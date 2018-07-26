--liquibase formatted sql
--changeset minikh:2018-07-26_08-05 objectQuotingStrategy="QUOTE_ALL_OBJECTS" failOnError: true


create table "order"
(
  id                  uuid        not null constraint order_pkey primary key DEFAULT public.uuid_generate_v1(),
  creation_date       bigint      not null,
  last_updated        bigint      not null,
  number              varchar(64) not null,
  desc                varchar,
  account_number      varchar,
  discount            real        not null,
  discount_sum        decimal     not null,
  count               integer     not null,
  summa               decimal     not null,
  area                real        not null,
  perimeter           real        not null,
  client_id           uuid        not null references client (id),
  receptionOfOrder_id uuid        not null references receptio (id),
  deleted             boolean     not null
);

create table order_item
(
  id            uuid        not null constraint order_item_pkey primary key DEFAULT public.uuid_generate_v1(),
  creation_date bigint      not null,
  last_updated  bigint      not null,
  desc          varchar,
  number        varchar(64) not null,
  length        real        not null,
  width         real        not null,
  count         integer     not null,
  area          real        not null,
  perimeter     real        not null,
  process_sum   decimal     not null,
  summa         decimal     not null,
  order_id      uuid        not null references "order" (id),
  material_id   uuid        not null references material (id)
);

create table order_item_process
(
--   id         uuid not null constraint order_item_process_pkey primary key DEFAULT public.uuid_generate_v1(),
  item_id    uuid not null references order_item (id),
  process_id uuid not null references process (id)
);

