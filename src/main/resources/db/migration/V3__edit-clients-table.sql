
BEGIN;

ALTER TABLE clientes DROP CONSTRAINT IF EXISTS clientes_cpf_key;

ALTER TABLE clientes DROP CONSTRAINT IF EXISTS clientes_email_key;

COMMIT;

COMMENT ON COLUMN clientes.cpf IS 'A restrição UNIQUE foi removida.';
COMMENT ON COLUMN clientes.email IS 'A restrição UNIQUE foi removida.';