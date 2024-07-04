CREATE DATABASE mercado;

USE mercado;

CREATE TABLE  produto (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50),
    valor VARCHAR(10),
    marca VARCHAR(20)
);