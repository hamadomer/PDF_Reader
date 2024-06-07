package org.example;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.Scanner;

public class CreatePdf {

    public void dataToPdf() throws IOException {
        Data data = new Data();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Give Document : 1 for Paiement 2 for Acquittement");
            String userTypeRes = scanner.nextLine();
            if(userTypeRes.equals("1") || userTypeRes.equals("2")) {
                if (userTypeRes.equals("1")) {
                    data.setTitle("Demande de paiement de facture");
                } else {
                    data.setTitle("Acquittement de facture");
                }
            } else {
                throw new InvalidObjectException("Invalid input : only 1 or 2 accepted, restart the programme");
            }
            System.out.println("Nom :");
            data.setNom(scanner.nextLine());
            System.out.println("Prénom :");
            data.setPrenom(scanner.nextLine());
            System.out.println("Facture :");
            data.setFacture(scanner.nextLine());
            System.out.println("Date DD/MM/YY");
            data.setDate(scanner.nextLine());
            System.out.println("Objet :");
            data.setObjet(scanner.nextLine());
            System.out.println("Montant :");
            data.setMontant(scanner.nextLine());
            PdfHandler pdf = new PdfHandler();
            System.out.println("Last thing provide path if you may :");
            pdf.dataToPdf(data, scanner.nextLine());
            break;
        }
    }
}
