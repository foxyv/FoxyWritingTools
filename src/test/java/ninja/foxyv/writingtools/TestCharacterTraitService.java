package ninja.foxyv.writingtools;

import ninja.foxyv.writing.services.CharacterTraitService;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

public class TestCharacterTraitService {
    @Test
    public void test() {
        File databaseDirectory = new File("database");

        String[][] weightedNames = CharacterTraitService.loadDB(databaseDirectory, "firstNames.csv");
        Assert.assertTrue(weightedNames.length > 0);
        for(String[] weightedName : weightedNames) {
            Assert.assertFalse(weightedName[0].trim().isEmpty());
            Float.parseFloat(weightedName[1]);
        }
    }
}
