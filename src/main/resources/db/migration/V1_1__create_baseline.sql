CREATE TABLE daily_cost
(
    id            SERIAL PRIMARY KEY,
    cost_date     DATE,
    creation_date TIMESTAMP,
    last_modified TIMESTAMP,
    value         DECIMAL(7, 2)
);
