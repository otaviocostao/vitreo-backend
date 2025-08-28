CREATE TABLE clientes (
    id UUID PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    sobrenome VARCHAR(100) NOT NULL,
    nome_completo VARCHAR(201) NOT NULL,
    cpf VARCHAR(20) UNIQUE,
    data_nascimento DATE,
    email VARCHAR(100) UNIQUE,
    telefone VARCHAR(20),
    logradouro VARCHAR(255),
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(50),
    cep VARCHAR(10),
    data_cadastro TIMESTAMP NOT NULL
);

CREATE TABLE receituarios(
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL,
    esferico_od DECIMAL(4,2),
    cilindrico_od DECIMAL(4,2),
    eixo_od INT,
    esferico_oe DECIMAL(4,2),
    cilindrico_oe DECIMAL(4,2),
    eixo_oe INT,
    adicao DECIMAL(4,2),
    distancia_pupilar DECIMAL (4,2),
    dnp_od DECIMAL (5,2),
    dnp_oe DECIMAL (5,2),
    centro_optico_od DECIMAL (5,2),
    centro_optico_oe DECIMAL (5,2),
    angulo_maior DECIMAL (5,2),
    ponte_aro DECIMAL (5,2),
    angulo_vertical DECIMAL (5,2),

    nome_medico VARCHAR(100) NOT NULL,
    crm_medico VARCHAR(20) NOT NULL,
    data_receita DATE NOT NULL,

    data_cadastro DATE NOT NULL,

    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE fornecedores (
    id UUID PRIMARY KEY,
    razao_social VARCHAR(100) NOT NULL,
    nome_fantasia VARCHAR(100) NOT NULL,
    cnpj VARCHAR(20) UNIQUE,
    inscricao_estadual VARCHAR(20) UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(100),
    logradouro VARCHAR(255),
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(50),
    cep VARCHAR(10),
    data_cadastro DATE NOT NULL
);

CREATE TABLE marcas (
    id UUID PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE produtos (
    tipo_produto VARCHAR(50) NOT NULL,

    id UUID PRIMARY KEY,

    fornecedor_id UUID NOT NULL,
    marca_id UUID,

    nome VARCHAR(255) NOT NULL,
    referencia VARCHAR(255),
    codigo_barras VARCHAR(255) UNIQUE,
    custo DECIMAL(10, 2),
    margem_lucro_percentual DECIMAL(5, 2),
    quantidade_estoque INTEGER,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    cor VARCHAR(50),
    material VARCHAR(50),
    tamanho VARCHAR(20),

    indice_refracao DECIMAL(4, 2),
    tratamento VARCHAR(100),
    tipo_lente VARCHAR(50),

    FOREIGN KEY (fornecedor_id) REFERENCES fornecedores(id),
    FOREIGN KEY (marca_id) REFERENCES marcas(id)
);

CREATE TABLE pedidos(
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL,
    receituario_id UUID,
    ordem_servico INTEGER UNIQUE,
    data_pedido TIMESTAMP NOT NULL,
    data_previsao_entrega DATE,
    data_entrega DATE,
    valor_armacao DECIMAL(10,2),
    valor_lentes DECIMAL(10,2),
    valor_total DECIMAL(10,2),
    valor_final DECIMAL(10,2),
    desconto DECIMAL(10,2),

    status VARCHAR(50) NOT NULL,

    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (receituario_id) REFERENCES receituarios(id)
);

CREATE TABLE itens_pedido (
    id UUID PRIMARY KEY,
    pedido_id UUID NOT NULL,
    produto_id UUID NOT NULL,

    quantidade INTEGER NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,

    FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produtos(id)
);

CREATE TABLE pagamentos (
    id UUID PRIMARY KEY,
    pedido_id UUID NOT NULL,

    forma_pagamento VARCHAR(50) NOT NULL,
    valor_pago DECIMAL(10, 2) NOT NULL,
    numero_parcelas INTEGER NOT NULL DEFAULT 1,
    data_pagamento TIMESTAMP NOT NULL,


    FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE
);

CREATE INDEX idx_pedidos_cliente_id ON pedidos(cliente_id);
CREATE INDEX idx_itens_pedido_pedido_id ON itens_pedido(pedido_id);
CREATE INDEX idx_pagamentos_pedido_id ON pagamentos(pedido_id);





CREATE TABLE fornecedores_marcas (
    fornecedor_id UUID NOT NULL,
    marca_id UUID NOT NULL,

    PRIMARY KEY (fornecedor_id, marca_id),

    FOREIGN KEY (fornecedor_id) REFERENCES fornecedores(id),
    FOREIGN KEY (marca_id) REFERENCES marcas(id)
);

CREATE INDEX idx_produtos_fornecedor_id ON produtos(fornecedor_id);
CREATE INDEX idx_produtos_marca_id ON produtos(marca_id);