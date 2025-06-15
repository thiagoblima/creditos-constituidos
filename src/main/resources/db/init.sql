-- Create the schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS public;

-- Create the credito table
CREATE TABLE IF NOT EXISTS credito (
    id SERIAL PRIMARY KEY,
    numero_credito VARCHAR(50) NOT NULL UNIQUE,
    numero_nfse VARCHAR(50) NOT NULL,
    data_constituicao DATE NOT NULL,
    valor_issqn DECIMAL(15, 2) NOT NULL,
    tipo_credito VARCHAR(50) NOT NULL,
    simples_nacional BOOLEAN NOT NULL,
    aliquota DECIMAL(5, 2) NOT NULL,
    valor_faturado DECIMAL(15, 2) NOT NULL,
    valor_deducao DECIMAL(15, 2) NOT NULL,
    base_calculo DECIMAL(15, 2) NOT NULL
);

-- Create index for faster searches
CREATE INDEX IF NOT EXISTS idx_credito_numero_nfse ON credito(numero_nfse);
CREATE INDEX IF NOT EXISTS idx_credito_numero_credito ON credito(numero_credito);

-- Insert sample data
INSERT INTO credito (
    numero_credito, 
    numero_nfse, 
    data_constituicao, 
    valor_issqn, 
    tipo_credito, 
    simples_nacional, 
    aliquota, 
    valor_faturado, 
    valor_deducao, 
    base_calculo
) VALUES 
('CR001', 'NFS001', '2024-01-15', 1500.75, 'ISSQN', true, 5.00, 30000.00, 5000.00, 25000.00),
('CR002', 'NFS002', '2024-01-20', 2250.50, 'ISSQN', false, 5.00, 45000.00, 0.00, 45000.00),
('CR003', 'NFS003', '2024-02-05', 750.25, 'ISSQN', true, 3.00, 25000.00, 0.00, 25000.00),
('CR004', 'NFS001', '2024-02-10', 1800.00, 'ISSQN', true, 5.00, 36000.00, 0.00, 36000.00),
('CR005', 'NFS004', '2024-02-15', 3500.00, 'ISSQN', false, 5.00, 70000.00, 10000.00, 60000.00),
('CR006', 'NFS005', '2024-03-01', 1250.00, 'ISSQN', true, 5.00, 25000.00, 2500.00, 22500.00),
('CR007', 'NFS006', '2024-03-10', 4000.00, 'ISSQN', false, 5.00, 80000.00, 0.00, 80000.00),
('CR008', 'NFS007', '2024-03-15', 900.00, 'ISSQN', true, 3.00, 30000.00, 0.00, 30000.00),
('CR009', 'NFS008', '2024-03-20', 2100.00, 'ISSQN', false, 5.00, 42000.00, 5000.00, 37000.00),
('CR010', 'NFS009', '2024-03-25', 1650.00, 'ISSQN', true, 5.00, 33000.00, 3000.00, 30000.00);

-- Grant privileges
ALTER TABLE credito OWNER TO postgres;
