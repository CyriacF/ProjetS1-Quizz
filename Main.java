import extensions.CSVFile;
class Main extends Program{
    void algorithm(){
        clearScreen();cursor(0,0);
        println(" ___________________________________________");
        println("|                                           |");
        println("|           Bienvenue sur le Quiz           |");
        println("|  Séléctionner une des options suivante :  |");
        println("|___________________________________________|");
        print("\n");
        println("1 - Lancer le jeu");
        println("2 - Ajouter des questions");
        println("3 - Classement");
        println("4 - Quitter");
        print("\n");
        print(">> ");
        int menu = readInt();
        if (menu == 1){
            clearScreen();cursor(0,0);
            println("Bienvenue dans le jeu du QUIZ !");
            delay(1500);
            int score = 0;
            println("Le but est simple, il faut répondre aux questions pour gagner un maximum de points !");
            delay(3000);
            println("J'espère que vous êtes prêt car c'est parti !");
            delay(2000);
            affichageJeu(score);
        }if (menu == 2){
            println("Ajout de question");
            AjoutQuestion();
        }if (menu == 3){
            AffichageScore();
        }if (menu == 4){
            println("Merci d'avoir joué !");
            delay(1000);
            System.exit(0);
        }if (menu > 4){
            println("Erreur, merci de rentrer un nombre valide");
            delay(1000);
            algorithm();
        }
    }

    void AjoutQuestion() {
        clearScreen();cursor(0,0);
        println("\nQuel est le thème de votre question ?\n");
        println("1 >> Français");
        println("2 >> Mathématique");
        println("3 >> Histoire");
        println("\n");
        println("Mettez le numéro :");
        print(">> ");
        String theme = readString();
        println("\n\nQuelle est la question ?");
        print(">> ");
        String question = readString(); 
        println("\n\nQuelle est la bonne réponse ?");
        print(">> ");
        String reponse = readString(); 
        println("Ajout terminé\n\n\n\n");
        SaveQuestions(theme, question, reponse);
        delay(1500);
        algorithm();
    }

    void affichageJeu(int score) {
        String[] tableau = affichageQuestion();
        String réponseVrai = tableau[3];
        print(">> ");
        String réponse = readString();
        String reponseMaj = toLowerCase(réponse);
        boolean phraserep = verificationReponse(reponseMaj, réponseVrai);
        score = Score(phraserep, score);
    }

    String[] affichageQuestion() {
        clearScreen();cursor(0,0);
        println("Et voici la superbe question :");
        String file = "CSV/question.csv";
        CSVFile questions = loadCSV(file);
        String affichage = "";
        int nbrLigne = rowCount(questions)-1;
        double random = random()*100;
        int randomT = (int)random;
        while(randomT > nbrLigne){
    	    random = random()*100;
    	    randomT = (int)random;
        }
        String[] tableau = new String[4];
        for(int cpt = 0; cpt<=3; cpt++){
        tableau[cpt] = getCell(questions, randomT, cpt);
        }
        String textTheme = "";
        if (equals(tableau[1], "1")){
            textTheme = "Français";
        }
        if (equals(tableau[1], "2")){
            textTheme = "Mathématique";
        }
        if (equals(tableau[1], "3")){
            textTheme = "Histoire";
        }
        println(" ___________________________________________");
        print("\n");
        println("   Thèmes de la question : " + textTheme);
        println(" ___________________________________________");
        println("\n\n " + tableau[2]);
        String resultat = tableau[3];
        println(affichage);
        return tableau;
  }

    boolean verificationReponse(String réponse, String réponseVrai) {
        boolean resultat = false;
		String vrai = "\nBravo tu as la bonne réponse";
		String faux = "\nMauvaise réponse, la bonne réponse était : " + réponseVrai;
		if (equals(réponse, réponseVrai)){
            clearScreen();cursor(0,0);
			println(vrai);
            resultat = true;
		}if (!equals(réponse, réponseVrai)){
            clearScreen();cursor(0,0);
			println(faux);
            delay(2500);
            resultat = false;
		}
        delay(1000);
		return resultat;
	}

    int Score(boolean resultat, int score){
        if (resultat == false){
            clearScreen();cursor(0,0);
            println("Vous avez perdu avec "+ score + " point(s) !\n\n\n");
            println("Quel est votre nom ? :");
            print(">> ");
            String pseudo = readString();
            SaveScore(pseudo, score);
        }
        if (resultat == true){
            score = score + 1;
            println("Ce qui vous fait maintenant " + score + " point(s)");
            affichageJeu(score);
        }
        return score;
    }

    void SaveScore(String pseudo, int score){
        String file = "CSV/leaderboard.csv";
        String scoreT = "" + score;
        CSVFile leaderboard = loadCSV(file);
        int nbrLigne = rowCount(leaderboard);
        int nbrLigneF = nbrLigne + 1;
        String[][] tableau = new String[nbrLigneF][2];
        for(int cpt = 0; cpt<nbrLigne; cpt++){
            for(int i = 0; i<2; i++){
                tableau[cpt][i] = getCell(leaderboard, cpt, i);
            }
        }
        tableau[nbrLigne][0] = pseudo;
        tableau[nbrLigne][1] = scoreT;
        saveCSV(tableau, file);
        algorithm();
    }

    void AffichageScore(){
        String file = "CSV/leaderboard.csv";
        CSVFile leaderboard = loadCSV(file);
        int nbrLigne = rowCount(leaderboard);
        clearScreen();cursor(0,0);
        println("Prénom - Score");
        print("\n");
        for(int cpt = 0; cpt<nbrLigne; cpt++){
            print(getCell(leaderboard, cpt, 0) + " | ");
            print(getCell(leaderboard, cpt, 1) + " point(s)");
            print("\n");
        }
        println("\n\n\nAppuyez sur '1' pour quitter");
        int quit = readInt();
        if (quit == 1){
            algorithm();
        }else{
            AffichageScore();
        }
    }

    void SaveQuestions(String theme, String question, String reponse){
        String file = "CSV/question.csv";
        String themeT = "" + theme;
        CSVFile questions = loadCSV(file);
        int nbrLigne = rowCount(questions);
        int nbrLigneF = nbrLigne + 1;
        String reponseMaj = toLowerCase(reponse);
        String nbrLigneT = "" + nbrLigneF;
        String[][] tableau = new String[nbrLigneF][4];
        for(int cpt = 0; cpt<nbrLigne; cpt++){
            for(int i = 0; i<4; i++){
                tableau[cpt][i] = getCell(questions, cpt, i);
            }
        }
        tableau[nbrLigne][0] = nbrLigneT;
        tableau[nbrLigne][1] = themeT;
        tableau[nbrLigne][2] = question;
        tableau[nbrLigne][3] = reponseMaj;
        saveCSV(tableau, file);
    }
}