-- Diff date: 2020-05-06 16:09:39
-- Source model: new_database
-- Database: auth_service
-- PostgreSQL version: 12.0

-- [ Diff summary ]
-- Dropped objects: 0
-- Created objects: 12
-- Changed objects: 1
-- Truncated tables: 0

SET search_path=public,pg_catalog,auth_service;
-- ddl-end --


-- [ Created objects ] --
-- object: auth_service | type: SCHEMA --
-- DROP SCHEMA IF EXISTS auth_service CASCADE;
CREATE SCHEMA auth_service;
-- ddl-end --
ALTER SCHEMA auth_service OWNER TO auth_service;
-- ddl-end --

-- object: auth_service.privilege | type: TABLE --
-- DROP TABLE IF EXISTS auth_service.privilege CASCADE;
CREATE TABLE auth_service.privilege (
                                        id smallint NOT NULL GENERATED ALWAYS AS IDENTITY ,
                                        name varchar(255) NOT NULL,
                                        CONSTRAINT privilege_pk PRIMARY KEY (id),
                                        CONSTRAINT privilege_name_unique UNIQUE (name)

);
-- ddl-end --
ALTER TABLE auth_service.privilege OWNER TO auth_service;
-- ddl-end --

-- object: auth_service.role | type: TABLE --
-- DROP TABLE IF EXISTS auth_service.role CASCADE;
CREATE TABLE auth_service.role (
                                   id smallint NOT NULL GENERATED ALWAYS AS IDENTITY ,
                                   name varchar(255) NOT NULL,
                                   CONSTRAINT role_pk PRIMARY KEY (id),
                                   CONSTRAINT role_name_unique UNIQUE (name)

);
-- ddl-end --
ALTER TABLE auth_service.role OWNER TO auth_service;
-- ddl-end --

-- object: auth_service.role_privilege | type: TABLE --
-- DROP TABLE IF EXISTS auth_service.role_privilege CASCADE;
CREATE TABLE auth_service.role_privilege (
                                             id serial NOT NULL,
                                             id_role smallint NOT NULL,
                                             id_privilege smallint NOT NULL,
                                             CONSTRAINT many_role_has_many_privilege_pk PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE auth_service.role_privilege OWNER TO auth_service;
-- ddl-end --

-- object: auth_service.account | type: TABLE --
-- DROP TABLE IF EXISTS auth_service.account CASCADE;
CREATE TABLE auth_service.account (
                                      id uuid NOT NULL,
                                      email varchar(512) NOT NULL,
                                      password varchar(512) NOT NULL,
                                      account_non_expired boolean NOT NULL DEFAULT false,
                                      account_non_locked boolean NOT NULL DEFAULT false,
                                      credentials_non_expired boolean NOT NULL DEFAULT false,
                                      enabled boolean NOT NULL DEFAULT false,
                                      CONSTRAINT user_unique_email UNIQUE (email),
                                      CONSTRAINT user_pk PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE auth_service.account OWNER TO auth_service;
-- ddl-end --

-- object: auth_service.account_role | type: TABLE --
-- DROP TABLE IF EXISTS auth_service.account_role CASCADE;
CREATE TABLE auth_service.account_role (
                                           id serial NOT NULL,
                                           id_account uuid NOT NULL,
                                           id_role smallint NOT NULL,
                                           CONSTRAINT many_user_has_many_role_pk PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE auth_service.account_role OWNER TO auth_service;
-- ddl-end --

-- [ Created foreign keys ] --
-- object: role_fk | type: CONSTRAINT --
-- ALTER TABLE auth_service.role_privilege DROP CONSTRAINT IF EXISTS role_fk CASCADE;
ALTER TABLE auth_service.role_privilege ADD CONSTRAINT role_fk FOREIGN KEY (id_role)
    REFERENCES auth_service.role (id) MATCH FULL
    ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: privilege_fk | type: CONSTRAINT --
-- ALTER TABLE auth_service.role_privilege DROP CONSTRAINT IF EXISTS privilege_fk CASCADE;
ALTER TABLE auth_service.role_privilege ADD CONSTRAINT privilege_fk FOREIGN KEY (id_privilege)
    REFERENCES auth_service.privilege (id) MATCH FULL
    ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: account_fk | type: CONSTRAINT --
-- ALTER TABLE auth_service.account_role DROP CONSTRAINT IF EXISTS account_fk CASCADE;
ALTER TABLE auth_service.account_role ADD CONSTRAINT account_fk FOREIGN KEY (id_account)
    REFERENCES auth_service.account (id) MATCH FULL
    ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: role_fk | type: CONSTRAINT --
-- ALTER TABLE auth_service.account_role DROP CONSTRAINT IF EXISTS role_fk CASCADE;
ALTER TABLE auth_service.account_role ADD CONSTRAINT role_fk FOREIGN KEY (id_role)
    REFERENCES auth_service.role (id) MATCH FULL
    ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

