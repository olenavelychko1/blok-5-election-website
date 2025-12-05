CREATE TABLE constituency (
      id INT PRIMARY KEY AUTO_INCREMENT,
      name VARCHAR(50) NOT NULL,
      state_id BIGINT NOT NULL,
      metadata_id INT NULL,
  CONSTRAINT fk_constituency_state
      FOREIGN KEY (state_id) REFERENCES state(id),
  CONSTRAINT fk_constituency_metadata
      FOREIGN KEY (metadata_id) REFERENCES metadata(id) ON DELETE SET NULL
);