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
