CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL
);
CREATE TABLE perfil (
    id_perfil SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    fk_id_usuario INTEGER NOT NULL,
    FOREIGN KEY (fk_id_usuario) REFERENCES usuario(id_usuario)
);
CREATE TABLE cursos (
    id_curso SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    categoria VARCHAR(255)
);

CREATE TABLE topicos (
    id_topico SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status_autor VARCHAR(255) NOT NULL,
    repostas INTEGER DEFAULT 0,
    fk_id_curso INTEGER,
    fk_id_usuario INTEGER NOT NULL,
    FOREIGN KEY (fk_id_curso) REFERENCES cursos(id_curso),
    FOREIGN KEY (fk_id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE respostas (
    id_resposta SERIAL PRIMARY KEY,
    fk_id_topico INTEGER NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    autor VARCHAR(255) NOT NULL,
    solucao BOOLEAN DEFAULT FALSE,
    fk_id_usuario INTEGER NOT NULL,
    FOREIGN KEY (fk_id_topico) REFERENCES topicos(id_topico),
    FOREIGN KEY (fk_id_usuario) REFERENCES usuario(id_usuario)
);
