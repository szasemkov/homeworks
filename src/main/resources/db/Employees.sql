CREATE TABLE employees
(
    id INTEGER NOT NULL
        CONSTRAINT employees_pk
            PRIMARY KEY,
    first_name TEXT,
    last_name TEXT,
    salary INTEGER,
    department TEXT
)