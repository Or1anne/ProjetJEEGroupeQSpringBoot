-- Script pour nettoyer les valeurs de grade invalides dans la base de données

-- 1. Voir quelles valeurs existent actuellement
SELECT DISTINCT grade FROM employee;

-- 2. Corriger les valeurs vides ou nulles (mettez une valeur par défaut)
UPDATE employee
SET grade = 'EMPLOYEES'
WHERE grade IS NULL OR grade = '' OR TRIM(grade) = '';

-- 3. Si vous avez des valeurs en français, les convertir en noms d'enum
UPDATE employee SET grade = 'EXECUTIVE_MANAGEMENT' WHERE grade = 'Direction exécutive';
UPDATE employee SET grade = 'SENIOR_MANAGEMENT' WHERE grade = 'Cadre supérieur';
UPDATE employee SET grade = 'MIDDLE_MANAGEMENT' WHERE grade = 'Cadre';
UPDATE employee SET grade = 'SKILLED_EMPLOYEES' WHERE grade = 'Employé qualifié';
UPDATE employee SET grade = 'EMPLOYEES' WHERE grade = 'Employé';
UPDATE employee SET grade = 'INTERNS' WHERE grade = 'Stagiaire';

-- 4. Vérifier le résultat
SELECT id_employee, first_name, last_name, grade FROM employee;

