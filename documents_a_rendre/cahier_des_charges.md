# Cahier des charges

## 1. Contexte

Le projet consiste à realiser un jeu d'aventure en console. Le joueur se deplace dans des maps, interagit avec des
objets et progresse en débloquant des zones.

## 2. Objectif du projet

Implementer un flux jouable minimal:

1. Le joueur voit une cle sur la map de depart.
2. Le joueur ramasse la cle.
3. Le joueur ouvre une porte verrouillée avec la cle.
4. Le joueur arrive sur une deuxième map.
5. Le joueur dispose de 3 cœurs de vie.
6. Le Bandit retire un cœur lors d'une interaction.

## 3. Exigences fonctionnelles

- Charger l'état du jeu depuis YAML (`state.yml`).
- Charger les maps depuis YAML (`maps/*.yml`).
- Afficher les elements de map (porte, cle, PNJ).
- Permettre une interaction simple avec les elements listés.
- Ajouter la cle a l'inventaire lors du ramassage.
- Refuser l'ouverture d'une porte sans cle correspondante.
- Changer de map lors de l'ouverture réussie.
- Sauvegarder l'état du joueur en sortie.
- Afficher les cœurs en haut à droite sur la ligne du titre de map.
- Arrêter la partie a 0 cœur et supprimer `state.yml`.

## 4. Exigences non fonctionnelles

- Code simple, lisible, testable.
- Utilisation de SnakeYAML sans logique de parsing custom complexe.
- Temps de lancement court, execution locale sur postes école.

## 5. Hors périmètre

- IA avancée des PNJ.
- Quêtes completes, trésor final.
- Interface graphique.

## 6. Critères d'acceptation

- Scenario de demo valide: `Cargo_Bay -> prendre cle -> ouvrir porte -> Engine_Room`.
- Scenario de combat valide: interaction avec `Bandit` retire 1 coeur.
- Scenario de défaite valide: a 0 cœur, fin de partie et suppression de la sauvegarde.
- Tests unitaires verts pour serialisation et interactions principales.

