-- ==============================
-- Criação do Banco de Dados
-- ==============================
CREATE DATABASE IF NOT EXISTS SistemaEventos;
USE SistemaEventos;

-- ==============================
-- Tabela: Usuario
-- ==============================
CREATE TABLE Usuario (
    Usuario_id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL
);

-- ==============================
-- Tabela: Admin (herda de Usuario)
-- ==============================
CREATE TABLE Admin (
    Admin_id INT AUTO_INCREMENT PRIMARY KEY,
    Usuario_id INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    FOREIGN KEY (Usuario_id) REFERENCES Usuario(Usuario_id)
);

-- ==============================
-- Tabela: Visualizador (herda de Usuario)
-- ==============================
CREATE TABLE Visualizador (
    Visualizador_id INT AUTO_INCREMENT PRIMARY KEY,
    Usuario_id INT NOT NULL,
    FOREIGN KEY (Usuario_id) REFERENCES Usuario(Usuario_id)
);

-- ==============================
-- Tabela: Agenda
-- ==============================
CREATE TABLE Agenda (
    Agenda_id INT AUTO_INCREMENT PRIMARY KEY,
    Usuario_id INT NOT NULL,
    FOREIGN KEY (Usuario_id) REFERENCES Usuario(Usuario_id)
);

-- ==============================
-- Tabela: Evento
-- ==============================
CREATE TABLE Evento (
    Evento_id INT AUTO_INCREMENT PRIMARY KEY,
    Admin_id INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    dataInicio DATETIME NOT NULL,
    dataFim DATETIME NOT NULL,
    local VARCHAR(100),
    categoria VARCHAR(50),
    status VARCHAR(50),
    FOREIGN KEY (Admin_id) REFERENCES Admin(Admin_id)
);

-- ==============================
-- Tabela: Inscricao
-- ==============================
CREATE TABLE Inscricao (
    Inscricao_id INT AUTO_INCREMENT PRIMARY KEY,
    Usuario_id INT NOT NULL,
    Evento_id INT NOT NULL,
    status ENUM('Confirmado','Pendente','Cancelado') DEFAULT 'Pendente',
    dataInscricao DATETIME DEFAULT CURRENT_TIMESTAMP,
    descricao VARCHAR(255),
    tipo ENUM('Email','Sistema'),
    FOREIGN KEY (Usuario_id) REFERENCES Usuario(Usuario_id),
    FOREIGN KEY (Evento_id) REFERENCES Evento(Evento_id)
);

-- ==============================
-- Tabela: Notificacao
-- ==============================
CREATE TABLE Notificacao (
    Notificacao_id INT AUTO_INCREMENT PRIMARY KEY,
    Usuario_id INT NOT NULL,
    Evento_id INT NOT NULL,
    dataEnvio DATETIME DEFAULT CURRENT_TIMESTAMP,
    tipo ENUM('Email','SMS','Sistema'),
    descricao VARCHAR(255),
    FOREIGN KEY (Usuario_id) REFERENCES Usuario(Usuario_id),
    FOREIGN KEY (Evento_id) REFERENCES Evento(Evento_id)
);

CREATE TABLE HistoricoEventos (
    Usuario_id INT NOT NULL,
    Evento_id INT NOT NULL,
    status BIT NOT NULL,
    PRIMARY KEY(Usuario_id,Evento_id),
    FOREIGN KEY(Usuario_id) REFERENCES Usuario(Usuario_id),
    FOREIGN KEY(Evento_id) REFERENCES Evento(Evento_id)
);

-- =======================================
-- Inserindo 10 Usuários (5 admins, 5 visualizadores)
-- =======================================
INSERT INTO Usuario (nome, email, senha) VALUES
('Alice Silva', 'alice@email.com', 'senha123'),
('Bruno Costa', 'bruno@email.com', 'senha123'),
('Carla Lima', 'carla@email.com', 'senha123'),
('Daniel Souza', 'daniel@email.com', 'senha123'),
('Eduarda Melo', 'eduarda@email.com', 'senha123'),
('Felipe Rocha', 'felipe@email.com', 'senha123'),
('Gabriela Duarte', 'gabriela@email.com', 'senha123'),
('Henrique Torres', 'henrique@email.com', 'senha123'),
('Isabela Martins', 'isabela@email.com', 'senha123'),
('João Pedro', 'joao@email.com', 'senha123');

-- =======================================
-- Inserindo Admins (usuários 1 a 5)
-- =======================================
INSERT INTO Admin (Usuario_id, nome, email) VALUES
(1, 'Alice Silva', 'alice@email.com'),
(2, 'Bruno Costa', 'bruno@email.com'),
(3, 'Carla Lima', 'carla@email.com'),
(4, 'Daniel Souza', 'daniel@email.com'),
(5, 'Eduarda Melo', 'eduarda@email.com');

-- =======================================
-- Inserindo Visualizadores (usuários 6 a 10)
-- =======================================
INSERT INTO Visualizador (Usuario_id) VALUES
(6),
(7),
(8),
(9),
(10);

-- =======================================
-- Inserindo Agendas (uma para cada usuário)
-- =======================================
INSERT INTO Agenda (Usuario_id) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10);

-- =======================================
-- Inserindo Eventos (criados por Admins 1 a 5)
-- =======================================
INSERT INTO Evento (Admin_id, nome, descricao, dataInicio, dataFim, local, categoria, status) VALUES
(1, 'Workshop de Inovação', 'Evento sobre criatividade e inovação', '2025-11-10 09:00:00', '2025-11-10 17:00:00', 'São Paulo - SP', 'Tecnologia', 'Ativo'),
(2, 'Feira de Startups', 'Encontro de empreendedores e investidores', '2025-12-05 10:00:00', '2025-12-05 18:00:00', 'Belo Horizonte - MG', 'Negócios', 'Ativo'),
(3, 'Congresso de Educação', 'Palestras sobre métodos de ensino', '2026-01-15 08:00:00', '2026-01-16 17:00:00', 'Curitiba - PR', 'Educação', 'Ativo'),
(4, 'Hackathon 2026', 'Competição de programação intensiva', '2026-02-20 09:00:00', '2026-02-21 20:00:00', 'Recife - PE', 'Tecnologia', 'Planejado'),
(5, 'Encontro de Designers', 'Discussão sobre design gráfico e UX/UI', '2026-03-10 13:00:00', '2026-03-10 18:00:00', 'Porto Alegre - RS', 'Design', 'Ativo');