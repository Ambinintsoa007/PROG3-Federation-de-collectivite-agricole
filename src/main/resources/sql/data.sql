-- 1. On crée 4 membres "Fondateurs" (obligatoires pour créer une collectivité)
INSERT INTO members (id, first_name, last_name, occupation, is_founder) VALUES
                                                                            ('MEMBER-001', 'Rakoto', 'Jean', 'PRESIDENT', true),
                                                                            ('MEMBER-002', 'Rabe', 'Alice', 'SECRETARY', true),
                                                                            ('MEMBER-003', 'Rasoa', 'Berthe', 'TREASURER', true),
                                                                            ('MEMBER-004', 'Randria', 'Luc', 'VICE_PRESIDENT', true);

-- 2. On crée une première collectivité de test
INSERT INTO collectivities (id, location, federation_approval)
VALUES ('COLL-001', 'Antananarivo', true);

-- 3. On définit la structure de cette collectivité
INSERT INTO collectivity_structures (collectivity_id, president_id, secretary_id, treasurer_id, vice_president_id)
VALUES ('COLL-001', 'MEMBER-001', 'MEMBER-002', 'MEMBER-003', 'MEMBER-004');

-- 4. On lie les membres à cette collectivité
INSERT INTO member_collectivity (member_id, collectivity_id) VALUES
                                                                 ('MEMBER-001', 'COLL-001'),
                                                                 ('MEMBER-002', 'COLL-001'),
                                                                 ('MEMBER-003', 'COLL-001'),
                                                                 ('MEMBER-004', 'COLL-001');



INSERT INTO members (id, first_name, last_name, occupation, is_founder) VALUES
                                                                            ('MEMBER-005', 'Soa', 'Line', 'MEMBER', false),
                                                                            ('MEMBER-006', 'Koto', 'Bé', 'MEMBER', false),
                                                                            ('MEMBER-007', 'Bery', 'Zery', 'MEMBER', false),
                                                                            ('MEMBER-008', 'Lita', 'Fety', 'MEMBER', false),
                                                                            ('MEMBER-009', 'Mamy', 'Rina', 'MEMBER', false),
                                                                            ('MEMBER-010', 'Vola', 'Tiana', 'MEMBER', false);

INSERT INTO financial_accounts (id, collectivity_id, account_type)
VALUES ('ACC-001', 'COLL-001', 'CASH');


INSERT INTO transactions (id, collectivity_id, amount, creation_date, payment_mode, account_id) VALUES
                                                                                                    ('TX-01', 'COLL-001', 50000.0, '2026-02-10', 'CASH', 'ACC-001'),
                                                                                                    ('TX-02', 'COLL-001', 120000.0, '2026-03-15', 'MOBILE', 'ACC-001');