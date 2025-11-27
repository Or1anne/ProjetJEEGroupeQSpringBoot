package org.example.projetjeegroupeqspringboot.entity.enumeration;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GradeConverter implements AttributeConverter<Grade, String> {

    @Override
    public String convertToDatabaseColumn(Grade attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }

    @Override
    public Grade convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }

        String value = dbData.trim();

        // Essayer d'abord de matcher avec les noms d'enum
        try {
            return Grade.valueOf(value);
        } catch (IllegalArgumentException e) {
            // Si ça ne marche pas, essayer avec les labels français
            for (Grade grade : Grade.values()) {
                if (grade.getLabel().equalsIgnoreCase(value)) {
                    return grade;
                }
            }
            // Si rien ne correspond, logger et retourner null au lieu de lancer une exception
            System.err.println("Valeur de grade inconnue dans la base de données : '" + dbData + "'");
            return null;
        }
    }
}

