CREATE TABLE payments
(
    id INTEGER NOT NULL
        CONSTRAINT payments_pk
            PRIMARY KEY,
    employee_id INTEGER,
    amount INTEGER,
    date DATE,
    status BOOLEAN
)