CREATE TABLE daily_cost
(
    id            SERIAL PRIMARY KEY,
    cost_date     DATE NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    last_modified TIMESTAMP NOT NULL,
    value         DECIMAL(7, 2) NOT NULL
);
