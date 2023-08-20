CREATE TABLE salary
(
    id            SERIAL PRIMARY KEY,
    salary_date   DATE NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    value         DECIMAL(7, 2) NOT NULL
);
