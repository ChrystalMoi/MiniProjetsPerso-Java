package com.jeu.flash;

import java.util.Random;
import java.util.Scanner;

/**
 * Mercredi 08 mars 2023
 * @author chrystal
 */

public class Demineur {

    //Variables
    private boolean[][] grille;
    private boolean[][] affichage;
    private int nbMines;
    private int nbCasesRestantes;

    //Constructeur
    public Demineur() {
        grille = new boolean[8][8];
        affichage = new boolean[8][8];
        nbMines = 0;
        nbCasesRestantes = 64;
    }

    /**
     * Appele lors de la création d'une nouvelle instance de la classe
     * Elle place 10 mines aléatoirement sur la grille de 8x8 cases
     */
    public void initialiser() {
        Random rand = new Random();
        int i = 0;
        while (i < 10) {
            int x = rand.nextInt(8);
            int y = rand.nextInt(8);
            if (!grille[x][y]) {
                grille[x][y] = true;
                i++;
            }
        }
    }

    /**
     * Affiche la grille de jeu sur la console avec les cases découvertes 
     * et les mines.
     * la fonction affiche la grille de jeu complète avec les numéros de 
     * colonnes, * les lettres de lignes et les caractères * et . pour 
     * représenter les cases découvertes et les mines.
     * 
     * Mine : *
     * Case à découvrir : .
     */
    public void afficher() {
        System.out.println("  1 2 3 4 5 6 7 8");
        for (int i = 0; i < 8; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < 8; j++) {
                if (affichage[i][j]) {
                    if (grille[i][j]) {
                        System.out.print("* ");
                    } else {
                        int count = 0;
                        if (i > 0 && grille[i - 1][j]) count++;
                        if (i < 7 && grille[i + 1][j]) count++;
                        if (j > 0 && grille[i][j - 1]) count++;
                        if (j < 7 && grille[i][j + 1]) count++;
                        System.out.print(count + " ");
                    }
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    /**
     *  Permet à l'utilisateur de jouer au jeu du démineur en entrant les 
     *  coordonnées d'une case à découvrir
     */
    public void jouer() {
        Scanner scanner = new Scanner(System.in);
        while (nbCasesRestantes > nbMines) {
            afficher();
            System.out.print("Entrez la coordonnée de la case à découvrir (ex: A3): ");
            String entree = scanner.nextLine();
            if (entree.length() == 2) {
                int x = entree.charAt(0) - 'A';
                int y = entree.charAt(1) - '1';
                if (x >= 0 && x < 8 && y >= 0 && y < 8 && !affichage[x][y]) {
                    affichage[x][y] = true;
                    nbCasesRestantes--;
                    if (grille[x][y]) {
                        System.out.println("BOOM ! Vous avez perdu !");
                        return;
                    }
                } else {
                    System.out.println("Coordonnée invalide ou case déjà découverte !");
                }
            } else {
                System.out.println("Coordonnée invalide !");
            }
        }
        System.out.println("Bravo ! Vous avez gagné !");
    }

    public static void main(String[] args) {
        Demineur demineur = new Demineur();
        demineur.initialiser();
        demineur.jouer();
    }
}

