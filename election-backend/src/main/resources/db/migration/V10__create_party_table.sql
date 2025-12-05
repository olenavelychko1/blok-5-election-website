CREATE TABLE party (
   id INT PRIMARY KEY AUTO_INCREMENT,
   pid INT,
   name VARCHAR(50) NOT NULL,
   seats INT DEFAULT 0,
   election_id VARCHAR(10),
   CONSTRAINT fk_party_election FOREIGN KEY (election_id) REFERENCES election(id)
);
