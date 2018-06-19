CREATE TABLE tokens (
  application VARCHAR(50) NOT NULL ,
  api_token VARCHAR(256) NOT NULL,
  enabled INT NOT NULL
);

ALTER TABLE tokens
  ADD CONSTRAINT token_pk PRIMARY KEY (application, api_token);

ALTER TABLE tokens
  ADD CONSTRAINT token_uk UNIQUE (api_token);


CREATE TABLE users (
  id BIGINT NOT NULL,
  username VARCHAR(50) NOT NULL ,
  password VARCHAR(256) NOT NULL,
  enabled INT NOT NULL
);

ALTER TABLE users
  ADD CONSTRAINT users_pk PRIMARY KEY (id);
 
ALTER TABLE users
  ADD CONSTRAINT users_uk UNIQUE (username);
 
 
CREATE TABLE roles (
  id BIGINT NOT NULL,
  rolename VARCHAR(50) NOT NULL
);

ALTER TABLE roles
  ADD CONSTRAINT roles_pk PRIMARY KEY (id);
 
ALTER TABLE roles
  ADD CONSTRAINT roles_uk UNIQUE (rolename);
 
 
CREATE TABLE user_role (
  ID BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL
);

ALTER TABLE user_role
  ADD CONSTRAINT user_role_pk PRIMARY KEY (id);
 
ALTER TABLE user_role
  ADD CONSTRAINT user_role_uk UNIQUE (user_id, role_id);
 
ALTER TABLE user_role
  ADD CONSTRAINT USER_ROLE_FK1 foreign key (user_id) REFERENCES users (id);
 
ALTER TABLE user_role
  ADD CONSTRAINT user_role_fk2 FOREIGN KEY (role_id) REFERENCES roles (id);

INSERT INTO tokens (application, api_token, enabled)
VALUES ('erp', 'bf9063f1-48ef-443c-b814-d8ab1b512290', 1);

INSERT INTO users (id, username, password, enabled)
VALUES (2, 'user1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
 
INSERT INTO users (id, username, password, enabled)
VALUES (1, 'admin1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
 
INSERT INTO roles (id, rolename)
VALUES (1, 'ROLE_ADMIN');
 
INSERT INTO roles (id, rolename)
VALUES (2, 'ROLE_USER');
 
INSERT INTO user_role (id, user_id, role_id)
VALUES (1, 1, 1);
 
INSERT INTO user_role (id, user_id, role_id)
VALUES (2, 1, 2);
 
INSERT INTO user_role (id, user_id, role_id)
VALUES (3, 2, 2);

COMMIT;