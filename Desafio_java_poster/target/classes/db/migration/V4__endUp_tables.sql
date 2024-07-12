
-- Remover tabelas especificadas
DROP TABLE IF EXISTS cursos;
DROP TABLE IF EXISTS respostas;
-- Remover a coluna repostas da tabela topicos
ALTER TABLE topicos
DROP COLUMN IF EXISTS repostas;