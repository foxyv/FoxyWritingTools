package ninja.foxyv.writing.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ninja.foxyv.writing.entities.Character;
import ninja.foxyv.writing.services.CharacterTraitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

@RestController
@RequestMapping("character")
public class CharacterGeneratorController {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    CharacterTraitService traitService;

    SecureRandom srand = new SecureRandom();

    @RequestMapping("generate")
    public ResponseEntity<Character> generateCharacter() {
        String[][] firstNames = traitService.firstNames();
        String[][] lastNames = traitService.lastNames();

        Character character = new Character();
        character.firstName = firstNames[srand.nextInt(firstNames.length)][0];
        character.lastName = lastNames[srand.nextInt(lastNames.length)][0];

        return new ResponseEntity<>(character, HttpStatus.OK);
    }

    @RequestMapping("firstname")
    public ResponseEntity<String> firstname() {
        String[][] firstNames = traitService.firstNames();
        String firstName = firstNames[srand.nextInt(firstNames.length)][0];

        return new ResponseEntity<>(firstName, HttpStatus.OK);
    }
}
