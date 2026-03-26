# Bilan outils

## 1. Outils utilises

- IDE: IntelliJ IDEA.
- Build/test: Gradle.
- Serialisation YAML: SnakeYAML.
- Modelisation UML: generation prevue via IntelliJ (hors de ce dossier).

## 2. Problemes rencontres

- Mapping YAML heterogene (types d'elements differents dans une meme liste).
- Champs transients et generation Lombok pouvant influencer la serialisation.
- Risque de logique de jeu dispersee dans la boucle principale.
- Integration d'un systeme de vie sans recoupler `GameState` a tous les types metier.

## 3. Solutions appliquees

- Centralisation de la serialisation dans `Serialization`.
- Utilisation des tags YAML explicites pour les types polymorphes.
- Approche OOP via `Interactable`: chaque classe (`Item`, `Door`, `NPC`) porte sa logique d'interaction.
- Vie encapsulee dans `Player` (`hearts`, `loseHeart`, `isDead`) + HUD coeurs.

## 4. Limites

- Interaction encore basee sur selection d'elements et non deplacement case a case.
- Increment volontairement limite pour garder un code simple.

## 5. Prochaines actions

- Ajouter un controleur de commandes avec deplacement et collisions.
- Etendre les tests d'integration sur scenario complet.

