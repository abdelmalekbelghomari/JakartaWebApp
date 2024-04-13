-- Create 'Adherent' table
CREATE TABLE Adherent (
    numeroAdherent SERIAL PRIMARY KEY,
    Nom VARCHAR(255),
    Prenom VARCHAR(255),
    Adresse TEXT,
    Telephone VARCHAR(20),
    Email VARCHAR(255),
    Password VARCHAR(255)
);

-- Create 'Tournoi' table
CREATE TABLE Tournoi (
    codeTournoi SERIAL PRIMARY KEY,
    Nom VARCHAR(255),
    Date DATE,
    Lieu VARCHAR(255)
);

-- Create 'Inscription' table
CREATE TABLE Inscription (
    numeroAdherent INTEGER,
    codeTournoi INTEGER,
    DateInscription DATE,
    PRIMARY KEY (numeroAdherent, codeTournoi),
    FOREIGN KEY (numeroAdherent) REFERENCES Adherent (numeroAdherent),
    FOREIGN KEY (codeTournoi) REFERENCES Tournoi (codeTournoi)
);

-- Insert data into 'Adherent' table
INSERT INTO Adherent (Nom, Prenom, Adresse, Telephone, Email, Password) VALUES
    ('Doe', 'John', '123 Rue de la Paix', '0123456789', 'john.doe@example.com', 'johnpassword'),
    ('Smith', 'Jane', '456 Avenue Liberté', '9876543210', 'jane.smith@example.com', 'janespassword'),
    ('Dupont', 'Marie', '789 Boulevard de la République', '1122334455', 'marie.dupont@example.com', 'mariespassword');

-- Insert data into 'Tournoi' table
INSERT INTO Tournoi (Nom, Date, Lieu) VALUES
    ('Open de France', '2024-06-01', 'Stade Roland Garros'),
    ('Tournoi de Wimbledon', '2024-07-01', 'All England Lawn Tennis and Croquet Club'),
    ('US Open', '2024-08-25', 'USTA Billie Jean King National Tennis Center');

-- Insert data into 'Inscription' table
-- Assuming 'numeroAdherent' 1, 2, and 'codeTournoi' 1, 2 have been created by previous inserts
INSERT INTO Inscription (numeroAdherent, codeTournoi, DateInscription) VALUES
   (1, 1, '2024-05-20'),
   (1, 2, '2024-06-20'),
   (2, 1, '2024-05-22');
