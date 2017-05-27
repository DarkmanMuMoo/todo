CREATE TABLE todo (
     id BIGINT(20) NOT NULL AUTO_INCREMENT,
     subject VARCHAR(100) NOT NULL,
     content VARCHAR(1000),
     status TINYINT NOT NULL,
     PRIMARY KEY (id)    
); 