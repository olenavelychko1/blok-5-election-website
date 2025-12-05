CREATE TABLE polling_station (
      id INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
      polling_station_id VARCHAR(25),
      name VARCHAR(255) NOT NULL,
      municipality_id BIGINT NOT NULL,
      metadata_id INT NULL,
      CONSTRAINT fk_polling_station_municipality
          FOREIGN KEY (municipality_id) REFERENCES municipality(id),
      CONSTRAINT fk_polling_station_metadata
          FOREIGN KEY (metadata_id) REFERENCES metadata(id) ON DELETE SET NULL
);