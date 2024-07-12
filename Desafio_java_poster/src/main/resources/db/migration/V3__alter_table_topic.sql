-- Adiciona a coluna status à tabela topicos
ALTER TABLE topicos
ADD COLUMN status BOOLEAN DEFAULT false;

-- Renomeia a coluna autor_status para autor na tabela topicos
ALTER TABLE topicos
RENAME COLUMN status_autor  TO autor;
