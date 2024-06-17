CREATE TABLE employees
(
    id INTEGER NOT NULL
        CONSTRAINT employees_pk
            PRIMARY KEY,
    firstName TEXT,
    lastName TEXT,
    salary INTEGER,
    department TEXT
)