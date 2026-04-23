CREATE TABLE members (
                         id VARCHAR(50) PRIMARY KEY,
                         first_name VARCHAR(100) NOT NULL,
                         last_name VARCHAR(100) NOT NULL,
                         birth_date DATE,
                         gender VARCHAR(10),
                         address TEXT,
                         profession VARCHAR(100),
                         phone_number BIGINT,
                         email VARCHAR(255),
                         occupation VARCHAR(20) NOT NULL,
                         is_founder BOOLEAN DEFAULT FALSE
);

CREATE TABLE collectivities (
                                id VARCHAR(50) PRIMARY KEY,
                                location VARCHAR(255) NOT NULL,
                                federation_approval BOOLEAN DEFAULT FALSE
);

CREATE TABLE collectivity_structures (
                                         collectivity_id VARCHAR(50) PRIMARY KEY REFERENCES collectivities(id) ON DELETE CASCADE,
                                         president_id VARCHAR(50) REFERENCES members(id),
                                         vice_president_id VARCHAR(50) REFERENCES members(id),
                                         treasurer_id VARCHAR(50) REFERENCES members(id),
                                         secretary_id VARCHAR(50) REFERENCES members(id)
);

CREATE TABLE member_collectivity (
                                     member_id VARCHAR(50) REFERENCES members(id) ON DELETE CASCADE,
                                     collectivity_id VARCHAR(50) REFERENCES collectivities(id) ON DELETE CASCADE,
                                     PRIMARY KEY (member_id, collectivity_id)
);

CREATE TABLE member_referees (
                                 member_id VARCHAR(50) REFERENCES members(id) ON DELETE CASCADE,
                                 referee_id VARCHAR(50) REFERENCES members(id),
                                 PRIMARY KEY (member_id, referee_id)
);

ALTER TABLE collectivities
    ADD COLUMN identification_number VARCHAR(50);

ALTER TABLE collectivities
    ADD COLUMN unique_name VARCHAR(100) UNIQUE;

CREATE TABLE membership_fees (
                                 id VARCHAR(50) PRIMARY KEY,
                                 collectivity_id VARCHAR(50) REFERENCES collectivities(id),
                                 eligible_from DATE,
                                 frequency VARCHAR(20), -- WEEKLY, MONTHLY, ANNUALLY, PUNCTUALLY
                                 amount DECIMAL(10, 2),
                                 label VARCHAR(255),
                                 status VARCHAR(20) DEFAULT 'ACTIVE'
);


CREATE TABLE financial_accounts (
                                    id VARCHAR(50) PRIMARY KEY,
                                    collectivity_id VARCHAR(50) REFERENCES collectivities(id),
                                    account_type VARCHAR(20), -- CASH, MOBILE_BANKING, BANK
                                    amount DECIMAL(15, 2) DEFAULT 0,
                                    holder_name VARCHAR(100),
                                    bank_name VARCHAR(50),
                                    mobile_number VARCHAR(20)
);

CREATE TABLE documents (
                           id VARCHAR(50) PRIMARY KEY,
                           collectivity_id VARCHAR(50) REFERENCES collectivities(id) ON DELETE CASCADE,
                           document_type VARCHAR(50) NOT NULL,
                           file_path TEXT NOT NULL,
                           upload_date DATE NOT NULL
);
ALTER TABLE financial_accounts
    ADD COLUMN mobile_service VARCHAR(50),
    ADD COLUMN bank_code INTEGER,
    ADD COLUMN branch_code INTEGER,
    ADD COLUMN account_number BIGINT,
    ADD COLUMN account_key INTEGER;
