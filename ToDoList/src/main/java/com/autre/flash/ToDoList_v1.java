package com.autre.flash;

/**
 * Mercredi 08 mars 2023
 * @author chrystal
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Color;

public class ToDoList_v1 extends JFrame implements ActionListener {
    
    private JTextField input;
    private JTextArea tasks; //colonne pour les taches a faire
    private JButton addBtn, deleteBtn, editBtn; //les boutons
    private JCheckBox doneBox;
    private JTextArea doneTasks; //Colonne pour les taches fini

    public ToDoList_v1() {
        super("La ToDo List de Madame Chrystal");

        // Créer les composants
        input = new JTextField(30);
        doneTasks = new JTextArea(20, 30); //tasks = new JTextArea(20, 30);
        doneTasks.setEditable(false); //tasks.setEditable(false);
        doneBox = new JCheckBox("Fait");
        addBtn = new JButton("Ajouter ");
        deleteBtn = new JButton("Supprimer ");
        editBtn = new JButton("Modifier");

        // Ajouter des listeners sur les boutons et la checkbox
        addBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        editBtn.addActionListener(this);
        doneBox.addActionListener(this);

        // Créer un panel pour le champ de texte et les boutons
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(input);
        inputPanel.add(addBtn);
        inputPanel.add(editBtn);
        inputPanel.add(deleteBtn);
        inputPanel.add(doneBox);

        // Ajouter les composants à la fenêtre
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(tasks), BorderLayout.CENTER);

        // Paramétrer la fenêtre
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            // Ajouter la tâche à la liste
            String task = input.getText().trim();
            
            if (!task.isEmpty()) {
                tasks.append("- " + task + "\n");
                input.setText("");
            }
        } else if (e.getSource() == deleteBtn) {
            // Supprimer la tâche sélectionnée
            int index = tasks.getCaretPosition();
            
            if (index < tasks.getText().length()) {
                tasks.select(index, tasks.getText().indexOf('\n', index));
                tasks.replaceSelection("");
            }
        } else if (e.getSource() == editBtn) {
            // Modifier la tâche sélectionnée
            int index = tasks.getCaretPosition();
            
            if (index < tasks.getText().length()) {
                tasks.select(index, tasks.getText().indexOf('\n', index));
                String newTask = JOptionPane.showInputDialog("Modifier la tâche :", tasks.getSelectedText());
                if (newTask != null && !newTask.trim().isEmpty()) {
                    tasks.replaceSelection("- " + newTask.trim());
                }
            }
        } 
        
        //La tache sélectionné est mis en couleur
        tasks.setSelectionColor(Color.MAGENTA);
        
        if (e.getSource() == doneBox) {
            // Cocher ou décocher la tâche sélectionnée
            int index = tasks.getCaretPosition();
            if (index < tasks.getText().length()) {
                String line = tasks.getText().substring(tasks.getText().lastIndexOf('\n', index - 1) + 1, index).trim();
                if (line.startsWith("- ")) {
                    line = line.substring(2);
                }
                if (doneBox.isSelected()) {
                    tasks.replaceRange("- [X] " + line, tasks.getText().lastIndexOf('\n', index - 1) + 1, index);
                } else {
                    tasks.replaceRange("- [ ] " + line, tasks.getText().lastIndexOf('\n', index - 1) + 1, index);
                }
            }
            updateTasks(); // Appel de la méthode pour mettre à jour les tâches
        }

    }

    /**
     * Met à jour les tâches dans la colonne "A faire" et "Fini" 
     * en fonction de l'état de la checkbox
     */
    private void updateTasks() {
        // Vider les deux zones de texte
        tasks.setText("");
        doneTasks.setText("");

        // Parcourir toutes les tâches et les ajouter à la zone de texte appropriée
        String[] allTasks = tasks.getText().split("\n");
        for (String task : allTasks) {
            if (task.startsWith("- [X] ")) {
                doneTasks.append(task + "\n");
            } else {
                tasks.append(task + "\n");
            }
        }
    }

    
    public static void main(String[] args) {
        new ToDoList_v1();
    }
}