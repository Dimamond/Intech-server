-- liquibase formatted sql

--changeset staryginda:init_schemas context:common failOnError:true
CREATE TABLE servertest.users (
  id bigserial PRIMARY KEY,
  login  varchar(64) UNIQUE
);

CREATE TABLE servertest.content (
  id bigserial PRIMARY KEY,
  name  varchar(64) UNIQUE
);

CREATE TABLE servertest.user_content (
    id bigserial PRIMARY KEY,
    user_id bigint references servertest.users(id),
    content_id bigint references servertest.content(id)

);

