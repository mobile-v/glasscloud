--liquibase formatted sql
--changeset minikh:2018-07-12_23-40 objectQuotingStrategy="QUOTE_ALL_OBJECTS" failOnError: true

create table IF NOT EXISTS "user"
(
  id         uuid                  not null constraint user_pkey primary key DEFAULT public.uuid_generate_v1(),
  NAME       VARCHAR               not null unique,
  LOGIN      VARCHAR               not null,
  PASSWORD   VARCHAR               not null,
  ROLE       VARCHAR               not null,
  EMAIL      VARCHAR               not null,
  PHONE      VARCHAR               not null,
  COMMENT    VARCHAR,
  ENABLED    BOOLEAN default FALSE not null,
  company_id uuid                  not null references company (id),
  deleted    boolean               not null                                  default false
);

-- admin/admin
INSERT INTO "user" (id, name, login, password, role, enabled, company_id, EMAIL, PHONE)
VALUES ('d4b6c666-909a-11e8-876c-0242ac110002',
        'admin',
        'admin',
        '68f03c3c59929248a4c8caffaa4bf5d5d8f05ec6e1b67b4b8078c7ee347d2f36c79c20569dd22b0c',
        'ROLE_ADMIN',
        true,
        'da2e914c-9099-11e8-ba1b-0242ac110002',
        'minikh@mail.ru',
        '911');
INSERT INTO "user" (id, name, login, password, role, enabled, company_id, EMAIL, PHONE)
VALUES ('d4b6f7b2-909a-11e8-876c-0242ac110002',
        'user',
        'user',
        '4df0a62b31a9171c070d15a48fc6149c261875244a70b1ea31c25d5f937af720fbaa4765e619f73d',
        'ROLE_USER',
        true,
        'da2e914c-9099-11e8-ba1b-0242ac110002',
        'minikh@mail.ru',
        '001');
