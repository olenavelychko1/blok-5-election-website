CREATE TABLE uncounted_votes (
    id INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    metadata_id INT NOT NULL,
    vote_reason VARCHAR(255) NOT NULL, -- better than listing all the reasons in separate columns, in case a new reason is added
    vote_count INT NOT NULL,
    CONSTRAINT fk_uncounted_votes_metadata
        FOREIGN KEY (metadata_id) REFERENCES metadata(id) ON DELETE CASCADE,
    CONSTRAINT unq_metadata_reason UNIQUE (metadata_id, vote_reason) -- prevents duplicate votes per reason per metadata

);