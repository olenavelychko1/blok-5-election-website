CREATE TABLE party_vote (
    id INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    region_type ENUM('NATIONAL','CONSTITUENCY','MUNICIPALITY','POLLING_STATION'),
    region_id INT,
    party_id INT,
    votes INT
)