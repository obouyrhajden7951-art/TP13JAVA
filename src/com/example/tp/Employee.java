package com.example.tp;

import java.io.Serializable;

// "implements Serializable" autorise Java à transformer cet objet en suite d'octets.
// Serializable est une interface "marqueur" : elle ne contient aucune méthode.
public class Employee implements Serializable {

    // Numéro de version de la classe. Il est écrit dans le fichier et comparé à la lecture.
    // Si la classe change et que ce numéro ne correspond plus : InvalidClassException.
    private static final long serialVersionUID = 1L;

    private final int    id;       // identifiant unique
    private final String name;     // nom complet
    private final double salary;   // salaire de base

    // transient = ce champ est IGNORÉ lors de la sérialisation.
    // Utile pour les données sensibles (mot de passe) ou inutiles à sauvegarder.
    // Après lecture du fichier, sa valeur sera la valeur par défaut : null.
    private transient String password;

    public Employee(int id, String name, double salary, String password) {
        this.id       = id;
        this.name     = name;
        this.salary   = salary;
        this.password = password;
    }

    // Getters : servent à vérifier les valeurs après la désérialisation
    public int    getId()       { return id; }
    public String getName()     { return name; }
    public double getSalary()   { return salary; }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        // %d = entier, %s = texte, %.2f = décimal à 2 chiffres
        return String.format(
            "Employee[id=%d, name=%s, salary=%.2f, password=%s]",
            id, name, salary,
            (password == null ? "null" : password)
        );
    }
}