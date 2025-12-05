CREATE TABLE metadata (
    id INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    region_type ENUM('NATIONAL','CONSTITUENCY','MUNICIPALITY','POLLING_STATION'),
    region_id VARCHAR(25),
    total_cast INT,
    total_counted INT,
    invalid INT,
    blank INT
)