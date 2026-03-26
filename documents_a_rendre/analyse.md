# Analyse

## 1. Acteurs

- Joueur: controle le personnage et choisit les interactions.
- Systeme de jeu: charge les donnees, applique les regles et affiche.

## 2. Cas d'utilisation

- UC1: Lancer une partie.
- UC2: Interagir avec une cle (ramassage).
- UC3: Interagir avec une porte (verification de cle).
- UC4: Changer de map apres ouverture.
- UC5: Sauvegarder a la sortie.
- UC6: Perdre des coeurs en interagissant avec le Bandit.
- UC7: Fin de partie a 0 coeur avec suppression de sauvegarde.

## 3. Sequence systeme (UC2+UC3+UC4)

```mermaid
sequenceDiagram
    participant U as Joueur
    participant G as Game
    participant S as GameState
    participant M as Map
    U ->> G: selectionne la cle
    G ->> S: interactWith(key)
    S ->> M: removeElement(key)
    S ->> S: add key to inventory
    S -->> G: "Picked up Key"
    U ->> G: selectionne la porte
    G ->> S: interactWith(door)
    S ->> S: verifier cle(id)
    S ->> S: maj position.map/x/y
    S -->> G: "Door opened: Engine_Room"
```

## 4. Concepts d'analyse

- `GameState`: etat applicatif principal (joueur + map courante).
- `Player`: position + inventaire.
- `Map`: decor + liste d'elements affichables.
- `Item`/`Key`: objets ramassables.
- `Door`: element interactif avec destination.

## 5. Regles metier

- Une porte d'id `n` s'ouvre seulement si l'inventaire contient une cle d'id `n`.
- Une cle ramassee disparait de la map et est ajoutee a l'inventaire.
- Une porte ouverte peut transferer vers `destinationMap`, `destinationX`, `destinationY`.
- Le joueur commence avec 3 coeurs.
- Une interaction avec `Bandit` retire 1 coeur.
- A 0 coeur, la partie s'arrete et `state.yml` est supprime.

