--liquibase formatted sql
--changeset minikh:2018-07-26_08-06 objectQuotingStrategy="QUOTE_ALL_OBJECTS" failOnError: true

create table users_module
(
  id          uuid        not null constraint users_module_pkey primary key DEFAULT public.uuid_generate_v1(),
  name        varchar(64) not null,
  description varchar,
  is_title    boolean     not null,
  number      integer     not null,
  deleted     boolean     not null                                          default false
);

create table users_module_permission
(
  id          uuid    not null constraint users_module_permission_pkey primary key DEFAULT public.uuid_generate_v1(),
  is_view     boolean not null,
  is_edit     boolean not null,
  module_id   uuid    not null references users_module (id),
  user_id     uuid    not null references "user" (id),
  description varchar,
  deleted     boolean not null                                                     default false
);

INSERT INTO glass.users_module (id, name, description, is_title, number, deleted) VALUES ('ced7a852-90fc-11e8-b376-0242ac110002', 'КАМПАНИИ', '', true, 10, false);
INSERT INTO glass.users_module (id, name, description, is_title, number, deleted) VALUES ('ced7c2f6-90fc-11e8-b376-0242ac110002', 'Список кампаний', 'company', false, 20, false);
INSERT INTO glass.users_module (id, name, description, is_title, number, deleted) VALUES ('ced7d66a-90fc-11e8-b376-0242ac110002', 'Точки приема заказов', 'reception_of_order', false, 30, false);
INSERT INTO glass.users_module (id, name, description, is_title, number, deleted) VALUES ('ced7e768-90fc-11e8-b376-0242ac110002', 'Клиенты', 'client', false, 40, false);
INSERT INTO glass.users_module (id, name, description, is_title, number, deleted) VALUES ('ced7fa8c-90fc-11e8-b376-0242ac110002', 'МАТЕРИАЛ', '', true, 50, false);
INSERT INTO glass.users_module (id, name, description, is_title, number, deleted) VALUES ('ced8113e-90fc-11e8-b376-0242ac110002', 'Список материалов', 'material', false, 60, false);
INSERT INTO glass.users_module (id, name, description, is_title, number, deleted) VALUES ('ced82656-90fc-11e8-b376-0242ac110002', 'Цвет материала', 'material-color', false, 70, false);
INSERT INTO glass.users_module (id, name, description, is_title, number, deleted) VALUES ('ced8386c-90fc-11e8-b376-0242ac110002', 'Тип материала', 'material-type', false, 80, false);
INSERT INTO glass.users_module (id, name, description, is_title, number, deleted) VALUES ('ced8449c-90fc-11e8-b376-0242ac110002', 'РАБОТА', '', true, 90, false);
INSERT INTO glass.users_module (id, name, description, is_title, number, deleted) VALUES ('ced851d0-90fc-11e8-b376-0242ac110002', 'Список работ', 'process', false, 100, false);
INSERT INTO glass.users_module (id, name, description, is_title, number, deleted) VALUES ('ced8613e-90fc-11e8-b376-0242ac110002', 'Тип работы', 'process-type', false, 110, false);
INSERT INTO glass.users_module (id, name, description, is_title, number, deleted) VALUES ('ced86df0-90fc-11e8-b376-0242ac110002', 'АДМИНКА', '', true, 120, false);
INSERT INTO glass.users_module (id, name, description, is_title, number, deleted) VALUES ('ced87b7e-90fc-11e8-b376-0242ac110002', 'Пользователи', 'users', false, 130, false);
INSERT INTO glass.users_module (id, name, description, is_title, number, deleted) VALUES ('ced88a38-90fc-11e8-b376-0242ac110002', 'Разрешения', 'module_permission', false, 140, false);