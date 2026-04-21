CREATE DATABASE federation_agricole_db;

CREATE USER federation_agricole_db_manager WITH PASSWORD '123456';

-- 3. Connexion à la base (en ligne de commande : \c federation_agricole_db)
-- Les commandes suivantes doivent être exécutées à l'intérieur de la DB federation_agricole_db

-- 4. Attribution des permissions CRUD (SELECT, INSERT, UPDATE, DELETE)
-- On donne les accès sur le schéma public (là où seront vos tables)
GRANT CONNECT ON DATABASE federation_agricole_db TO federation_agricole_db_manager;
GRANT USAGE ON SCHEMA public TO federation_agricole_db_manager;

-- Note : Ces permissions s'appliqueront aux tables une fois créées.
-- Pour que l'user puisse utiliser les futurs tables :
ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO federation_agricole_db_manager;

-- Si vous utilisez des colonnes auto-incrémentées (SERIAL / IDENTITY),
-- l'user a besoin de permissions sur les séquences pour faire des INSERT :
ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT USAGE, SELECT ON SEQUENCES TO federation_agricole_db_manager;