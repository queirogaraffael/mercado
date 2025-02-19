CREATE TABLE produto(
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    marca_id INTEGER NOT NULL,
    FOREIGN KEY (marca_id) REFERENCES marca(id)
);