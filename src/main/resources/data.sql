-- ========================================
-- INITIALISATION DE LA BASE DE DONNÉES
-- Ce fichier s'exécute automatiquement au premier démarrage
-- IMPORTANT : Après le premier démarrage, changez dans application.properties :
--             spring.sql.init.mode=never
-- ========================================

-- ===========================
--   Insertion des données de base
-- ===========================

-- Rôles
INSERT INTO role (role_name) VALUES
    ('ADMIN'),
    ('RH'),
    ('EMPLOYE');

-- Départements
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

-- Projets
INSERT INTO project (name, status) VALUES
    ('ProjetQ', 'WORKED_ON'),
    ('DevWeb', 'FINISHED'),
    ('Cynapse', 'CANCELLED'),
    ('Tekiens', 'WORKED_ON'),
    ('X', 'WORKED_ON'),
    ('Cacook', 'WORKED_ON'),
    ('Migration Cloud', 'WORKED_ON'),
    ('Refonte ERP', 'WORKED_ON'),
    ('Application Mobile', 'FINISHED'),
    ('Green Energy', 'WORKED_ON'),
    ('Formation IA', 'CANCELLED'),
    ('Optimisation Prod', 'WORKED_ON');

-- Employés avec département
INSERT INTO employee (last_name, first_name, grade, post, salary, username, password, id_department) VALUES
    ('Ngo', 'Jonathan', 'INTERNS', 'Barista', 1200, 'jngo', 'departement', (SELECT id_department FROM department WHERE department_name='Production')),
    ('Bodier', 'Fantine', 'EXECUTIVE_MANAGEMENT', 'Directeur financier', 15000, 'fbodier', 'departement', (SELECT id_department FROM department WHERE department_name='Finances')),
    ('Martin', 'Sophie', 'MIDDLE_MANAGEMENT', 'Responsable RH', 6500, 'smartin', 'departement', (SELECT id_department FROM department WHERE department_name='Ressources humaines')),
    ('Dupont', 'Lucas', 'SKILLED_EMPLOYEES', 'Développeur', 4200, 'ldupont', 'departement', (SELECT id_department FROM department WHERE department_name='Informatique')),
    ('Keller', 'Anna', 'EMPLOYEES', 'Assistant juridique', 3300, 'akeller', 'departement', (SELECT id_department FROM department WHERE department_name='Juridique')),
    ('Morel', 'Thomas', 'MIDDLE_MANAGEMENT', 'Responsable R&D', 9800, 'tmorel', 'departement', (SELECT id_department FROM department WHERE department_name='Recherche et développement')),
    ('Olsen', 'Marie', 'MIDDLE_MANAGEMENT', 'Responsable marketing', 6100, 'molsen', 'departement', (SELECT id_department FROM department WHERE department_name='Marketing et communication')),
    ('Silva', 'Pedro', 'EMPLOYEES', 'Technicien', 2800, 'psilva', 'departement', (SELECT id_department FROM department WHERE department_name='Production')),
    ('Roche', 'Elisa', 'EXECUTIVE_MANAGEMENT', 'Directeur des opérations', 18000, 'eroche', 'departement', (SELECT id_department FROM department WHERE department_name='Direction générale')),
    ('Bernard', 'Claire', 'SENIOR_MANAGEMENT', 'Directeur commercial', 12000, 'cbernard', 'departement', (SELECT id_department FROM department WHERE department_name='Ventes')),
    ('Petit', 'Marc', 'SKILLED_EMPLOYEES', 'Développeur senior', 5200, 'mpetit', 'departement', (SELECT id_department FROM department WHERE department_name='Informatique')),
    ('Rousseau', 'Julie', 'EMPLOYEES', 'Comptable', 3500, 'jrousseau', 'departement', (SELECT id_department FROM department WHERE department_name='Comptabilité')),
    ('Garcia', 'Carlos', 'SKILLED_EMPLOYEES', 'Ingénieur R&D', 4800, 'cgarcia', 'departement', (SELECT id_department FROM department WHERE department_name='Recherche et développement')),
    ('Blanc', 'Émilie', 'EMPLOYEES', 'Assistante RH', 3100, 'eblanc', 'departement', (SELECT id_department FROM department WHERE department_name='Ressources humaines')),
    ('Fournier', 'Paul', 'MIDDLE_MANAGEMENT', 'Chef de production', 6800, 'pfournier', 'departement', (SELECT id_department FROM department WHERE department_name='Production')),
    ('Girard', 'Sarah', 'EMPLOYEES', 'Chargée de communication', 3400, 'sgirard', 'departement', (SELECT id_department FROM department WHERE department_name='Marketing et communication')),
    ('Bonnet', 'Alexandre', 'SKILLED_EMPLOYEES', 'Analyste financier', 4500, 'abonnet', 'departement', (SELECT id_department FROM department WHERE department_name='Finances')),
    ('Meyer', 'Laura', 'EMPLOYEES', 'Commercial', 3200, 'lmeyer', 'departement', (SELECT id_department FROM department WHERE department_name='Ventes')),
    ('Lambert', 'Vincent', 'INTERNS', 'Stagiaire développeur', 1000, 'vlambert', 'departement', (SELECT id_department FROM department WHERE department_name='Informatique')),
    ('Fontaine', 'Nathalie', 'MIDDLE_MANAGEMENT', 'Responsable juridique', 7200, 'nfontaine', 'departement', (SELECT id_department FROM department WHERE department_name='Juridique'));

-- Employé administrateur (sans département)
INSERT INTO employee (last_name, first_name, grade, post, salary, username, password)
VALUES ('Admin', 'Système', 'EMPLOYEES', 'Administrateur', 100.0, 'admin', 'admin');
-- TODO : Mettre le hash du mot de passe ici

-- Attribution du rôle ADMIN à l'utilisateur admin
INSERT INTO employee_role (id_employee, id_role)
VALUES (
    (SELECT id_employee FROM employee WHERE username = 'admin' LIMIT 1),
    (SELECT id_role FROM role WHERE role_name = 'ADMIN' LIMIT 1)
);

-- Chef de département
UPDATE department SET id_chef_dep = (SELECT id_employee FROM employee WHERE username='tmorel')
WHERE department_name='Recherche et développement';

UPDATE department SET id_chef_dep = (SELECT id_employee FROM employee WHERE username='smartin')
WHERE department_name='Ressources humaines';

UPDATE department SET id_chef_dep = (SELECT id_employee FROM employee WHERE username='eroche')
WHERE department_name='Direction générale';

UPDATE department SET id_chef_dep = (SELECT id_employee FROM employee WHERE username='cbernard')
WHERE department_name='Ventes';

UPDATE department SET id_chef_dep = (SELECT id_employee FROM employee WHERE username='pfournier')
WHERE department_name='Production';

UPDATE department SET id_chef_dep = (SELECT id_employee FROM employee WHERE username='molsen')
WHERE department_name='Marketing et communication';

UPDATE department SET id_chef_dep = (SELECT id_employee FROM employee WHERE username='nfontaine')
WHERE department_name='Juridique';

UPDATE department SET id_chef_dep = (SELECT id_employee FROM employee WHERE username='fbodier')
WHERE department_name='Finances';

-- Chef de projet
UPDATE project SET id_chef_pro = (SELECT id_employee FROM employee WHERE username='ldupont') WHERE name='DevWeb';
UPDATE project SET id_chef_pro = (SELECT id_employee FROM employee WHERE username='tmorel') WHERE name='Cynapse';
UPDATE project SET id_chef_pro = (SELECT id_employee FROM employee WHERE username='psilva') WHERE name='Cacook';
UPDATE project SET id_chef_pro = (SELECT id_employee FROM employee WHERE username='mpetit') WHERE name='Migration Cloud';
UPDATE project SET id_chef_pro = (SELECT id_employee FROM employee WHERE username='cgarcia') WHERE name='Green Energy';
UPDATE project SET id_chef_pro = (SELECT id_employee FROM employee WHERE username='molsen') WHERE name='X';
UPDATE project SET id_chef_pro = (SELECT id_employee FROM employee WHERE username='abonnet') WHERE name='Refonte ERP';
UPDATE project SET id_chef_pro = (SELECT id_employee FROM employee WHERE username='ldupont') WHERE name='Application Mobile';

-- Rôles employés
INSERT INTO employee_role (id_employee, id_role) VALUES
    ((SELECT id_employee FROM employee WHERE username='smartin'), (SELECT id_role FROM role WHERE role_name='EMPLOYE')),
    ((SELECT id_employee FROM employee WHERE username='tmorel'), (SELECT id_role FROM role WHERE role_name='RH')),
    ((SELECT id_employee FROM employee WHERE username='ldupont'), (SELECT id_role FROM role WHERE role_name='EMPLOYE')),
    ((SELECT id_employee FROM employee WHERE username='cbernard'), (SELECT id_role FROM role WHERE role_name='EMPLOYE')),
    ((SELECT id_employee FROM employee WHERE username='mpetit'), (SELECT id_role FROM role WHERE role_name='EMPLOYE')),
    ((SELECT id_employee FROM employee WHERE username='jrousseau'), (SELECT id_role FROM role WHERE role_name='EMPLOYE')),
    ((SELECT id_employee FROM employee WHERE username='cgarcia'), (SELECT id_role FROM role WHERE role_name='EMPLOYE')),
    ((SELECT id_employee FROM employee WHERE username='eblanc'), (SELECT id_role FROM role WHERE role_name='RH')),
    ((SELECT id_employee FROM employee WHERE username='pfournier'), (SELECT id_role FROM role WHERE role_name='EMPLOYE')),
    ((SELECT id_employee FROM employee WHERE username='sgirard'), (SELECT id_role FROM role WHERE role_name='EMPLOYE')),
    ((SELECT id_employee FROM employee WHERE username='abonnet'), (SELECT id_role FROM role WHERE role_name='EMPLOYE')),
    ((SELECT id_employee FROM employee WHERE username='lmeyer'), (SELECT id_role FROM role WHERE role_name='EMPLOYE')),
    ((SELECT id_employee FROM employee WHERE username='nfontaine'), (SELECT id_role FROM role WHERE role_name='EMPLOYE'));

-- Insertion table de jointure employé-projet
INSERT INTO employee_project (id_employee, id_project) VALUES
    -- Projets anciens
    ((SELECT id_employee FROM employee WHERE username='ldupont'), (SELECT id_project FROM project WHERE name='DevWeb')),
    ((SELECT id_employee FROM employee WHERE username='ldupont'), (SELECT id_project FROM project WHERE name='Cynapse')),
    ((SELECT id_employee FROM employee WHERE username='tmorel'), (SELECT id_project FROM project WHERE name='Cynapse')),
    ((SELECT id_employee FROM employee WHERE username='psilva'), (SELECT id_project FROM project WHERE name='Cacook')),
    ((SELECT id_employee FROM employee WHERE username='akeller'), (SELECT id_project FROM project WHERE name='Tekiens')),
    ((SELECT id_employee FROM employee WHERE username='molsen'), (SELECT id_project FROM project WHERE name='X')),
    ((SELECT id_employee FROM employee WHERE username='smartin'), (SELECT id_project FROM project WHERE name='ProjetQ')),

    -- Migration Cloud
    ((SELECT id_employee FROM employee WHERE username='mpetit'), (SELECT id_project FROM project WHERE name='Migration Cloud')),
    ((SELECT id_employee FROM employee WHERE username='ldupont'), (SELECT id_project FROM project WHERE name='Migration Cloud')),
    ((SELECT id_employee FROM employee WHERE username='vlambert'), (SELECT id_project FROM project WHERE name='Migration Cloud')),

    -- Refonte ERP
    ((SELECT id_employee FROM employee WHERE username='abonnet'), (SELECT id_project FROM project WHERE name='Refonte ERP')),
    ((SELECT id_employee FROM employee WHERE username='jrousseau'), (SELECT id_project FROM project WHERE name='Refonte ERP')),
    ((SELECT id_employee FROM employee WHERE username='mpetit'), (SELECT id_project FROM project WHERE name='Refonte ERP')),

    -- Application Mobile
    ((SELECT id_employee FROM employee WHERE username='ldupont'), (SELECT id_project FROM project WHERE name='Application Mobile')),
    ((SELECT id_employee FROM employee WHERE username='sgirard'), (SELECT id_project FROM project WHERE name='Application Mobile')),

    -- Green Energy
    ((SELECT id_employee FROM employee WHERE username='cgarcia'), (SELECT id_project FROM project WHERE name='Green Energy')),
    ((SELECT id_employee FROM employee WHERE username='tmorel'), (SELECT id_project FROM project WHERE name='Green Energy')),
    ((SELECT id_employee FROM employee WHERE username='pfournier'), (SELECT id_project FROM project WHERE name='Green Energy')),

    -- Formation IA (annulé)
    ((SELECT id_employee FROM employee WHERE username='tmorel'), (SELECT id_project FROM project WHERE name='Formation IA')),

    -- Optimisation Prod
    ((SELECT id_employee FROM employee WHERE username='pfournier'), (SELECT id_project FROM project WHERE name='Optimisation Prod')),
    ((SELECT id_employee FROM employee WHERE username='psilva'), (SELECT id_project FROM project WHERE name='Optimisation Prod')),
    ((SELECT id_employee FROM employee WHERE username='jngo'), (SELECT id_project FROM project WHERE name='Optimisation Prod')),

    -- ProjetQ (suite)
    ((SELECT id_employee FROM employee WHERE username='eblanc'), (SELECT id_project FROM project WHERE name='ProjetQ')),

    -- Tekiens (suite)
    ((SELECT id_employee FROM employee WHERE username='nfontaine'), (SELECT id_project FROM project WHERE name='Tekiens')),

    -- X (suite)
    ((SELECT id_employee FROM employee WHERE username='sgirard'), (SELECT id_project FROM project WHERE name='X')),
    ((SELECT id_employee FROM employee WHERE username='lmeyer'), (SELECT id_project FROM project WHERE name='X'));

-- Insertion fiches de paie
-- Janvier 2025
INSERT INTO pay (date, bonus, deductions, net, id_employee) VALUES
    ('2025-01-31', 200, 50, 3450, (SELECT id_employee FROM employee WHERE username='akeller')),
    ('2025-01-31', 400, 100, 4300, (SELECT id_employee FROM employee WHERE username='ldupont')),
    ('2025-01-31', 800, 250, 7050, (SELECT id_employee FROM employee WHERE username='smartin')),
    ('2025-01-31', 100, 500, 17600, (SELECT id_employee FROM employee WHERE username='eroche')),
    ('2025-01-31', 600, 150, 6550, (SELECT id_employee FROM employee WHERE username='molsen')),
    ('2025-01-31', 100, 50, 2850, (SELECT id_employee FROM employee WHERE username='psilva')),
    ('2025-01-31', 500, 200, 5500, (SELECT id_employee FROM employee WHERE username='mpetit')),
    ('2025-01-31', 300, 150, 3650, (SELECT id_employee FROM employee WHERE username='jrousseau')),
    ('2025-01-31', 400, 180, 5020, (SELECT id_employee FROM employee WHERE username='cgarcia')),
    ('2025-01-31', 200, 100, 3200, (SELECT id_employee FROM employee WHERE username='eblanc')),
    ('2025-01-31', 700, 220, 7280, (SELECT id_employee FROM employee WHERE username='pfournier')),
    ('2025-01-31', 250, 120, 3530, (SELECT id_employee FROM employee WHERE username='sgirard')),
    ('2025-01-31', 450, 170, 4780, (SELECT id_employee FROM employee WHERE username='abonnet')),
    ('2025-01-31', 300, 130, 3370, (SELECT id_employee FROM employee WHERE username='lmeyer')),
    ('2025-01-31', 1200, 400, 12800, (SELECT id_employee FROM employee WHERE username='cbernard')),
    ('2025-01-31', 1500, 500, 16000, (SELECT id_employee FROM employee WHERE username='fbodier')),
    ('2025-01-31', 800, 280, 7720, (SELECT id_employee FROM employee WHERE username='nfontaine')),
    ('2025-01-31', 1000, 320, 10480, (SELECT id_employee FROM employee WHERE username='tmorel')),
    ('2025-01-31', 50, 30, 1220, (SELECT id_employee FROM employee WHERE username='jngo')),
    ('2025-01-31', 100, 40, 1060, (SELECT id_employee FROM employee WHERE username='vlambert'));

-- Février 2025
INSERT INTO pay (date, bonus, deductions, net, id_employee) VALUES
    ('2025-02-28', 150, 50, 3400, (SELECT id_employee FROM employee WHERE username='akeller')),
    ('2025-02-28', 500, 120, 4380, (SELECT id_employee FROM employee WHERE username='ldupont')),
    ('2025-02-28', 900, 280, 7120, (SELECT id_employee FROM employee WHERE username='smartin')),
    ('2025-02-28', 200, 550, 17650, (SELECT id_employee FROM employee WHERE username='eroche')),
    ('2025-02-28', 550, 170, 6480, (SELECT id_employee FROM employee WHERE username='molsen')),
    ('2025-02-28', 120, 60, 2860, (SELECT id_employee FROM employee WHERE username='psilva')),
    ('2025-02-28', 600, 210, 5590, (SELECT id_employee FROM employee WHERE username='mpetit')),
    ('2025-02-28', 280, 140, 3640, (SELECT id_employee FROM employee WHERE username='jrousseau')),
    ('2025-02-28', 450, 200, 5050, (SELECT id_employee FROM employee WHERE username='cgarcia')),
    ('2025-02-28', 180, 90, 3190, (SELECT id_employee FROM employee WHERE username='eblanc')),
    ('2025-02-28', 750, 240, 7310, (SELECT id_employee FROM employee WHERE username='pfournier')),
    ('2025-02-28', 300, 130, 3570, (SELECT id_employee FROM employee WHERE username='sgirard')),
    ('2025-02-28', 500, 180, 4820, (SELECT id_employee FROM employee WHERE username='abonnet')),
    ('2025-02-28', 280, 120, 3360, (SELECT id_employee FROM employee WHERE username='lmeyer')),
    ('2025-02-28', 1300, 420, 12880, (SELECT id_employee FROM employee WHERE username='cbernard')),
    ('2025-02-28', 1600, 520, 16080, (SELECT id_employee FROM employee WHERE username='fbodier')),
    ('2025-02-28', 850, 300, 7750, (SELECT id_employee FROM employee WHERE username='nfontaine')),
    ('2025-02-28', 1100, 350, 10550, (SELECT id_employee FROM employee WHERE username='tmorel')),
    ('2025-02-28', 80, 35, 1245, (SELECT id_employee FROM employee WHERE username='jngo')),
    ('2025-02-28', 120, 45, 1075, (SELECT id_employee FROM employee WHERE username='vlambert'));

-- Mars 2025
INSERT INTO pay (date, bonus, deductions, net, id_employee) VALUES
    ('2025-03-31', 180, 55, 3425, (SELECT id_employee FROM employee WHERE username='akeller')),
    ('2025-03-31', 450, 110, 4340, (SELECT id_employee FROM employee WHERE username='ldupont')),
    ('2025-03-31', 850, 260, 7090, (SELECT id_employee FROM employee WHERE username='smartin')),
    ('2025-03-31', 150, 520, 17630, (SELECT id_employee FROM employee WHERE username='eroche')),
    ('2025-03-31', 620, 160, 6560, (SELECT id_employee FROM employee WHERE username='molsen')),
    ('2025-03-31', 110, 55, 2855, (SELECT id_employee FROM employee WHERE username='psilva')),
    ('2025-03-31', 550, 205, 5545, (SELECT id_employee FROM employee WHERE username='mpetit')),
    ('2025-03-31', 310, 145, 3665, (SELECT id_employee FROM employee WHERE username='jrousseau')),
    ('2025-03-31', 420, 190, 5030, (SELECT id_employee FROM employee WHERE username='cgarcia')),
    ('2025-03-31', 190, 95, 3195, (SELECT id_employee FROM employee WHERE username='eblanc')),
    ('2025-03-31', 720, 230, 7290, (SELECT id_employee FROM employee WHERE username='pfournier')),
    ('2025-03-31', 270, 125, 3545, (SELECT id_employee FROM employee WHERE username='sgirard')),
    ('2025-03-31', 480, 175, 4805, (SELECT id_employee FROM employee WHERE username='abonnet')),
    ('2025-03-31', 290, 125, 3365, (SELECT id_employee FROM employee WHERE username='lmeyer')),
    ('2025-03-31', 1250, 410, 12840, (SELECT id_employee FROM employee WHERE username='cbernard')),
    ('2025-03-31', 1550, 510, 16040, (SELECT id_employee FROM employee WHERE username='fbodier')),
    ('2025-03-31', 820, 290, 7730, (SELECT id_employee FROM employee WHERE username='nfontaine')),
    ('2025-03-31', 1050, 340, 10510, (SELECT id_employee FROM employee WHERE username='tmorel')),
    ('2025-03-31', 60, 32, 1228, (SELECT id_employee FROM employee WHERE username='jngo')),
    ('2025-03-31', 110, 42, 1068, (SELECT id_employee FROM employee WHERE username='vlambert'));

