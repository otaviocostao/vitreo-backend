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

    logradouro VARCHAR(255),
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(50),
    cep VARCHAR(10)
);

COMMENT ON TABLE empresas IS 'Armazena informações cadastrais e de configuração da empresa/otica.';