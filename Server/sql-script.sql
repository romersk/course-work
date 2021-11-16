CREATE DATABASE IF NOT EXISTS kp_edgar;
USE kp_edgar;

CREATE TABLE IF NOT EXISTS user_type (
	id INT AUTO_INCREMENT NOT NULL,
	id_person INT,
	login VARCHAR(50),
	pass VARCHAR(50),
	role VARCHAR(30),
	PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS work_place (
	id INT AUTO_INCREMENT NOT NULL,
	name VARCHAR(50),
	address VARCHAR(50),
	PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS person (
	id INT AUTO_INCREMENT NOT NULL,
	id_user INT,
	id_place INT,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	address VARCHAR(30),
	PRIMARY KEY(id),
	FOREIGN KEY(id_place) REFERENCES work_place(id)
);


ALTER TABLE user_type ADD FOREIGN KEY (id_person) REFERENCES person(id);
ALTER TABLE person ADD FOREIGN KEY (id_user) REFERENCES user_type(id);

CREATE TABLE IF NOT EXISTS salary (
	id INT AUTO_INCREMENT NOT NULL,
	size DECIMAL(10,2),
	method VARCHAR(30),
	PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS history (
	id INT AUTO_INCREMENT NOT NULL,
	id_user INT,
	id_salary INT,
	PRIMARY KEY(id),
	FOREIGN KEY(id_user) REFERENCES user_type(id),
	FOREIGN KEY(id_salary) REFERENCES salary(id)
);

INSERT INTO user_type(login, pass, role) VALUES ('root', 'root', 'Admin');
