BEGIN;

ALTER TABLE clientes ADD COLUMN rg VARCHAR(20);

ALTER TABLE clientes ADD COLUMN telefone_secundario VARCHAR(20);

ALTER TABLE clientes
ADD COLUMN genero VARCHAR(50);

ALTER TABLE clientes
ADD COLUMN naturalidade VARCHAR(100);

ALTER TABLE clientes
ADD COLUMN observacoes TEXT;

COMMIT;

COMMENT ON COLUMN clientes.telefone_secundario IS 'Telefone de contato secundário do cliente.';
COMMENT ON COLUMN clientes.genero IS 'Gênero do cliente.';
COMMENT ON COLUMN clientes.naturalidade IS 'Cidade/Estado de nascimento do cliente.';
COMMENT ON COLUMN clientes.observacoes IS 'Campo de texto livre para anotações sobre o cliente.';