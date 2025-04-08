-- Enable dblink extension
CREATE EXTENSION IF NOT EXISTS dblink;

-- In init.sql
CREATE EXTENSION IF NOT EXISTS pgcrypto; -- Example extension

-- Create the database directly
CREATE DATABASE donor_service_db;