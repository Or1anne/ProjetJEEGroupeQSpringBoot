-- ===========================
--   Création de l'utilisateur
-- ===========================
DROP USER IF EXISTS 'artic_user'@'localhost';
CREATE USER 'artic_user'@'localhost' IDENTIFIED BY 'monSuperPass';

GRANT ALL PRIVILEGES ON *.* TO 'artic_user'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;