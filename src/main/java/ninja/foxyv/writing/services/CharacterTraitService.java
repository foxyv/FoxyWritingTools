package ninja.foxyv.writing.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

@Service
public class CharacterTraitService {

    File databaseDirectory;

    public CharacterTraitService(@Value(value = "${database.directory}") String databaseDirectory) {
        this.databaseDirectory = new File(databaseDirectory);
    }

    public String[][] firstNames() {
        return loadDB(databaseDirectory, "firstNames.csv");

    }

    public String[][] lastNames() {
        return loadDB(databaseDirectory, "lastNames.csv");
    }

    public static String[][] loadDB(File databaseDirectory, String dbName) {
        File file = new File(databaseDirectory, dbName);

        try(FileReader fr = new FileReader(file)) {
            CSVParser csvParser = new CSVParser(fr, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            return csvParser.getRecords().stream()
                    .map(x -> new String[]{x.get(0), x.get(1)})
                    .toArray(x -> new String[x][]);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
