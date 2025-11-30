# ArticSB - Application de Gestion RH

## Sommaire
- [Contenu](#contenu)
- [Objectif](#objectif)
- [Groupe](#groupe)
- [Fichiers](#fichiers)
- [Prérequis](#prérequis)
- [Compiler](#compiler)
- [Lancement](#lancement)

---

## Contenu

**ArticSB** est une application web complète de gestion des ressources humaines développée avec Spring Boot. Elle permet de gérer l'ensemble des aspects RH d'une entreprise de manière centralisée et sécurisée.

### Fonctionnalités principales

#### Gestion des Employés
- Création, modification et suppression d'employés
- Consultation des profils détaillés
- Attribution de grades (Stagiaires/Apprentis, Employés, Employés qualifiés, Cadres, Direction)
- Gestion des rôles et permissions (Admin, RH, Employé)
- Génération automatique de noms d'utilisateur uniques
- Recherche et filtrage avancés

#### Gestion des Départements
- Création et gestion de départements
- Affectation d'un chef de département
- Visualisation des employés par département
- Statistiques par département

#### Gestion des Projets
- Création et suivi des projets
- Affectation des employés aux projets (relation many-to-many)
- Suivi de l'état des projets (En cours, Terminé, Annulé)
- Désignation de chefs de projet
- Vue d'ensemble des ressources allouées

#### Gestion des Fiches de Paie
- Création de fiches de paie mensuelles
- Calcul automatique du salaire net (base + primes - déductions)
- Historique des paies par employé
- Génération de PDF professionnels pour l'impression
- Date automatique en fin de mois

#### Statistiques & Rapports
- Nombre d'employés par département (tableau et graphique)
- Nombre d'employés par projet (tableau et graphique)
- Répartition des employés par grade (graphique en camembert)
- Suivi de l'avancement des projets
- Tableaux de bord interactifs avec Chart.js

#### Sécurité & Authentification
- Système d'authentification sécurisé avec Spring Security
- Gestion des rôles et permissions
- Changement de mot de passe
- Protection des endpoints sensibles
- Sessions utilisateurs

---

## Objectif

Ce projet a été développé dans le cadre de la formation en **JEE (Java Enterprise Edition)** de deuxième année d'ingénieur. Il a pour objectifs pédagogiques :

1. **Maîtriser Spring Boot** : Comprendre l'architecture MVC et les concepts de base du framework
2. **Gestion de bases de données** : Utiliser JPA/Hibernate pour la persistance des données
3. **Sécurité des applications web** : Implémenter Spring Security
4. **Développement Full-Stack** : Créer une application complète avec backend REST et frontend Thymeleaf
5. **Génération de documents** : Créer des PDF dynamiques avec Flying Saucer
6. **Visualisation de données** : Intégrer des graphiques interactifs
7. **Bonnes pratiques** : Respecter l'architecture en couches (Controller, Service, Repository, Entity)

Le projet simule un environnement professionnel réel de gestion RH et permet d'acquérir des compétences directement applicables en entreprise.

---

## Groupe

**Groupe Q**

Ce projet a été réalisé par les membres du Groupe Q dans le cadre du cours de JEE :

- **Membre 1** : Courtade Orianne
- **Membre 2** : Ngo Jonathan 
- **Membre 3** : Bodier Fantine
- **Membre 4** : Rouet Mathieu
- **Membre 5** : Conti Tom

**École** : CY Tech  
**Année** : 2025-2026

---

## Fichiers

### Structure du projet

```
ProjetJEEGroupeQSpringBoot/
│
├── src/
│   ├── main/
│   │   ├── java/org/example/projetjeegroupeqspringboot/
│   │   │   │
│   │   │   ├── config/                          # Configuration Spring
│   │   │   │   └── SecurityConfig.java          # Configuration Spring Security
│   │   │   │
│   │   │   ├── controller/                      # Contrôleurs MVC
│   │   │   │   ├── DepartmentController.java    # Gestion des départements
│   │   │   │   ├── EmployeeController.java      # Gestion des employés
│   │   │   │   ├── HomeController.java          # Page d'accueil
│   │   │   │   ├── LoginController.java         # Authentification
│   │   │   │   ├── PayController.java           # Gestion des fiches de paie
│   │   │   │   ├── ProjectController.java       # Gestion des projets
│   │   │   │   └── ReportController.java        # Statistiques et rapports
│   │   │   │
│   │   │   ├── entity/                          # Entités JPA
│   │   │   │   ├── embededId/                   # Clés composites
│   │   │   │   │   ├── EmployeeProjectId.java   # Clé pour relation Employee-Project
│   │   │   │   │   └── EmployeeRoleId.java      # Clé pour relation Employee-Role
│   │   │   │   ├── enumeration/                 # Énumérations
│   │   │   │   │   ├── Grade.java               # Grades des employés
│   │   │   │   │   └── ProjectStatus.java       # Statuts des projets
│   │   │   │   ├── Department.java              # Entité Département
│   │   │   │   ├── Employee.java                # Entité Employé
│   │   │   │   ├── EmployeeProject.java         # Table de jointure Employee-Project
│   │   │   │   ├── EmployeeRole.java            # Table de jointure Employee-Role
│   │   │   │   ├── Pay.java                     # Entité Fiche de paie
│   │   │   │   ├── Project.java                 # Entité Projet
│   │   │   │   └── Role.java                    # Entité Rôle
│   │   │   │
│   │   │   ├── repository/                      # Repositories JPA
│   │   │   │   ├── DepartmentRepository.java    # Repository des départements
│   │   │   │   ├── EmployeeProjectRepository.java # Repository affectations projet
│   │   │   │   ├── EmployeeRepository.java      # Repository des employés
│   │   │   │   ├── EmployeeRoleRepository.java  # Repository des rôles
│   │   │   │   ├── PayRepository.java           # Repository des fiches de paie
│   │   │   │   ├── ProjectRepository.java       # Repository des projets
│   │   │   │   └── RoleRepository.java          # Repository des rôles
│   │   │   │
│   │   │   ├── service/                         # Couche Service (logique métier)
│   │   │   │   ├── AssignService.java           # Service d'affectation aux projets
│   │   │   │   ├── CustomUserDetailsService.java # Service d'authentification
│   │   │   │   ├── DepartmentService.java       # Service de gestion départements
│   │   │   │   ├── EmployeeService.java         # Service de gestion employés
│   │   │   │   ├── PayService.java              # Service de gestion fiches de paie
│   │   │   │   ├── ProjectService.java          # Service de gestion projets
│   │   │   │   └── ReportService.java           # Service de statistiques
│   │   │   │
│   │   │   ├── util/                            # Classes utilitaires
│   │   │   │   └── PayPdfGenerator.java         # Générateur de PDF pour fiches de paie
│   │   │   │
│   │   │   ├── ProjetJeeGroupeQSpringBootApplication.java # Classe principale
│   │   │   └── ServletInit.java                 # Initialisation servlet
│   │   │
│   │   └── resources/
│   │       ├── application.properties           # Configuration application
│   │       ├── data.sql                         # Données d'initialisation
│   │       ├── Data/
│   │       │   ├── fix_grade_values.sql         # Correction des valeurs de grade
│   │       │   └── user.sql                     # Utilisateurs de test
│   │       ├── static/
│   │       │   └── css/
│   │       │       └── style.css                # Feuille de style CSS
│   │       └── templates/                       # Templates Thymeleaf
│   │           ├── AssignEmployeeProject.html   # Affectation employé à projet
│   │           ├── ChangePassword.html          # Changement de mot de passe
│   │           ├── FormDepartment.html          # Formulaire département
│   │           ├── FormEmployee.html            # Formulaire employé
│   │           ├── FormPay.html                 # Formulaire fiche de paie
│   │           ├── FormProject.html             # Formulaire projet
│   │           ├── Gestion.html                 # Tableau de bord gestion
│   │           ├── index.html                   # Page d'accueil
│   │           ├── ListDepartment.html          # Liste des départements
│   │           ├── ListEmployee.html            # Liste des employés
│   │           ├── ListPay.html                 # Liste des fiches de paie
│   │           ├── ListProject.html             # Liste des projets
│   │           ├── Login.html                   # Page de connexion
│   │           ├── Profile.html                 # Profil utilisateur
│   │           ├── Report.html                  # Page de statistiques
│   │           ├── TrackProject.html            # Suivi de projet
│   │           ├── ViewDepartment.html          # Détails département
│   │           ├── ViewEmployee.html            # Détails employé
│   │           ├── ViewPay.html                 # Détails fiche de paie
│   │           ├── ViewProject.html             # Détails projet
│   │           ├── fragments/
│   │           │   └── navbar.html              # Barre de navigation
│   │           └── PayslipTemplate.html         # Template PDF fiche de paie
│   │
│   └── test/                                    # Tests unitaires
│       └── java/org/example/projetjeegroupeqspringboot/
│           └── ProjetJeeGroupeQSpringBootApplicationTests.java
│
├── target/                                      # Fichiers compilés (généré)
├── .gitignore                                   # Fichiers à ignorer par Git
├── mvnw                                         # Maven Wrapper (Linux/Mac)
├── mvnw.cmd                                     # Maven Wrapper (Windows)
├── pom.xml                                      # Configuration Maven
└── README.md                                    # Ce fichier
```

### Description des principaux fichiers

#### Configuration
- **application.properties** : Configuration de la base de données, JPA, et paramètres Spring Boot
- **SecurityConfig.java** : Configuration de la sécurité (authentification, autorisation, protection CSRF)

#### Entités principales
- **Employee** : Représente un employé avec ses informations personnelles et professionnelles
- **Department** : Représente un département de l'entreprise
- **Project** : Représente un projet avec son statut et son chef de projet
- **Pay** : Représente une fiche de paie avec calculs de salaire
- **Role** : Définit les rôles d'accès (Admin, RH, Employé)

#### Contrôleurs
Gèrent les requêtes HTTP et retournent les vues Thymeleaf appropriées avec les données du modèle.

#### Services
Contiennent la logique métier et font le lien entre les contrôleurs et les repositories.

#### Repositories
Interfaces Spring Data JPA qui fournissent les méthodes CRUD et requêtes personnalisées.

---

## Prérequis

Avant de compiler et lancer le projet, assurez-vous d'avoir installé les éléments suivants :

### Logiciels requis

| Logiciel | Version minimale | Recommandé | Téléchargement |
|----------|-----------------|------------|----------------|
| **Java JDK** | 21 | 21 ou supérieur | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) ou [OpenJDK](https://openjdk.org/) |
| **Maven** | 3.8+ | 3.9+ | [Apache Maven](https://maven.apache.org/download.cgi) |
| **MySQL** | 8.0+ | 8.0+ | [MySQL Community Server](https://dev.mysql.com/downloads/mysql/) |
| **WampServer (Windows)** | 3.3+ | 3.3+ | [WampServer](https://www.wampserver.com/) |
| **MySQL Workbench (Windows)** | 8.0+ | 8.0+ | [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) |
| **IDE** | - | IntelliJ IDEA Ultimate | [JetBrains](https://www.jetbrains.com/idea/) |

> Astuce Windows: si vous ne voulez pas installer Maven globalement, utilisez le Maven Wrapper fourni (mvnw.cmd).

### Dépendances Maven (gérées automatiquement)

Le fichier `pom.xml` contient toutes les dépendances nécessaires qui seront téléchargées automatiquement :

- **Spring Boot Starter Web** : Framework web MVC
- **Spring Boot Starter Data JPA** : ORM Hibernate + JPA
- **Spring Boot Starter Security** : Authentification et autorisation
- **Spring Boot Starter Thymeleaf** : Moteur de templates
- **Thymeleaf Extras Spring Security** : Intégration Thymeleaf-Security
- **MySQL Connector** : Pilote JDBC pour MySQL
- **Flying Saucer PDF** : Génération de PDF depuis HTML
- **Jsoup** : Parsing HTML
- **Spring Boot DevTools** : Rechargement automatique en développement

### Configuration de la base de données

#### Guide rapide (Windows) avec WampServer + MySQL Workbench

**IMPORTANT : Prérequis avant de lancer le projet**

Avant de démarrer l'application Spring Boot, vous **devez obligatoirement** :

1. **Démarrer WampServer** 
   - Cliquez sur l'icône WampServer dans la barre des tâches Windows
   - Attendez que l'icône devienne **verte** (cela peut prendre quelques secondes)
   - Vérifiez que le service MySQL est bien démarré (clic droit sur l'icône → MySQL → Service)

2. **Ouvrir MySQL Workbench**
   - Lancez MySQL Workbench
   - Créez ou ouvrez une connexion locale vers MySQL (généralement sur le port 3306)

3. **Créer l'utilisateur et la base de données**

Dans MySQL Workbench, exécutez les commandes SQL suivantes :

```sql
CREATE USER IF NOT EXISTS 'artic_user'@'localhost' IDENTIFIED BY 'monSuperPass';
CREATE DATABASE IF NOT EXISTS ArticSB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
GRANT ALL PRIVILEGES ON ArticSB.* TO 'artic_user'@'localhost';
FLUSH PRIVILEGES;
```

4. **Vérifier la configuration dans `application.properties`**

Le fichier `src/main/resources/application.properties` contient les paramètres de connexion :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ArticSB?createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.username=artic_user
spring.datasource.password=monSuperPass
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuration JPA (Pour créer les tables automatiquement)
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Configuration pour exécuter data.sql après la création du schéma
spring.sql.init.mode=never
spring.jpa.defer-datasource-initialization=true
```

**Note importante sur `spring.sql.init.mode=never`**

Ce paramètre contrôle l'exécution automatique du fichier `data.sql` au démarrage :

- `spring.sql.init.mode=always` : Le fichier `data.sql` sera exécuté à **chaque** démarrage de l'application
- `spring.sql.init.mode=never` : Le fichier `data.sql` ne sera **jamais** exécuté automatiquement

**Pourquoi utiliser `never` ?**

Dans ce projet, nous utilisons `spring.sql.init.mode=never` pour éviter les problèmes suivants :
- **Éviter les doublons** : Les données ne seront pas réinsérées à chaque redémarrage
- **Préserver les modifications** : Vos modifications dans la base de données ne seront pas écrasées

**Comment insérer les données initiales ?**

Si vous souhaitez charger les données de test (employés, départements, projets) :

1. **Option 1 : Via MySQL Workbench** (recommandé)
   ```sql
   USE ArticSB;
   SOURCE H:/Documents/Orianne_doc_partage/Etudes/ING_2/JEE/Projet/ProjetJEEGroupeQSpringBoot/src/main/resources/data.sql;
   ```

2. **Option 2 : Modifier temporairement le paramètre**
   - Changez `spring.sql.init.mode=never` en `spring.sql.init.mode=always`
   - Démarrez l'application **une seule fois**
   - Remettez `spring.sql.init.mode=never`
   - Redémarrez l'application

**Points de vérification :**
- Si MySQL écoute sur un autre port (ex: 3308), modifiez l'URL: `jdbc:mysql://localhost:3308/ArticSB?...`
- Avec WampServer, évitez d'utiliser la base MariaDB (port 3307) avec le driver MySQL
- Les tables seront créées automatiquement au premier lancement grâce à Hibernate (`spring.jpa.hibernate.ddl-auto=update`)


> **Note** : Les tables seront créées automatiquement au premier lancement grâce à Hibernate (`spring.jpa.hibernate.ddl-auto=update`).

#### 5. Données de test

Le fichier `data.sql` contient des données d'initialisation qui seront chargées automatiquement :
- Rôles (Admin, Employé)
- Départements d'exemple
- Projets d'exemple
- Employés de test
- Fiches de paie d'exemple

**Identifiants par défaut** :
- **Administrateur** : `admin` / `admin`
- **Employés de test** : `username` / `departement`

---

## Compiler

### Compilation avec Maven

#### Option 1 : En ligne de commande

1. **Ouvrez un terminal** dans le répertoire du projet :
   
```powershell
cd H:\Documents\Orianne_doc_partage\Etudes\ING_2\JEE\Projet\ProjetJEEGroupeQSpringBoot
```

2. **Nettoyez et compilez le projet** :
   
```powershell
mvn clean install
```

3. **Compiler sans exécuter les tests** (plus rapide) :
   
```powershell
mvn clean install -DskipTests
```

#### Option 1 bis (Windows sans Maven installé) : Maven Wrapper

Dans PowerShell, utilisez le wrapper fourni :

```powershell
./mvnw.cmd clean install
./mvnw.cmd spring-boot:run
```

#### Option 2 : Avec IntelliJ IDEA

1. **Ouvrez le projet** dans IntelliJ IDEA (File → Open → sélectionner le dossier du projet)
2. **Maven devrait être détecté automatiquement**. Si ce n'est pas le cas :
   - Clic droit sur `pom.xml` → "Add as Maven Project"
3. **Compilez le projet** :
   - Menu : Build → Build Project
   - Ou : `Ctrl+F9` (Windows/Linux) / `Cmd+F9` (Mac)
4. **Rechargez les dépendances Maven** (si nécessaire) :
   - Onglet Maven (à droite) → "Reload All Maven Projects" (🔄)

### Vérification de la compilation

Si la compilation réussit, vous devriez voir :

```
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: XX.XXX s
[INFO] Finished at: YYYY-MM-DDTHH:MM:SS
[INFO] ------------------------------------------------------------------------
```

Le fichier JAR exécutable sera créé dans :
```
target/ProjetJEEGroupeQSpringBoot-0.0.1-SNAPSHOT.jar
```

### Résolution des problèmes de compilation

**Problème : "JAVA_HOME is not set"**
```powershell
$env:JAVA_HOME = "C:\\Program Files\\Java\\jdk-21"
$env:Path = "$env:JAVA_HOME\\bin;$env:Path"
```

**Problème : Dépendances Maven non téléchargées**
```powershell
mvn dependency:resolve
```

**Problème : Conflit de versions**
```powershell
mvn dependency:tree
```

---

## Lancement

###  Avant de démarrer l'application

**IMPORTANT** : Avant de lancer le projet Spring Boot, assurez-vous que :

1. **WampServer est démarré** et l'icône est **verte**
2. **MySQL Workbench** peut se connecter à votre base de données
3. La base de données **ArticSB** et l'utilisateur **artic_user** sont créés
4. Le fichier `application.properties` contient les bons identifiants

> **Astuce** : Si vous oubliez de démarrer WampServer, l'application ne pourra pas se connecter à MySQL et affichera une erreur "Communications link failure".

### Démarrer l'application

#### Option 1 : Avec Maven (ligne de commande)

```powershell
mvn spring-boot:run
```

#### Option 2 : Avec le JAR compilé

```powershell
java -jar target/ProjetJEEGroupeQSpringBoot-0.0.1-SNAPSHOT.jar
```

#### Option 3 : Avec IntelliJ IDEA

1. **Ouvrez la classe principale** : `ProjetJeeGroupeQSpringBootApplication.java`
2. **Cliquez sur le bouton vert "Run"** ▶️ à côté de la méthode `main()`
3. **Ou configurez une Run Configuration** :
   - Run → Edit Configurations → Spring Boot → Main class : `org.example.projetjeegroupeqspringboot.ProjetJeeGroupeQSpringBootApplication`

### Accéder à l'application

Une fois l'application démarrée avec succès, ouvrez votre navigateur sur :

```
http://localhost:8080
```

### Connexion initiale

Utilisez les identifiants par défaut pour vous connecter :

| Rôle | Username | Mot de passe | Permissions |
|------|----------|--------------|-------------|
| Administrateur | `admin` | `admin` | Accès complet à toutes les fonctionnalités |
| Employé | `jngo` | `departement` | Consultation limitée |

> **Important** : Changez le mot de passe après la première connexion !

### Navigation dans l'application

- Page d'accueil, Gestion (Employés, Départements, Projets, Paies), Statistiques, Profil

### Arrêter l'application

- **En ligne de commande** : `Ctrl+C`
- **Dans IntelliJ IDEA** : bouton rouge "Stop" ⏹️

### Dépannage

**Problème : Port 8080 déjà utilisé**

Modifiez le port dans `application.properties` :
```properties
server.port=8081
```

**Problème : Erreur de connexion à MySQL**

Vérifiez :
1. WampServer est démarré (icône verte) et le service MySQL tourne
2. Le port MySQL utilisé (souvent 3306). Si différent, adaptez l'URL JDBC
3. Les identifiants dans `application.properties` sont corrects
4. La base de données `ArticSB` existe et l'utilisateur `artic_user` a les droits

**Problème : Communications link failure**
- Un autre serveur (XAMPP, MariaDB) occupe le port. Fermez l'autre service ou changez le port MySQL et l'URL JDBC.

**Problème : Page blanche ou erreur 404**
- Videz le cache du navigateur et rafraîchissez (`Ctrl+Shift+R`).

**Problème : Erreur de compilation**
```powershell
mvn clean install -U
```

---

## Documentation supplémentaire

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [MySQL Documentation](https://dev.mysql.com/doc/)
