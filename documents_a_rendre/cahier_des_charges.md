# Cahier des charges

## 1. Contexte

Le projet consiste a realiser un jeu d'aventure en console. Le joueur se deplace dans des maps, interagit avec des
objets et progresse en debloquant des zones.

## 2. Objectif de l'increment

Implementer un flux jouable minimal:

1. Le joueur voit une cle sur la map de depart.
2. Le joueur ramasse la cle.
3. Le joueur ouvre une porte verrouillee avec la cle.
4. Le joueur arrive sur une deuxieme map.

## 3. Exigences fonctionnelles

- Charger l'etat du jeu depuis YAML (`state.yml`).
- Charger les maps depuis YAML (`maps/*.yml`).
- Afficher les elements de map (porte, cle, PNJ).
- Permettre une interaction simple avec les elements listés.
- Ajouter la cle a l'inventaire lors du ramassage.
- Refuser l'ouverture d'une porte sans cle correspondante.
- Changer de map lors de l'ouverture reussie.
- Sauvegarder l'etat du joueur en sortie.

## 4. Exigences non fonctionnelles

- Code simple, lisible, testable.
- Utilisation de SnakeYAML sans logique de parsing custom complexe.
- Temps de lancement court, execution locale sur postes ecole.

## 5. Hors perimetre de cet increment

- IA avancee des PNJ.
- Quetes completes, tresor final.
- Interface graphique.

## 6. Criteres d'acceptation

- Scenario de demo valide: `Cargo_Bay -> prendre cle -> ouvrir porte -> Engine_Room`.
- Tests unitaires verts pour serialisation et interactions principales.

