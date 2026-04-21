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