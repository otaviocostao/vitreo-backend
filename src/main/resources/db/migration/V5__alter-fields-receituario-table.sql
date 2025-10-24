
BEGIN;
ALTER TABLE receituarios ALTER COLUMN nome_medico DROP NOT NULL;

ALTER TABLE receituarios ALTER COLUMN crm_medico DROP NOT NULL;

ALTER TABLE receituarios ALTER COLUMN data_receita DROP NOT NULL;

COMMIT;

COMMENT ON COLUMN receituarios.nome_medico IS 'Nome do médico (opcional).';
COMMENT ON COLUMN receituarios.crm_medico IS 'CRM do médico (opcional).';
COMMENT ON COLUMN receituarios.data_receita IS 'Data de emissão da receita (opcional).';