# FamilyElementSpace-API

## Introduction
FamilyElementSpace-API est une API backend développée en **Java avec Spring Boot**. Elle permet la gestion d'éléments familiaux tels que des pensées quotidiennes, des souvenirs en images, des haïkus et des tâches collaboratives (*post-it*). L'application gère les familles et leurs membres tout en offrant des fonctionnalités de partage et de gestion de la visibilité des contenus.

## Installation et Lancement

### Prérequis
- **Java 21 ou supérieur**
- **Maven**
- **Base de données MariaDB ou autre compatible avec Spring Data JPA**

### Installation
```bash
git clone https://github.com/jux42/FamilyElementSpace-API.git
cd FamilyElementSpace-API
mvn clean install
```

### Exécution de l'application
```bash
mvn spring-boot:run
```

## Configuration des Credentials et du Token JWT

L'application attend des **données sensibles** (credentials de la base de données et secret du token JWT) dans un fichier **`application-familyspace-configuration.yml`**, placé **au même niveau que `application.yml`**.

### Emplacement attendu :
```plaintext
src/main/resources/application.yml
src/main/resources/application-familyspace-configuration.yml
```

### 📋 Contenu attendu dans `application-familyspace-configuration.yml` :
```yaml
spring:
  datasource:
    url: jdbc:mariadb://localhost:3306/familyspace
    username: votre-utilisateur
    password: votre-mot-de-passe
jwt:
  secret: votre-clé-secrète-jwt
```

> 💡 **Note** : Ce fichier **ne doit pas être versionné dans Git** pour éviter d'exposer des informations sensibles.




L'API sera accessible sur `http://localhost:8080` par défaut.

## Gestion des Visibilités des Contenus
Les objets gérés dans l'application (*pensées, souvenirs, haïkus*) ont un attribut de visibilité :
- **Public** : visible par tous, y compris les invités (*guests*).
- **Shared** : visible uniquement des membres d'une même famille.
- **Private** : visible uniquement par l'auteur.

## Gestion des Espaces Familiaux
🛠️**En développement**
Chaque famille possède un **pinboard** (tableau d'affichage numérique) qui centralise plusieurs éléments partagés entre ses membres :
- **Les post-its collaboratifs** : Une zone où chaque membre peut ajouter des tâches à effectuer, marquer une tâche comme complétée, et en ajouter de nouvelles en temps réel.
- **Une liste de courses dynamique** : Un espace permettant d'ajouter et de modifier les articles de courses de manière collaborative, afin que tous les membres puissent voir et compléter la liste selon les besoins.
- **Les éléments partagés des membres** : Toutes les pensées, souvenirs et haïkus marqués comme "Shared" par un utilisateur sont accessibles par tous les membres de la famille via cet espace.

Ce pinboard permet donc une meilleure coordination et communication au sein des familles, en simplifiant l'accès aux tâches et contenus collaboratifs.

## Endpoints

### 1. **Gestion des Pensées Quotidiennes** (`DailyThoughtController`)
- **GET** `/thoughts` - Liste toutes les pensées
- **GET** `/thoughts/date` - Récupère une pensée par date
- **GET** `/thought/{id}` - Récupère une pensée spécifique
- **POST** `/thought` - Ajoute une nouvelle pensée
- **DELETE** `/thought/{id}` - Supprime une pensée

### 2. **Gestion des Souvenirs en Image** (`FamilyMemoryPictureController`)
- **GET** `/memorypics` - Liste toutes les images souvenir
- **GET** `/memorypicsonly` - Liste les images sans métadonnées
- **GET** `/memorypic/{id}` - Récupère un souvenir précis
- **GET** `/memorypiconly/{id}` - Récupère une image seule
- **POST** `/memorypic` - Ajoute une nouvelle image souvenir
- **DELETE** `/memorypic/{id}` - Supprime une image souvenir

### 3. **Gestion des Haïkus** (`HaikuController`)
- **GET** `/haikus` - Liste tous les haïkus
- **GET** `/haiku/{id}` - Récupère un haïku précis
- **POST** `/haiku` - Ajoute un nouveau haïku
- **DELETE** `/haiku/{id}` - Supprime un haïku

### 4. **Gestion des Utilisateurs et Authentification** (`AuthController`)
- **POST** `/login` - Connexion d'un utilisateur
- **POST** `/memberregister` - Inscription d'un membre
- **POST** `/guestlogin` - Connexion en tant qu'invité
- **GET** `/getname` - Récupère le nom de l'utilisateur connecté
- **GET** `/whoami` - Vérifie l'identité de l'utilisateur

### 5. **Gestion des Familles** (`FamilyController`)
- **POST** `/family` - Crée une nouvelle famille
- **PUT** `/family` - Met à jour une famille
- **GET** `/family` - Récupère les informations d'une famille

### 6. **Gestion des Membres de la Famille** (`FamilyMemberController`)
- **GET** `/details` - Récupère les détails d'un membre
- **POST** `/haiku/public/{id}` - Rend public un haïku
- **POST** `/haiku/share/{id}` - Partage un haïku avec la famille
- **POST** `/thought/public/{id}` - Rend publique une pensée
- **POST** `/thought/share/{id}` - Partage une pensée avec la famille
- **POST** `/memorypic/public/{id}` - Rend publique une image souvenir
- **POST** `/memorypic/share/{id}` - Partage une image souvenir
- **POST** `/haiku/pin/{id}` - Épingle un haïku (pinboard)
- **POST** `/haiku/unpin/{id}` - Désépingle un haïku
- **POST** `/thought/pin/{id}` - Épingle une pensée (pinboard)
- **POST** `/thought/unpin/{id}` - Désépingle une pensée
- **POST** `/memorypic/pin/{id}` - Épingle une image souvenir (pinboard)
- **POST** `/memorypic/unpin/{id}` - Désépingle une image souvenir

### 7. **Gestion des Tâches Collaboratives (Post-its)** (`PostItController`)
Les post-its permettent d'afficher des **tâches à effectuer** (ménage, courses, promenade du chien, etc.) qui peuvent être marquées comme complétées par un membre de la famille.
- **GET** `/user/{username}` - Liste les post-its d'un utilisateur
- **GET** `/{id}` - Récupère un post-it spécifique
- **POST** `/{username}` - Ajoute une tâche pour un utilisateur
- **PUT** `{id}` - Met à jour une tâche (ex: marquer comme terminée)



