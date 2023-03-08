package com.jeu.flash;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Mercredi 08 mars 2023
 * @author chrystal
 */

public class FenetreDemineur extends JFrame implements ActionListener {
    //Variables
    private JButton[][] boutons;
    private int[][] mines;
    private int nbMines;
    private int nbCasesRestantes;
    private boolean partieTerminee;

    //Constructeur
    public FenetreDemineur(int taille, int nbMines) {
        super("Le démineur de Chrystal !");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(taille * 50, taille * 50);
        setLayout(new GridLayout(taille, taille));
        this.nbMines = nbMines;
        nbCasesRestantes = taille * taille - nbMines;
        partieTerminee = false;
        boutons = new JButton[taille][taille];
        mines = new int[taille][taille];

        // Initialisation des boutons
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                boutons[i][j] = new JButton();
                boutons[i][j].addActionListener(this);
                add(boutons[i][j]);
            }
        }

        // Placement des miness sur la grilles
        int k = 0;
        while (k < nbMines) {
            int i = (int) (Math.random() * taille);
            int j = (int) (Math.random() * taille);
            if (mines[i][j] != -1) {
                mines[i][j] = -1;
                k++;
            }
        }

        // Calcul du nombre de mines adjacente
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (mines[i][j] == -1) {
                    for (int ii = Math.max(i - 1, 0); ii <= Math.min(i + 1, taille - 1); ii++) {
                        for (int jj = Math.max(j - 1, 0); jj <= Math.min(j + 1, taille - 1); jj++) {
                            if (mines[ii][jj] != -1) {
                                mines[ii][jj]++;
                            }
                        }
                    }
                }
            }
        }
        setVisible(true);
    }

    /**
     * Gestion des clics de souris sur les boutons
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (partieTerminee) {
            return;
        }    
        JButton bouton = (JButton) e.getSource();
        int i = -1;
        int j = -1;
        
        boolean trouve = false;
        
        for (int ii = 0; ii < boutons.length && !trouve; ii++) {
            for (int jj = 0; jj < boutons[ii].length && !trouve; jj++) {
                if (boutons[ii][jj] == bouton) {
                    i = ii;
                    j = jj;
                    trouve = true;
                }
            }
        }
        
        if (mines[i][j] == -1) {
            partieTerminee = true;
            JOptionPane.showMessageDialog(this, "Perdu !", 
                    "Le démineur de Chrystal !", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            for (int ii = 0; ii < boutons.length; ii++) {
                for (int jj = 0; jj < boutons[ii].length; jj++) {
                    if (mines[ii][jj] == -1) {
                        boutons[ii][jj].setText("*");
                    }
                }
            }
        }else {
            decouvrirCase(i, j);
            if (nbCasesRestantes == 0) {
                partieTerminee = true;
                JOptionPane.showMessageDialog(this, "Gagné !", 
                        "Le démineur de Chrystal !", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * Découvre la case à la position (i, j)
     * @param i
     * @param j 
     */
    private void decouvrirCase(int i, int j) {
        if (mines[i][j] >= 0 && boutons[i][j].getText().equals("")) {
            boutons[i][j].setText(Integer.toString(mines[i][j]));
            nbCasesRestantes--;
            if (mines[i][j] == 0) {
                for (int ii = Math.max(i - 1, 0); ii <= Math.min(i + 1, boutons.length - 1); ii++) {
                    for (int jj = Math.max(j - 1, 0); jj <= Math.min(j + 1, boutons[ii].length - 1); jj++) {
                        decouvrirCase(ii, jj);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new FenetreDemineur(8, 10);
    }
    
}
