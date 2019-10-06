-- liquibase formatted sql

--changeset staryginda:add_data context:common failOnError:true

INSERT INTO servertest.users(id, login) VALUES(1,'User 1');
INSERT INTO servertest.users(id, login) VALUES(2,'User 2');
INSERT INTO servertest.users(id, login) VALUES(3,'User 3');

INSERT INTO servertest.content(id, name) VALUES(1,'Content 1');
INSERT INTO servertest.content(id, name) VALUES(2,'Content 2');
INSERT INTO servertest.content(id, name) VALUES(3,'Content 3');

INSERT INTO servertest.user_content(user_id, content_id) VALUES(1, 1);

INSERT INTO servertest.user_content(user_id, content_id) VALUES(2, 2);
INSERT INTO servertest.user_content(user_id, content_id) VALUES(2, 2);
INSERT INTO servertest.user_content(user_id, content_id) VALUES(2, 3);




