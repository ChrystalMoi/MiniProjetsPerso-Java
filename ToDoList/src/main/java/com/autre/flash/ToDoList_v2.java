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

public class ToDoList_v2 extends JFrame implements ActionListener {
    
    private JTextField input;
    private JTextArea tasks; //colonne pour les taches a faire
    private JButton addBtn, deleteBtn, editBtn; //les boutons
    private JCheckBox doneBox;
    private JTextArea doneTasks; //Colonne pour les taches fini

    public ToDoList_v2() {
        super("La ToDo List de Madame Chrystal");

        // Créer les composants
        input = new JTextField(30);
        tasks = new JTextArea(20, 30);
        tasks.setEditable(false);
        doneBox = new JCheckBox("Fait");
        addBtn = new JButton("Ajouter ");
        deleteBtn = new JButton("Supprimer ");
        editBtn = new JButton("Modifier");
        doneTasks = new JTextArea(20, 30);
        doneTasks.setEditable(false);

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

        // Créer les composants pour la colonne "A faire"
        tasks = new JTextArea(20, 30);
        tasks.setEditable(false);
        JScrollPane tasksScrollPane = new JScrollPane(tasks);

        // Créer les composants pour la colonne "Fini"
        doneTasks = new JTextArea(20, 30);
        doneTasks.setEditable(false);
        JScrollPane doneTasksScrollPane = new JScrollPane(doneTasks);

        // Ajouter les titres des colonnes
        JLabel tasksTitle = new JLabel("A faire", JLabel.CENTER);
        JPanel tasksPanel = new JPanel(new BorderLayout());
        tasksPanel.add(tasksTitle, BorderLayout.NORTH);
        tasksPanel.add(tasksScrollPane, BorderLayout.CENTER);

        JLabel doneTasksTitle = new JLabel("Fini", JLabel.CENTER);
        JPanel doneTasksPanel = new JPanel(new BorderLayout());
        doneTasksPanel.add(doneTasksTitle, BorderLayout.NORTH);
        doneTasksPanel.add(doneTasksScrollPane, BorderLayout.CENTER);

        // Créer un JSplitPane pour afficher les deux colonnes côte à côte
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tasksPanel, doneTasksPanel);
        splitPane.setDividerLocation(0.5);
        splitPane.setOneTouchExpandable(true);

        // Ajouter les composants à la fenêtre
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        // Paramétrer la fenêtre
        setSize(650, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //La tache sélectionné est mis en couleur
        tasks.setSelectionColor(Color.MAGENTA);
        
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
                       
        if (e.getSource() == doneBox) {
            // Cocher ou décocher la tâche sélectionnée
            int index = tasks.getCaretPosition();
            if (index < tasks.getText().length()) {
                String line = tasks.getText().substring(tasks.getText().lastIndexOf('\n', index - 1) + 1, index).trim();
                if (line.startsWith("- ")) {
                    line = line.substring(2);
                }
                if (doneBox.isSelected()) {
                    // Déplacer la tâche de la colonne "A faire" à la colonne "Fini"
                    tasks.select(index, tasks.getText().indexOf('\n', index));
                    String task = tasks.getSelectedText();
                    tasks.replaceSelection("");
                    doneTasks.append("- " + line + " " + task + "\n");
                } else {
                    // Déplacer la tâche de la colonne "Fini" à la colonne "A faire"
                    doneTasks.select(index, doneTasks.getText().indexOf('\n', index));
                    String task = doneTasks.getSelectedText();
                    doneTasks.replaceSelection("");
                    tasks.append("- " + line + " " + task.substring(2) + "\n");
                }
            }
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

        // Parcourir toutes les tâches finies et les ajouter à la colonne "Fini"
        String[] allDoneTasks = doneTasks.getText().split("\n");
        for (String doneTask : allDoneTasks) {
            if (doneTask.startsWith("- [X] ")) {
                doneTasks.append(doneTask + "\n");
            }
        }
    }


    public static void main(String[] args) {
        //new ToDoList_v2();
        ToDoList_v2 toDoList = new ToDoList_v2();
    }
}
