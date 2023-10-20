CREATE TABLE privilege (
  id smallint NOT NULL GENERATED ALWAYS AS IDENTITY ,
  name varchar(255) NOT NULL,
  CONSTRAINT privilege_pk PRIMARY KEY (id),
  CONSTRAINT privilege_name_unique UNIQUE (name)
);

CREATE TABLE role (
  id smallint NOT NULL GENERATED ALWAYS AS IDENTITY ,
  name varchar(255) NOT NULL,
  CONSTRAINT role_pk PRIMARY KEY (id),
  CONSTRAINT role_name_unique UNIQUE (name)
);

CREATE TABLE role_privilege (
  id serial NOT NULL,
  id_role smallint NOT NULL,
  id_privilege smallint NOT NULL,
  CONSTRAINT many_role_has_many_privilege_pk PRIMARY KEY (id)
);

CREATE TABLE account (
  id uuid NOT NULL,
  email varchar(512) NOT NULL,
  password varchar(512) NOT NULL,
  account_non_expired boolean NOT NULL DEFAULT false,
  account_non_locked boolean NOT NULL DEFAULT false,
  credentials_non_expired boolean NOT NULL DEFAULT false,
  enabled boolean NOT NULL DEFAULT false,
  CONSTRAINT account_unique_email UNIQUE (email),
  CONSTRAINT account_pk PRIMARY KEY (id)
);

CREATE TABLE account_role (
  id serial NOT NULL,
  id_account uuid NOT NULL,
  id_role smallint NOT NULL,
  CONSTRAINT many_account_has_many_role_pk PRIMARY KEY (id)

);

ALTER TABLE role_privilege ADD CONSTRAINT role_fk FOREIGN KEY (id_role)
    REFERENCES role (id) MATCH FULL
    ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE role_privilege ADD CONSTRAINT privilege_fk FOREIGN KEY (id_privilege)
    REFERENCES privilege (id) MATCH FULL
    ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE account_role ADD CONSTRAINT account_fk FOREIGN KEY (id_account)
    REFERENCES account (id) MATCH FULL
    ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE account_role ADD CONSTRAINT role_fk FOREIGN KEY (id_role)
    REFERENCES role (id) MATCH FULL
    ON DELETE RESTRICT ON UPDATE CASCADE;

