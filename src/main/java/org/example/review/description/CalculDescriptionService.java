package org.example.review.description;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.example.review.model.Facture;

public class CalculDescriptionService {

    public String calculerDescription(Facture facture) {
        
        return switch (facture.getType()) {
            case PAIEMENT -> calculerDescriptionPaiement(facture);
            case ACQUITTEMENT -> calculerDescriptionFromConditions(facture);
        };
        
    }

    public String calculerDescriptionPaiement(Facture facture) {
        String[] dateParts = facture.getDate().split("/");
        int year = dateParts.length == 3 ? Integer.parseInt(dateParts[2]) : 0;

        String amountString = extractAmount(facture.getMontant());
        int money = amountString.isEmpty() ? 0 : Integer.parseInt(amountString.replace(" ", ""));

        if (money > 1000 && year < 2000) {
            return "Demande de paiement MVP (" + facture.getMontant() + ") via Facturo1999, de la facture " + facture.getNumero();
        } else if (money > 1000) {
            return "Demande de paiement MVP (" +facture.getMontant() + "), de la facture " + facture.getNumero() + ", émise le " + facture.getDate();
        } else {
            return handleObjectConditions(facture);
        }
    }

    public String calculerDescriptionFromConditions(Facture facture) {
        String fullName = facture.getPrenom() + " " + facture.getNom();
        String amountString = extractAmount(facture.getMontant());
        int money = amountString.isEmpty() ? 0 : Integer.parseInt(amountString.replace(" ", ""));

        switch (fullName) {
            case "Emmanuel Macron":
                return "Acquittement automatique de la facture émise par le Président de la République française (" + facture.getNumero() + ")";
            case "Joe Biden":
                return "Acquittement automatique de la facture émise par le Président des Etats-Unis d'Amérique (" + facture.getNumero() + ")";
            case "Elon Musk":
                return "Acquittement automatique de la facture émise par le Directeur Général de Tesla (" + facture.getNumero() + ")";
            case "Ursula von der Leyen":
                return "Acquittement automatique de la facture émise par la Présidente de la commission européenne (" + facture.getNumero() + ")";
            default:
                if (money > 1000) {
                    return "Acquittement automatique de la facture " + facture.getNumero() + " MVP (" + facture.getMontant() + ")";
                }
                break;
        }

        return "Acquittement automatique de la facture " + facture.getNumero();
    }

    public String handleObjectConditions(Facture facture) {
        String object = facture.getObjet();

        if (object.contains("Donuts") && object.contains("Tacos")) {
            return "Commande Donuts & Tacos (" + facture.getNumero() + ")";
        } else if (object.contains("Donuts")) {
            return "Commande Donuts (" + facture.getNumero() + ")";
        } else if (object.contains("Tacos")) {
            return "Commande Tacos (" + facture.getNumero() + ")";
        } else {
            return "Demande de paiement à effectuer via Facturo1999 de la facture " + facture.getNumero() + ", pour un montant de " + facture.getMontant();
        }
    }

    public String extractAmount(String montant) {
        Pattern pattern = Pattern.compile("\\d[\\d\\s]*");
        Matcher matcher = pattern.matcher(montant);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

}
