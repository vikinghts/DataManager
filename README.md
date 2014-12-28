DataManager
=============

Store and retrieve the SmartMeter data.

Got a lot of my inspiration from this website:
http://crunchify.com/create-very-simple-jersey-rest-service-and-send-json-data-from-java-client/
and here:
http://www.mkyong.com/webservices/jax-rs/jersey-hello-world-example/

For the hibernate part i got inspiration here:
http://www.tutorialspoint.com/hibernate/hibernate_examples.htm


database details:
CREATE DATABASE datamanagerdb
  WITH OWNER =  datamanageruser
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       CONNECTION LIMIT = -1;
GRANT ALL ON DATABASE datamanagerdb TO datamanageruser;
GRANT CONNECT, TEMPORARY ON DATABASE datamanagerdb TO public;

CREATE ROLE datamanageruser LOGIN
NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;

alter user datamanageruser with password "bla"

to select the database
\c datamanagerdb
