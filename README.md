# My Project

Tennis est une API simple permettant de retourner les statistiques des joueurs de tennis.

## Installation

Pour lancer ce projet, il faut utiliser JAVA 11 au moins. 
Il suffit clonner le projet et lancer un install de l'application, puis lancer en localHost 1011 "ma date de naissance :)"

## Usage

### Endpoints
```java

1- http://localhost:1011/players
   retourner tous les joueurs. La liste doit être triée du meilleur au moins bon.

2- http://localhost:1011/players/{id de joueur}
   retourner les informations d’un joueur grâce à son ID.

3- http://localhost:1011/stats
   retourner les statistiques suivantes :
    - Pays qui a le plus grand ratio de parties gagnées
    - IMC moyen de tous les joueurs
    - La médiane de la taille des joueur
```

