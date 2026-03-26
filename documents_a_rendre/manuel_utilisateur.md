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

## 4. Scenario de demo

1. Lancer le jeu sur `Cargo_Bay`.
2. Choisir l'element correspondant a la cle.
3. Choisir ensuite la porte.
4. Si la cle est en inventaire, la map devient `Engine_Room`.

## 5. Sauvegarde

Le fichier de sauvegarde est `state.yml` a la racine du projet.

