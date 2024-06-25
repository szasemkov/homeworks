--liquibase formatted sql

-- changeset szasemkov:1

CREATE TABLE employees
(
    id INTEGER NOT NULL
        CONSTRAINT employees_pk
            PRIMARY KEY,
    first_name TEXT,
    last_name TEXT,
    salary INTEGER,
    department TEXT
);

CREATE TABLE payments
(
    id INTEGER NOT NULL
        CONSTRAINT payments_pk
            PRIMARY KEY,
    employee_id INTEGER,
    amount INTEGER,
    date DATE,
    status BOOLEAN
);

CREATE SEQUENCE seq_employee START 1;
CREATE SEQUENCE seq_payment START 1;
