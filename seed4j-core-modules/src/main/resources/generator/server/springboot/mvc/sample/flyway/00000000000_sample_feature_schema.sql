CREATE TABLE beers
(
  id            BINARY(16) NOT NULL PRIMARY KEY,
  name          VARCHAR(255) NOT NULL,
  unit_price    DECIMAL(10,2) NOT NULL,
  selling_state VARCHAR(20) NOT NULL
);

CREATE INDEX idx_beers_selling_state ON beers (selling_state);
