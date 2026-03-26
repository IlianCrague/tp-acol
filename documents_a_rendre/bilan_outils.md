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

## 3. Solutions appliquees

- Centralisation de la serialisation dans `Serialization`.
- Utilisation des tags YAML explicites pour les types polymorphes.
- Regles metier regroupees dans `GameState` (pickup/open door).

## 4. Limites

- Interaction encore basee sur selection d'elements et non deplacement case a case.
- Increment volontairement limite pour garder un code simple.

## 5. Prochaines actions

- Ajouter un controleur de commandes avec deplacement et collisions.
- Etendre les tests d'integration sur scenario complet.

