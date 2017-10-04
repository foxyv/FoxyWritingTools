package ninja.foxyv.writing.utils;

import org.apache.commons.csv.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * A utility for sorting character traits files automatically.
 */
public class TraitsSorter {
    private static final String traitsCSV = "database/characters/traits.csv";
    private static final CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreEmptyLines().withIgnoreSurroundingSpaces().withTrim();

    public static void main(String[] args) throws IOException {
        File csvFile = new File(traitsCSV);
        File tempFile = new File("database/temp/traits.temp.csv");

        if(tempFile.getParentFile().mkdirs()) {
            System.out.println("Creating temp directory for sorted traits file.");
        }

        if(tempFile.createNewFile()) {
            System.out.println("Creating temporary file to store sorted traits: " + tempFile.getAbsolutePath());
        }

        final List<CSVRecord> records;
        try(FileReader fr = new FileReader(traitsCSV)) {

            CSVParser csvParser = new CSVParser(fr, format);
            records = csvParser.getRecords();


        }


        int traitColumnLength = records.stream().map(x -> ("" + x.get("Trait")).length()).max(Integer::compare).orElse(0);
        int typeColumnLength = records.stream().map(x -> ("" + x.get("Type")).length()).max(Integer::compare).orElse(0);
        int descriptionColumnLength = records.stream().map(x -> ("" + x.get("Description")).length()).max(Integer::compare).orElse(0);

        try(FileWriter fw = new FileWriter(tempFile)) {
            CSVPrinter printer = CSVFormat.DEFAULT.withEscape('\\').withQuoteMode(QuoteMode.NONE).withHeader("Trait", "Type", "Description").print(fw);

            records.stream().sorted(TraitsSorter::compareTraitTypes)
                    .forEach(x -> writeRecord(printer, x, traitColumnLength, typeColumnLength, descriptionColumnLength));
        }

    }

    private static void writeRecord(CSVPrinter printer, CSVRecord record, int traitColumnLength, int typeColumnLength, int descriptionColumnLength) {
        String trait = padRight("" + record.get("Trait"), traitColumnLength + 1);
        String type = padRight("" + record.get("Type"), typeColumnLength + 1);
        String description = padRight("" + record.get("Description"), descriptionColumnLength + 1);

        try {
            printer.printRecord(trait, type, description);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String padRight(String aString, int length) {
        if(aString.length() >= length) {
            return aString;
        }

        StringBuilder sb = new StringBuilder(aString);
        while(sb.length() < length) {
            sb.append(' ');
        }
        return sb.toString();
    }

    private static int compareTraitNames(CSVRecord record1, CSVRecord record2) {
        String name1 = "" + record1.get("Trait");
        String name2 = "" + record2.get("Trait");
        return name1.compareTo(name2);
    }

    private static int compareTraitTypes(CSVRecord record1, CSVRecord record2) {
        String name1 = "" + record1.get("Type");
        String name2 = "" + record2.get("Type");
        int comparison = name1.compareTo(name2);
        if(comparison == 0 ) {
            return compareTraitNames(record1, record2);
        } else {
            return comparison;
        }
    }
}
