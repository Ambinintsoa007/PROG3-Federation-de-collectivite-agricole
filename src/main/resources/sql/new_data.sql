-- 1. Permettre de compter les nouveaux adhérents par période
ALTER TABLE member ADD COLUMN creation_date DATE DEFAULT CURRENT_DATE;

-- 2. Lier les transactions aux membres pour calculer le pourcentage à jour
ALTER TABLE transaction ADD COLUMN member_id CHARACTER VARYING;
ALTER TABLE transaction ADD CONSTRAINT fk_member_transaction
    FOREIGN KEY (member_id) REFERENCES member(id);

-------------------------------

TRUNCATE TABLE transaction, membership_fee, bank_account, cash_account, mobile_banking_account, collectivity_member, member_referee CASCADE;
TRUNCATE TABLE collectivity, member CASCADE;

------------------------

--collectivity
INSERT INTO collectivity (id, name, number, location, specialization) VALUES
                                                                          ('C1', 'Union des Producteurs de Riz', 101, 'Antsirabe', 'Riziculture'),
                                                                          ('C2', 'Cooperative Vanille SAVA', 102, 'Sambava', 'Vanille'),
                                                                          ('C3', 'Collectif des Maraichers du Sud', 103, 'Toliara', 'Maraichage');

---- COLLECTIVITE 1
INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, email, occupation, registration_fee_paid, membership_dues_paid, creation_date) VALUES
                                                                                                                                                                           ('C1-M1', 'Jean', 'Dupont', '1985-05-15', 'MALE', 'IAV 10', 'Riziculteur', 'jean@email.com', 'SENIOR', true, true, '2026-01-01'),
                                                                                                                                                                           ('C1-M2', 'Marie', 'Rakoto', '1992-08-22', 'FEMALE', 'II B 45', 'Agronome', 'marie@email.com', 'SENIOR', true, true, '2026-01-01'),
                                                                                                                                                                           ('C1-M3', 'Sitraka', 'Andria', '1998-12-02', 'MALE', 'III M 12', 'Maraicher', 'sitraka@email.com', 'JUNIOR', true, false, '2026-03-01');

-- COLLECTIVITE 2
INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, email, occupation, registration_fee_paid, membership_dues_paid, creation_date) VALUES
                                                                                                                                                                           ('C2-M1', 'Rindra', 'Andry', '1980-11-12', 'MALE', 'SAVA 101', 'Exportateur', 'rindra@email.com', 'SENIOR', true, true, '2026-01-01'),
                                                                                                                                                                           ('C2-M2', 'Lova', 'Sitraka', '1988-02-28', 'FEMALE', 'SAVA 202', 'Producteur', 'lova@email.com', 'SENIOR', true, true, '2026-01-01'),
                                                                                                                                                                           ('C2-M3', 'Mamy', 'Rasoa', '2001-07-14', 'FEMALE', 'Villa Vanille', 'Preparateur', 'mamy@email.com', 'JUNIOR', true, false, '2026-03-01');

-- COLLECTIVITE 3
INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, email, occupation, registration_fee_paid, membership_dues_paid, creation_date) VALUES
                                                                                                                                                                           ('C3-M1', 'Nicolas', 'Solo', '1975-03-25', 'MALE', 'Bord de mer', 'Grossiste', 'nicolas@email.com', 'SENIOR', true, true, '2026-01-01'),
                                                                                                                                                                           ('C3-M2', 'Fanja', 'Koto', '1982-12-12', 'FEMALE', 'Toliara 505', 'Maraicher', 'fanja@email.com', 'SENIOR', true, true, '2026-01-01'),
                                                                                                                                                                           ('C3-M3', 'Elias', 'Raza', '2003-05-18', 'MALE', 'Quartier Sud', 'Horticulteur', 'elias@email.com', 'JUNIOR', true, false, '2026-01-01');

--collectivity_member
INSERT INTO collectivity_member (id, member_id, collectivity_id) VALUES
                                                                     ('CM1', 'C1-M1', 'C1'), ('CM2', 'C1-M2', 'C1'), ('CM3', 'C1-M3', 'C1'),
                                                                     ('CM4', 'C2-M1', 'C2'), ('CM5', 'C2-M2', 'C2'), ('CM6', 'C2-M3', 'C2'),
                                                                     ('CM7', 'C3-M1', 'C3'), ('CM8', 'C3-M2', 'C3'), ('CM9', 'C3-M3', 'C3');

--cacsh_account
INSERT INTO cash_account (id, collectivity_id) VALUES ('CS1', 'C1'), ('CS2', 'C2'), ('CS3', 'C3');

--membership_fee
INSERT INTO membership_fee (id, label, amount, eligible_from, status, frequency, collectivity_id) VALUES
                                                                                                      ('F1', 'Cotisation Annuelle', 50000.00, '2026-01-01', 'ACTIVE', 'ANNUALLY', 'C1'),
                                                                                                      ('F2', 'Cotisation Annuelle', 50000.00, '2026-01-01', 'ACTIVE', 'ANNUALLY', 'C2'),
                                                                                                      ('F3', 'Cotisation Annuelle', 50000.00, '2026-01-01', 'ACTIVE', 'ANNUALLY', 'C3');

--TRANSACTION
-- On fait payer M1 et M2 de la Collectivité 1 pour avoir un taux de 66% (2/3 payés)
INSERT INTO transaction (id, amount, creation_date, transaction_type, financial_account_id, member_id) VALUES
                                                                                                           ('T1', 50000.00, '2026-01-15', 'IN', 'CS1', 'C1-M1'),
                                                                                                           ('T2', 50000.00, '2026-01-20', 'IN', 'CS1', 'C1-M2');

--
UPDATE collectivity SET president_id = 'C1-M1', treasurer_id = 'C1-M2' WHERE id = 'C1';
UPDATE collectivity SET president_id = 'C2-M1', treasurer_id = 'C2-M2' WHERE id = 'C2';
UPDATE collectivity SET president_id = 'C3-M1', treasurer_id = 'C3-M2' WHERE id = 'C3';

--
-- Collectivite 1
UPDATE collectivity SET president_id = 'C1-M1', treasurer_id = 'C1-M2', secretary_id = 'C1-M3' WHERE id = 'C1';
-- Collectivite 2
UPDATE collectivity SET president_id = 'C2-M1', treasurer_id = 'C2-M2' WHERE id = 'C2';
-- Collectivite 3
UPDATE collectivity SET president_id = 'C3-M1', treasurer_id = 'C3-M2' WHERE id = 'C3';

--
INSERT INTO member_referee (id, member_refereed_id, member_referee_id) VALUES
                                                                           ('R1', 'C1-M3', 'C1-M1'), -- M3 parraine par M1
                                                                           ('R2', 'C1-M3', 'C1-M2'), -- M3 aussi parraine par M2
                                                                           ('R3', 'C2-M3', 'C2-M1'),
                                                                           ('R4', 'C3-M3', 'C3-M1');

--membership_fee
INSERT INTO membership_fee (id, label, amount, eligible_from, status, frequency, collectivity_id) VALUES
                                                                                                      ('F1', 'Cotisation Annuelle', 50000.00, '2026-01-01', 'ACTIVE', 'ANNUALLY', 'C1'),
                                                                                                      ('F2', 'Cotisation Mensuelle', 10000.00, '2026-01-01', 'ACTIVE', 'MONTHLY', 'C2'),
                                                                                                      ('F3', 'Cotisation Saisonniere', 30000.00, '2026-01-01', 'ACTIVE', 'ANNUALLY', 'C3');


CREATE TABLE activities (
                            id VARCHAR(50) PRIMARY KEY,
                            collectivity_id VARCHAR(50) REFERENCES collectivity(id),
                            label VARCHAR(255) NOT NULL,
                            type VARCHAR(50) NOT NULL,
                            occupations VARCHAR[] NOT NULL,
                            executive_date DATE NOT NULL
);

ALTER TABLE activities ALTER COLUMN executive_date DROP NOT NULL;


ALTER TABLE activities ADD COLUMN week_ordinal INT;
ALTER TABLE activities ADD COLUMN day_of_week VARCHAR(2);
ALTER TABLE activities ADD CONSTRAINT check_timing
    CHECK (
        (executive_date IS NOT NULL AND week_ordinal IS NULL AND day_of_week IS NULL) OR
        (executive_date IS NULL AND week_ordinal IS NOT NULL AND day_of_week IS NOT NULL)
        );
ALTER TABLE activities ALTER COLUMN executive_date DROP NOT NULL;
CREATE TABLE activity_attendance (
                                     id VARCHAR(50) PRIMARY KEY,
                                     activity_id VARCHAR(50) REFERENCES activities(id),
                                     member_id VARCHAR(50) REFERENCES member(id),
                                     status VARCHAR(20) NOT NULL,
                                     UNIQUE(activity_id, member_id)
);