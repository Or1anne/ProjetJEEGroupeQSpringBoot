package org.example.projetjeegroupeqspringboot.util;

import org.example.projetjeegroupeqspringboot.entity.Pay;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitaire pour générer les fiches de paie en PDF
 */
public class PayPdfGenerator {

    /**
     * Génère un PDF de fiche de paie
     * @param pay La fiche de paie à générer
     * @param outputStream Le flux de sortie où écrire le PDF
     */
    public static void generatePayPdf(Pay pay, OutputStream outputStream) {
        try {
            String htmlContent = createHtmlTemplate(pay);
            Document document = Jsoup.parse(htmlContent);
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(document.html());
            renderer.layout();
            renderer.createPDF(outputStream);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }

    /**
     * Crée le contenu HTML à partir du template
     */
    private static String createHtmlTemplate(Pay pay) {
        try {
            String template = loadTemplateFile();
            return replaceTemplateVariables(template, pay);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement du template", e);
        }
    }

    /**
     * Charge le fichier template HTML
     */
    private static String loadTemplateFile() throws Exception {
        ClassLoader classLoader = PayPdfGenerator.class.getClassLoader();
        return new String(
                Files.readAllBytes(Paths.get(classLoader.getResource("templates/PayslipTemplate.html").toURI())),
                StandardCharsets.UTF_8
        );
    }

    /**
     * Remplace les variables dans le template par les valeurs réelles
     */
    private static String replaceTemplateVariables(String template, Pay pay) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfMois = new SimpleDateFormat("MMMM yyyy", java.util.Locale.FRENCH);

        Map<String, String> variables = new HashMap<>();
        variables.put("${periode}", pay.getDate() != null ? sdfMois.format(pay.getDate()) : "N/A");
        variables.put("${employeeId}", String.valueOf(pay.getEmployee().getId()));
        variables.put("${nomComplet}", pay.getEmployee().getLastName().toUpperCase() + " " + pay.getEmployee().getFirstName());
        variables.put("${poste}", pay.getEmployee().getPost() != null ? pay.getEmployee().getPost() : "-");
        variables.put("${departement}", pay.getEmployee().getDepartment() != null ? pay.getEmployee().getDepartment().getDepartmentName() : "-");
        variables.put("${salaireBase}", String.format("%.2f", pay.getEmployee().getSalary()));
        variables.put("${bonus}", String.format("%.2f", pay.getBonus()));
        variables.put("${deductions}", String.format("%.2f", pay.getDeductions()));
        variables.put("${net}", String.format("%.2f", pay.getSalaryNet()));
        variables.put("${dateActuelle}", sdf.format(new Date()));

        String result = template;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }
}

