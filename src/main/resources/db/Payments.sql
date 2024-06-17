CREATE TABLE payments
(
    id INTEGER NOT NULL
        CONSTRAINT payments_pk
            PRIMARY KEY,
    employeeId INTEGER,
    amount INTEGER,
    date DATE,
    status BOOLEAN
)