package ninja.foxyv.writing.entities;

/**
 * Describes a gross personality or physical trait for a character.
 */
public class CharacterTrait {
    String trait;
    String description;
    Type type;

    public enum Type {
        Positive, Neutral, Negative, Other;

        public static Type fromString(String name) {
            try {
                return Type.valueOf(name);
            } catch(Exception ex) {
                for(Type type : Type.values()) {
                    if(type.name().equalsIgnoreCase(name)) {
                        return type;
                    }
                }

                return Other;
            }
        }
    }
}
