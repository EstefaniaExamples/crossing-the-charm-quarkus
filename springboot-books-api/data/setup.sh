#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE TABLE IF NOT EXISTS "books" ("id"   SERIAL , "title" VARCHAR(255), "description" VARCHAR(500), "author" VARCHAR(255), PRIMARY KEY ("id"));
    INSERT INTO books (title, description, author) VALUES ('R2dbc is refined', 'R2dbc is now part of Spring framework core', 'Estefania');
EOSQL