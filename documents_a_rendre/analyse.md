# Analyse

## 1. Acteurs

- Joueur : contrôle le personnage et choisit les interactions.
- Système de jeu : charge les données, applique les règles métier et affiche le rendu.

## 2. Cas d'utilisation

- UC1 : Lancer une partie.
- UC2 : Interagir avec une clé (ramassage).
- UC3 : Interagir avec une porte (vérification de clé et franchissement).
- UC4 : Changer de map après ouverture.
- UC5 : Sauvegarder à la sortie.
- UC6 : Perdre des cœurs en interagissant avec le Bandit.
- UC7 : Fin de partie à 0 cœur avec suppression de sauvegarde.

## 3. Séquence système (UC2 + UC3 + UC4)

Conformément à la phase d'analyse, le système est ici modélisé comme une boîte noire interagissant avec l'acteur.

```mermaid
sequenceDiagram
    participant J as Joueur
    participant S as Système
    J ->> S: Sélectionne la clé
    S -->> J: Affiche "Picked up Key" et met à jour l'inventaire
    J ->> S: Sélectionne la porte
    alt Clé possédée dans l'inventaire
        S -->> J: Affiche "Door opened" et affiche la nouvelle Map (Engine_Room)
    else Clé manquante
        S -->> J: Affiche "Door is locked (missing key)"
    end