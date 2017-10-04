package ninja.foxyv.writing.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class CharacterTraitService {

    File characterDatabaseDirectory;

    public CharacterTraitService(@Value(value = "${character.database.directory}") String databaseDirectory) {
        this.characterDatabaseDirectory = new File(databaseDirectory);
    }

    public String[][] firstNames() {
        return loadDB(characterDatabaseDirectory, "firstNames.csv");

    }

    public String[][] lastNames() {
        return loadDB(characterDatabaseDirectory, "lastNames.csv");
    }

    public static String[][] loadDB(File databaseDirectory, String dbName) {
        File file = new File(databaseDirectory, dbName);

        try(FileReader fr = new FileReader(file)) {
            CSVParser csvParser = new CSVParser(fr, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            return csvParser.getRecords().stream()
                    .map(x -> new String[]{x.get(0), x.get(1)})
                    .toArray(String[][]::new);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
