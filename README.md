Experiences du 21/10/2014
============

    NGNIX Load Balancing
* round robin : répartition équitable entre serveurs (par défaut). Possibilité de mettre des poids sur certains serveurs pour les privilégier
* least-connected : répartition des requêtes en fonction des serveurs les moins utilisés
* ip_hash : répartition par ip en appliquant un hashage sur l'ip (ce qui est fait actuellement)

Possibilité de faire de l'affinité de session mais payant !

    GIT
* créer une branche : git branch Ma_Branche
* pousser une branche : git push origin Ma_Branche
* supprimer une branche : git branch -d Ma_Branche
* changer de branche : git checkout Ma_Branche
* revenir au master : git checkout master
* ajout une modification : git add .
* commiter une modification : git commit -m "Mon Commentaire"
* pousser une modification : git push
* se mettre à jour : git pull
* réaliser le merge
 *  revenir sur le master ou la branche (où on va intégrer les modifications du merge)
 * 	lancer la commande : git merge  Ma_Branche
 *  si pas de conflit, committer
 *  sinon resoudre les conflits

