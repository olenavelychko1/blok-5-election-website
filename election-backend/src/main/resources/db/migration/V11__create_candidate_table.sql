CREATE TABLE candidate (
   id INT PRIMARY KEY AUTO_INCREMENT,
   cid INT,
   initials VARCHAR(20),
   first_name VARCHAR(50),
   last_name VARCHAR(50),
   gender VARCHAR(10),
   locality VARCHAR(100),
   elected BOOLEAN DEFAULT FALSE,
   party_id VARCHAR(10) NOT NULL,
    CONSTRAINT fk_candidate_party FOREIGN KEY (party_id) REFERENCES party(id)
);
