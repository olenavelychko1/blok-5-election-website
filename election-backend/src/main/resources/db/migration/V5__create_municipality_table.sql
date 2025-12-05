CREATE TABLE municipality (
      id INT PRIMARY KEY AUTO_INCREMENT,
      name VARCHAR(50) NOT NULL,
      constituency_id BIGINT NOT NULL,
      metadata_id INT NULL,
      CONSTRAINT fk_municipality_constituency
          FOREIGN KEY (constituency_id) REFERENCES constituency(id),
      CONSTRAINT fk_municipality_metadata
          FOREIGN KEY (metadata_id) REFERENCES metadata(id) ON DELETE SET NULL
);