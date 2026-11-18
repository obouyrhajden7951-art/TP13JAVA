package com.example.tp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Fichier créé dans le dossier d'exécution du programme (".ser" = sérialisé)
        final String filePath = "employees.ser";

        // 1) Création d'une liste d'employés
        List<Employee> staff = new ArrayList<>();
        staff.add(new Employee(1, "Youssef", 3000.0, "pwdYoussef"));
        staff.add(new Employee(2, "Fatima",  3500.5, "pwdFatima"));
        staff.add(new Employee(3, "Omar",    2800.75, "pwdOmar"));

        // 2) Sérialisation : on écrit la liste dans le fichier
        try {
            SerializationUtil.serializeEmployees(filePath, staff);
            System.out.println("→ Sérialisation réussie dans " + filePath);
        } catch (IOException e) {
            System.err.println("Erreur de sérialisation : " + e.getMessage());
            return; // inutile de continuer si l'écriture a échoué
        }

        // 3) Désérialisation : on relit le fichier pour recréer les objets
        List<Employee> loaded;
        try {
            loaded = SerializationUtil.deserializeEmployees(filePath);
            System.out.println("→ Désérialisation réussie, objets restaurés :");
        } catch (IOException | ClassNotFoundException e) {
            // Le "|" permet d'attraper deux types d'exceptions dans le même catch
            System.err.println("Erreur de désérialisation : " + e.getMessage());
            return;
        }

        // 4) Affichage : on constate que password vaut null (champ transient)
        for (Employee emp : loaded) {
            System.out.println("  " + emp);
        }

        System.out.println("\nNote : le champ 'password' est transient, il n'a pas été enregistré → null.");
    }
}
