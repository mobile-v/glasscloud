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
  id          uuid    not null constraint material_pkey primary key DEFAULT public.uuid_generate_v1(),
  depth       REAL    not null,
  length      REAL    not null,
  width       REAL    not null,
  price       DECIMAL not null,
  description varchar,
  color_id    uuid    not null references material_color (id),
  type_id     uuid    not null references material_type (id),
  company_id  uuid    not null references company (id),
  deleted     boolean not null                                      default false
);


INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de28566-90fb-11e8-a1cc-0242ac110002', 'Сатин б/цв', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de2a08c-90fb-11e8-a1cc-0242ac110002', 'Бронза', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de2b022-90fb-11e8-a1cc-0242ac110002', 'Серый', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de2bf9a-90fb-11e8-a1cc-0242ac110002', 'Сатин бронза', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de2cdc8-90fb-11e8-a1cc-0242ac110002', 'Лакобель', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de2dc28-90fb-11e8-a1cc-0242ac110002', 'Оптивайт', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de2eace-90fb-11e8-a1cc-0242ac110002', 'Стопсол', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de2f7b2-90fb-11e8-a1cc-0242ac110002', 'Черный', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de3045a-90fb-11e8-a1cc-0242ac110002', 'Dark', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de3109e-90fb-11e8-a1cc-0242ac110002', 'Grey', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de31ca6-90fb-11e8-a1cc-0242ac110002', 'Графит', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de328f4-90fb-11e8-a1cc-0242ac110002', 'Золото', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de3351a-90fb-11e8-a1cc-0242ac110002', 'Сантук', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de34258-90fb-11e8-a1cc-0242ac110002', 'Черный полимер', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de35266-90fb-11e8-a1cc-0242ac110002', 'Матовый', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_color (id, name, company_id, deleted) VALUES ('3de361ca-90fb-11e8-a1cc-0242ac110002', 'Б/цветный', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);

INSERT INTO glass.material_type (id, name, company_id, deleted) VALUES ('3de36fc6-90fb-11e8-a1cc-0242ac110002', 'СТЕКЛО ЛИСТОВОЕ', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_type (id, name, company_id, deleted) VALUES ('3de389ca-90fb-11e8-a1cc-0242ac110002', 'СТЕКЛО ТОНИРОВАННОЕ', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_type (id, name, company_id, deleted) VALUES ('3de39bcc-90fb-11e8-a1cc-0242ac110002', 'ОПТИВАЙТ', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_type (id, name, company_id, deleted) VALUES ('3de3aec8-90fb-11e8-a1cc-0242ac110002', 'САТИНАТ', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_type (id, name, company_id, deleted) VALUES ('3de3c3ae-90fb-11e8-a1cc-0242ac110002', 'ЗЕРКАЛО', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_type (id, name, company_id, deleted) VALUES ('3de3d79a-90fb-11e8-a1cc-0242ac110002', 'ЗЕРКАЛО ДЕКОРАТИВНОЕ', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);
INSERT INTO glass.material_type (id, name, company_id, deleted) VALUES ('3de3e852-90fb-11e8-a1cc-0242ac110002', 'ТРИПЛЕКС', 'da2e914c-9099-11e8-ba1b-0242ac110002', false);