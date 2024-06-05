package org.example;

import java.util.HashMap;
import java.util.Map;

public class Data {
    private String title;
    private String nom;
    private String prenom;
    private String facture;
    private String date;
    private String objet;
    private String montant;
    private Map<String, String> dataObject;

    public String getTitle() {
        return title;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getFacture() {
        return facture;
    }

    public String getDate() {
        return date;
    }

    public String getObjet() {
        return objet;
    }

    public String getMontant() {
        return montant;
    }

    public Data(String title, String nom, String prenom, String facture, String date, String objet, String montant) {
        this.title = title;
        this.nom = nom;
        this.prenom = prenom;
        this.facture = facture;
        this.date = date;
        this.objet = objet;
        this.montant = montant;
        this.dataObject = new HashMap<>();
        addValuesToMap();
    }

    private void addValuesToMap() {
        dataObject.put("Title", this.title);
        dataObject.put("Nom", this.nom);
        dataObject.put("Prénom", this.prenom);
        dataObject.put("Facture", this.facture);
        dataObject.put("Date", this.date);
        dataObject.put("Objet", this.objet);
        dataObject.put("Montant", this.montant);
    }

    public Map<String, String> getData() {
        return this.dataObject;
    }
}
