package com.example.tp;

import java.io.*;
import java.util.List;

// Classe utilitaire : contient les méthodes pour écrire et relire une liste d'employés.
public class SerializationUtil {

    // SÉRIALISATION : objet Java -> fichier binaire
    public static void serializeEmployees(String path, List<Employee> data)
            throws IOException {
        // FileOutputStream ouvre le fichier en écriture (octets).
        // ObjectOutputStream sait écrire des objets Java par-dessus.
        // try-with-resources : les flux sont fermés automatiquement à la fin du bloc.
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(path))) {
            // writeObject écrit la liste ET tous les objets qu'elle contient.
            // ArrayList est lui-même sérialisable, et Employee aussi : tout passe.
            oos.writeObject(data);
        }
    }

    // DÉSÉRIALISATION : fichier binaire -> objet Java
    // @SuppressWarnings : on fait un cast non vérifié (Object -> List<Employee>),
    // le compilateur émet un avertissement que l'on désactive volontairement.
    @SuppressWarnings("unchecked")
    public static List<Employee> deserializeEmployees(String path)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(path))) {
            // readObject renvoie un Object : on le convertit en List<Employee>.
            // ClassNotFoundException : levée si la classe Employee est introuvable.
            return (List<Employee>) ois.readObject();
        }
    }
}