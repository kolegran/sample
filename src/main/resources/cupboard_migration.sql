CREATE TABLE cupboard (
    id SERIAL UNIQUE PRIMARY KEY,
    title CHARACTER VARYING(32)
);

INSERT INTO cupboard (title) VALUES
('physics laboratory'),
('chemistry laboratory'),
('drug laboratory');