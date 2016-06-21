# --- !Ups

CREATE TABLE knowledge (
  id INT NOT NULL AUTO_INCREMENT,
  knowledge TEXT NOT NULL,
  PRIMARY KEY (id)
);

# --- !Downs
DROP TABLE knowledge;
