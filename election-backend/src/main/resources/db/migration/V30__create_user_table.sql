CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
   username VARCHAR(255) NOT NULL UNIQUE,
   email VARCHAR(255) NOT NULL UNIQUE,
   password VARCHAR(255) NOT NULL
);

INSERT INTO users (username, email, password)
VALUES ('Mai1202390', 'user@email.com', 'password1')
