-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.2-beta2
-- PostgreSQL version: 12.0
-- Project Site: pgmodeler.io
-- Model Author: Alex

-- object: auth_service | type: ROLE --
-- DROP ROLE IF EXISTS auth_service;
-- CREATE ROLE auth_service WITH
   -- INHERIT
   -- LOGIN
   -- ENCRYPTED PASSWORD 'auth_service';
-- ddl-end --


-- Database creation must be done outside a multicommand file.
-- These commands were put in this file only as a convenience.
-- -- object: new_database | type: DATABASE --
-- -- DROP DATABASE IF EXISTS new_database;
-- CREATE DATABASE new_database;
-- -- ddl-end --
--

-- object: auth_service | type: SCHEMA --
DROP SCHEMA IF EXISTS auth_service CASCADE;
CREATE SCHEMA auth_service;
-- ddl-end --
ALTER SCHEMA auth_service OWNER TO auth_service;
-- ddl-end --

SET search_path TO pg_catalog,public,auth_service;
-- ddl-end --

-- object: auth_service.privilege | type: TABLE --
-- DROP TABLE IF EXISTS auth_service.privilege CASCADE;
CREATE TABLE auth_service.privilege (
                                        id smallint NOT NULL,
                                        name varchar(255) NOT NULL,
                                        CONSTRAINT privilege_pk PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE auth_service.privilege OWNER TO auth_service;
-- ddl-end --

-- object: auth_service.role | type: TABLE --
-- DROP TABLE IF EXISTS auth_service.role CASCADE;
CREATE TABLE auth_service.role (
                                   id smallint NOT NULL,
                                   name varchar(255) NOT NULL,
                                   CONSTRAINT role_pk PRIMARY KEY (id)

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

-- object: auth_service."user" | type: TABLE --
-- DROP TABLE IF EXISTS auth_service."user" CASCADE;
CREATE TABLE auth_service."user" (
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
ALTER TABLE auth_service."user" OWNER TO auth_service;
-- ddl-end --

-- object: auth_service.user_role | type: TABLE --
-- DROP TABLE IF EXISTS auth_service.user_role CASCADE;
CREATE TABLE auth_service.user_role (
                                        id serial NOT NULL,
                                        id_user uuid NOT NULL,
                                        id_role smallint NOT NULL,
                                        CONSTRAINT many_user_has_many_role_pk PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE auth_service.user_role OWNER TO auth_service;
-- ddl-end --

-- object: user_fk | type: CONSTRAINT --
-- ALTER TABLE auth_service.user_role DROP CONSTRAINT IF EXISTS user_fk CASCADE;
ALTER TABLE auth_service.user_role ADD CONSTRAINT user_fk FOREIGN KEY (id_user)
    REFERENCES auth_service."user" (id) MATCH FULL
    ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: role_fk | type: CONSTRAINT --
-- ALTER TABLE auth_service.user_role DROP CONSTRAINT IF EXISTS role_fk CASCADE;
ALTER TABLE auth_service.user_role ADD CONSTRAINT role_fk FOREIGN KEY (id_role)
    REFERENCES auth_service.role (id) MATCH FULL
    ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --


