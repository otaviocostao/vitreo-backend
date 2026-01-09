
CREATE TABLE empresas (

    id UUID PRIMARY KEY,

    razao_social VARCHAR(255),
    nome_fantasia VARCHAR(255),

    cnpj VARCHAR(18) UNIQUE,

    inscricao_estadual VARCHAR(20),

    telefone_celular VARCHAR(20),
    telefone_fixo VARCHAR(20),
    email VARCHAR(255),

    url_logo VARCHAR(1024),

    endereco_logradouro VARCHAR(255),
    endereco_numero VARCHAR(20),
    endereco_complemento VARCHAR(100),
    endereco_bairro VARCHAR(100),
    endereco_cidade VARCHAR(100),
    endereco_uf VARCHAR(2),
    endereco_cep VARCHAR(10)
);

COMMENT ON TABLE empresas IS 'Armazena informações cadastrais e de configuração da empresa/organização.';