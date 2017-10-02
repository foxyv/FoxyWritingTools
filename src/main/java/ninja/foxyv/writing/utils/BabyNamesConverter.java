package ninja.foxyv.writing.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class BabyNamesConverter {
    public static void main(String[] args) throws IOException {
        File lastNamesFile = new File("database/CSV_Database_of_Last_Names.csv");
        File lastNamesOut = new File("database/lastNames.csv");

        try (FileReader fr = new FileReader(lastNamesFile); FileWriter fw = new FileWriter(lastNamesOut)) {
            CSVParser parser = new CSVParser(fr, CSVFormat.EXCEL.withFirstRecordAsHeader());
            CSVPrinter printer = new CSVPrinter(fw, CSVFormat.EXCEL.withFirstRecordAsHeader());

            Iterator<CSVRecord> iterator = parser.iterator();
            StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
                .forEach(x -> printRecord(printer, x));
        }
    }

    public static void printRecord(CSVPrinter printer, CSVRecord record) {
        try {
            printer.printRecord(record.get(0), "0.01");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void convertBabyFirstNames() throws IOException {
        File babyNamesFile = new File("database/baby-names.csv");
        File firstNames = new File("database/firstNames.csv");

        try (FileReader fr = new FileReader(babyNamesFile); FileWriter fw = new FileWriter(firstNames)) {
            CSVParser parser = new CSVParser(fr, CSVFormat.EXCEL.withFirstRecordAsHeader());
            CSVPrinter printer = new CSVPrinter(fw, CSVFormat.EXCEL.withFirstRecordAsHeader());
            printer.printRecord("name", "weight");

            Iterator<CSVRecord> itr = parser.iterator();
            while(itr.hasNext()) {
                CSVRecord record = itr.next();
                Map<String, String> map = record.toMap();
                String name = map.get("name");
                String weight = map.get("percent");
                printer.printRecord(name, weight);
            }
        }
    }
}
