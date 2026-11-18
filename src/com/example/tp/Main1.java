package com.example.tp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main1 {
    public static void main(String[] args) {
        String file = "chat.ser";

        // 1) Création de l'historique
        List<ChatMessage> history = new ArrayList<>();
        history.add(new ChatMessage("Youssef", "Salam tout le monde"));
        history.add(new ChatMessage("Fatima", "Wa ʿalaykum as-salām"));
        history.add(new ChatMessage("Omar",   "Comment ça va ?"));

        // 2) Sauvegarde dans le fichier
        try {
            ChatHistory.save(file, history);
            System.out.println("Historique sauvegardé dans " + file);
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde : " + e.getMessage());
            return;
        }

        // 3) Chargement depuis le fichier
        List<ChatMessage> loaded;
        try {
            loaded = ChatHistory.load(file);
            System.out.println("Historique chargé :");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur chargement : " + e.getMessage());
            return;
        }

        // 4) Affichage. getLength() est correct car recalculé dans readExternal.
        for (ChatMessage msg : loaded) {
            System.out.printf("  [%d chars] %s%n", msg.getLength(), msg);
        }
    }
}