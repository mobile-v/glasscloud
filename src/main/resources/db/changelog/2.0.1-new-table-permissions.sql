--liquibase formatted sql
--changeset minikh:2018-04-19_13:59 objectQuotingStrategy="QUOTE_ALL_OBJECTS" failOnError: true

create table PROCESS
(
  ID integer AUTO_INCREMENT PRIMARY KEY NOT NULL,
  CPU_ID integer NOT NULL,
  DATE_START BIGINT not null,
  DATE_STOP BIGINT not null default 0,
  COMMENT varchar(300),

  CONSTRAINT TAG_CPU FOREIGN KEY (CPU_ID) REFERENCES CPU (ID)
);

create index PROCESS_DATE_START_INDEX
	on PROCESS (DATE_START)
;

