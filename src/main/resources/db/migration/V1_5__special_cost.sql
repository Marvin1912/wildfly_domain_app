CREATE TABLE special_cost
(
    id            SERIAL PRIMARY KEY,
    cost_date     DATE NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    last_modified TIMESTAMP NOT NULL
);

CREATE TABLE special_cost_entry
(
    id              SERIAL PRIMARY KEY,
    description     VARCHAR(2048) NOT NULL,
    creation_date   TIMESTAMP NOT NULL,
    last_modified   TIMESTAMP NOT NULL,
    value           DECIMAL(7, 2) NOT NULL,
    special_cost_id INT NOT NULL
);

ALTER TABLE special_cost_entry
    ADD CONSTRAINT fk_special_cost FOREIGN KEY (special_cost_id)
        REFERENCES special_cost (id)
        ON DELETE CASCADE;
