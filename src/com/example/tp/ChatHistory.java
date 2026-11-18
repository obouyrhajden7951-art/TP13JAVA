package com.example.tp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Sauvegarde et chargement d'une liste de ChatMessage.
public class ChatHistory {

    // SAUVEGARDE
    public static void save(String path, List<ChatMessage> history) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            // On écrit d'abord le NOMBRE de messages pour savoir combien en lire ensuite
            oos.writeInt(history.size());
            // Puis chaque message, grâce à notre propre méthode writeExternal.
            // ObjectOutputStream est un ObjectOutput, on peut donc le lui passer directement.
            for (ChatMessage msg : history) {
                msg.writeExternal(oos);
            }
        }
    }

    // CHARGEMENT
    public static List<ChatMessage> load(String path) throws IOException, ClassNotFoundException {
        List<ChatMessage> history = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            int count = ois.readInt();             // combien de messages ?
            for (int i = 0; i < count; i++) {
                ChatMessage msg = new ChatMessage();  // objet vide (constructeur sans argument)
                msg.readExternal(ois);                // on le remplit depuis le fichier
                history.add(msg);
            }
        }
        return history;
    }
}
