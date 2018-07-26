--liquibase formatted sql
--changeset minikh:2018-07-12_23-39 objectQuotingStrategy="QUOTE_ALL_OBJECTS" failOnError: true

create extension if not exists "uuid-ossp" SCHEMA public;