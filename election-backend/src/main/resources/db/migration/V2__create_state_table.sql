CREATE TABLE state (
       id INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
       name VARCHAR(50) NOT NULL,
       election_id VARCHAR(10) UNIQUE,
       CONSTRAINT fk_state_election FOREIGN KEY (election_id) REFERENCES election(id),
       metadata_id INT
);