package org.example.review.model;

public class Facture {
    
    public enum Type{
        ACQUITTEMENT, PAIEMENT
    }
    
    private Type type;
    private String nom;
    private String prenom;
    private String numero;
    private String date;
    private String objet;
    private String montant;
    
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String facture) {
        this.numero = facture;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getObjet() {
        return objet;
    }
    public void setObjet(String objet) {
        this.objet = objet;
    }
    public String getMontant() {
        return montant;
    }
    public void setMontant(String montant) {
        this.montant = montant;
    }
    
    @Override
    public String toString() {
        return "Facture [type=" + type + ", nom=" + nom + ", prenom=" + prenom + ", facture=" + numero + ", date=" + date + ", objet=" + objet
                + ", montant=" + montant + "]";
    }
    
}
