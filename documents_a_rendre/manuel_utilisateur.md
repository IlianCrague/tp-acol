# Manuel utilisateur

## 1. Prerequis

- Java installe.
- Projet gradle cloné localement.

## 2. Lancer le jeu

```powershell
.\gradlew.bat run
```

## 3. Commandes en jeu

- `1`, `2`, ... : interagir avec l'element numerote dans le prompt.
- `i` : afficher/masquer l'inventaire.
- `q` : quitter (sauvegarde immediate).
- `Ctrl+C` : quitter (sauvegarde via shutdown hook).

## 4. Vie et Bandit

- Le joueur commence avec `3` coeurs.
- Les coeurs sont affiches en haut a droite, sur la ligne du titre de map.
- Se teleporter sur `Bandit` retire `1` coeur.
- A `0` coeur: fin de partie immediate et suppression de `state.yml`.

## 5. Scenario de demo

1. Lancer le jeu sur `Cargo_Bay`.
2. Choisir l'element correspondant a la cle.
3. Choisir ensuite la porte.
4. Si la cle est en inventaire, la map devient `Engine_Room`.

## 6. Sauvegarde

Le fichier de sauvegarde est `state.yml` a la racine du projet.

