-- ========================================
-- INITIALISATION DE LA BASE DE DONNÉES
-- Ce fichier s'exécute automatiquement au premier démarrage
-- IMPORTANT : Après le premier démarrage, changez dans application.properties :
--             spring.sql.init.mode=never
-- ========================================

-- Insertion des départements d'abord (requis pour les employés)
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

-- Insertion des employés sans département d'abord
INSERT INTO employee (last_name, grade, first_name, post, salary, username, password)
VALUES ('NGO', 'INTERNS', 'Jonathan', 'Coffee Maker', 0, 'jngo', 'departement'),
       ('BODIER', 'SENIOR_MANAGEMENT', 'Fantine', 'Financial Director', 15000, 'fbodier', 'departement');

-- Insertion des employés avec département
INSERT INTO employee (last_name, grade, first_name, post, salary, username, password, id_department)
VALUES ('Martin', 'MIDDLE_MANAGEMENT', 'Sophie', 'Responsable RH', 6500, 'smartin', 'departement', (SELECT id_department FROM department WHERE department_name='Ressources humaines')),
       ('Dupont', 'SKILLED_EMPLOYEES', 'Lucas', 'Developpeur', 4200, 'ldupont', 'departement', (SELECT id_department FROM department WHERE department_name='Informatique')),
       ('Keller', 'EMPLOYEES', 'Anna', 'Assistant juridique', 3300, 'akeller', 'departement', (SELECT id_department FROM department WHERE department_name='Juridique')),
       ('Morel', 'MIDDLE_MANAGEMENT', 'Thomas', 'Responsable R&D', 9800, 'tmorel', 'departement', (SELECT id_department FROM department WHERE department_name='Recherche et développement')),
       ('Olsen', 'MIDDLE_MANAGEMENT', 'Marie', 'Responsable marketing', 6100, 'molsen', 'departement', (SELECT id_department FROM department WHERE department_name='Marketing et communication')),
       ('Silva', 'EMPLOYEES', 'Pedro', 'Technicien', 2800, 'psilva', 'departement', (SELECT id_department FROM department WHERE department_name='Production')),
       ('Roche', 'EXECUTIVE_MANAGEMENT', 'Elisa', 'Directeur des operations', 18000, 'eroche', 'departement', (SELECT id_department FROM department WHERE department_name='Direction générale'));


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

-- Insertion fiche de paie
INSERT INTO pay (date, bonus, deductions, net, id_employee)
VALUES ('2025-01-31', 200, 50, 3450, (SELECT id_employee FROM employee WHERE username='akeller')),
       ('2025-01-31', 400, 100, 4300, (SELECT id_employee FROM employee WHERE username='ldupont')),
       ('2025-01-31', 800, 250, 7050, (SELECT id_employee FROM employee WHERE username='smartin')),
       ('2025-01-31', 100, 500, 17600, (SELECT id_employee FROM employee WHERE username='eroche')),
       ('2025-01-31', 600, 150, 6550, (SELECT id_employee FROM employee WHERE username='molsen')),
       ('2025-01-31', 100, 50, 2850, (SELECT id_employee FROM employee WHERE username='psilva')),
       ('2025-02-28', 200, 50, 3450, (SELECT id_employee FROM employee WHERE username='akeller')),
       ('2025-02-28', 400, 100, 4300, (SELECT id_employee FROM employee WHERE username='ldupont')),
       ('2025-02-28', 800, 250, 7050, (SELECT id_employee FROM employee WHERE username='smartin')),
       ('2025-02-28', 100, 500, 17600, (SELECT id_employee FROM employee WHERE username='eroche')),
       ('2025-02-28', 600, 150, 6550, (SELECT id_employee FROM employee WHERE username='molsen')),
       ('2025-02-28', 100, 50, 2850, (SELECT id_employee FROM employee WHERE username='psilva'));

