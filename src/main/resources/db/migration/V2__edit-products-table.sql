
BEGIN;
ALTER TABLE produtos DROP CONSTRAINT IF EXISTS produtos_codigo_barras_key;

ALTER TABLE produtos DROP COLUMN margem_lucro_percentual;


ALTER TABLE produtos ADD COLUMN valor_venda DECIMAL(10, 2) NOT NULL DEFAULT 0.00;


ALTER TABLE produtos RENAME COLUMN indice_refracao TO material_lente;

ALTER TABLE produtos ALTER COLUMN material_lente TYPE VARCHAR(50) USING material_lente::text;

COMMIT;

COMMENT ON COLUMN produtos.valor_venda IS 'Valor de venda final do produto, definido manualmente.';
COMMENT ON COLUMN produtos.material_lente IS 'Material da lente (ex: Poli, Orma, 1.67, 1.74). Substituiu a coluna indice_refracao.';