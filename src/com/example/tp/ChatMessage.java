package com.example.tp;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.Instant;

// Externalizable : contrairement à Serializable, c'est NOUS qui décidons exactement
// quoi écrire et quoi lire, dans quel ordre et dans quel format.
public class ChatMessage implements Externalizable {

    private static final long serialVersionUID = 2L;

    // Version du FORMAT binaire que nous écrivons (différente du serialVersionUID).
    // Si on change le format plus tard, on passera à 2 pour détecter les anciens fichiers.
    private static final int FORMAT_VERSION = 1;

    // Pas de "final" ici : readExternal doit pouvoir modifier ces champs
    private String user;       // nom d'utilisateur
    private String message;    // contenu du message
    private Instant timestamp; // date et heure d'envoi

    // Champ dérivé (calculable à partir de message).
    // Avec Externalizable, "transient" n'a en fait aucun effet : seuls les champs
    // écrits à la main dans writeExternal sont sauvegardés. Il reste ici à titre d'indication.
    private transient int length;

    // OBLIGATOIRE : constructeur public sans argument.
    // Java l'appelle pour créer l'objet vide, puis appelle readExternal pour le remplir.
    public ChatMessage() { }

    public ChatMessage(String user, String message) {
        this.user      = user;
        this.message   = message;
        this.timestamp = Instant.now();   // date et heure actuelles
        this.length    = message.length();
    }

    // ÉCRITURE : nous écrivons les champs un par un, dans un ordre précis
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(FORMAT_VERSION);              // 1) numéro de version du format

        out.writeUTF(user);                        // 2) nom d'utilisateur
        out.writeUTF(message);                     // 3) message
        out.writeLong(timestamp.toEpochMilli());   // 4) date en millisecondes depuis 1970
        // length n'est PAS écrit : on le recalcule à la lecture
    }

    // LECTURE : exactement le même ordre que dans writeExternal, sinon les données sont fausses
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int version = in.readInt();                // 1) on lit d'abord la version
        if (version != FORMAT_VERSION) {
            // Format inconnu : on refuse plutôt que de lire des données erronées
            throw new IOException("Version de format inconnue: " + version);
        }

        user      = in.readUTF();                           // 2)
        message   = in.readUTF();                           // 3)
        timestamp = Instant.ofEpochMilli(in.readLong());    // 4) reconstruit l'Instant
        length    = message.length();                       // recalcul du champ dérivé
    }

    // Getters pour l'affichage
    public String getUser()       { return user; }
    public String getMessage()    { return message; }
    public Instant getTimestamp() { return timestamp; }
    public int getLength()        { return length; }

    @Override
    public String toString() {
        return String.format("%s [%s]: %s", timestamp, user, message);
    }
}