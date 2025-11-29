-- ========================================
-- INITIALISATION DE LA BASE DE DONNÉES
-- Ce fichier s'exécute automatiquement au premier démarrage
-- IMPORTANT : Après le premier démarrage, changez dans application.properties :
--             spring.sql.init.mode=never
-- ========================================

-- Insertion des employés avec les bonnes valeurs d'enum
INSERT INTO employee (last_name, grade, first_name, post, salary, username, password)
VALUES ('NGO', 'INTERNS', 'Jonathan', 'Coffee Maker', 0, 'jngo', 'departement'),
       ('BODIER', 'SENIOR_MANAGEMENT', 'Fantine', 'Financial Director', 15000, 'fbodier', 'departement');

-- Insertion des départements
INSERT INTO department (department_name) VALUES
    ('Direction générale'),
    ('Ressources humaines'),
    ('Comptabilité'),
    ('Opérations'),
    ('Ventes'),
    ('Marketing et communication'),
    ('Recherche et développement'),
    ('Production'),
    ('Juridique'),
    ('Services généraux'),
    ('Services de santé'),
    ('Informatique'),
    ('Finances');

-- Affectation du chef de département RH
UPDATE department SET id_chef_dep = (SELECT id_employee FROM employee WHERE username='jngo')
WHERE department_name='Ressources humaines';

-- Insertion des projets
INSERT INTO project (name, status)
VALUES ('ProjetQ', 'WORKED_ON'),
       ('DevWeb', 'FINISHED'),
       ('Cynapse', 'CANCELLED'),
       ('Tekiens', 'WORKED_ON'),
       ('X', 'WORKED_ON'),
       ('Cacook', 'WORKED_ON');



