CREATE TABLE estado (
	codigo BIGINT(20) PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	sigla VARCHAR(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE cidade (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	codigo_estado BIGINT(20) NOT NULL,
	FOREIGN KEY (codigo_estado) REFERENCES estado(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO estado (codigo, nome, sigla) VALUES (1, 'Acre', 'AC');
INSERT INTO estado (codigo, nome, sigla) VALUES (2, 'Bahia', 'BA');
INSERT INTO estado (codigo, nome, sigla) VALUES (3, 'Ceará', 'CE');
INSERT INTO estado (codigo, nome, sigla) VALUES (4, 'Goiás', 'GO');
INSERT INTO estado (codigo, nome, sigla) VALUES (5, 'Minas Gerais', 'MG');
INSERT INTO estado (codigo, nome, sigla) VALUES (6, 'Santa Catarina', 'SC');
INSERT INTO estado (codigo, nome, sigla) VALUES (7, 'São Paulo', 'SP');

INSERT INTO cidade (nome, codigo_estado) VALUES ('Rio Branco', 1);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Cruzeiro do Sul', 1);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Salvador', 2);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Porto Seguro', 2);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Fortaleza', 3);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Maracanaú', 3);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Eusébio', 3);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Sobral', 3);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Santana', 2);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Goiânia', 4);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Itumbiara', 4);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Novo Brasil', 4);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Belo Horizonte', 5);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Uberlândia', 5);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Montes Claros', 5);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Florianópolis', 6);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Criciúma', 6);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Camboriú', 6);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Lages', 6);
INSERT INTO cidade (nome, codigo_estado) VALUES ('São Paulo', 7);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Ribeirão Preto', 7);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Campinas', 7);
INSERT INTO cidade (nome, codigo_estado) VALUES ('Santos', 7);