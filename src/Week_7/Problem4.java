package Week7;

public class Problem4 {

    // Interfaces defining combat capabilities
    public interface Attackable {
        String attack();
        String attack(String weaponName);
    }

    public interface Defendable {
        String defend();
    }

    // Abstract class representing combat characters
    public abstract static class GameCharacter {
        private static int characterCounter = 0;
        private final String characterId;

        public GameCharacter() {
            characterCounter++;
            this.characterId = "CHAR-" + characterCounter;
        }

        public String getCharacterId() {
            return characterId;
        }

        public abstract String getSpecialMove();
    }

    // Warrior: Character implementing both Attackable (overloaded) and Defendable
    public static class Warrior extends GameCharacter implements Attackable, Defendable {
        private String name;

        public Warrior(String name) {
            super();
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Warrior name cannot be blank.");
            }
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String attack() {
            return name + " strikes with a blade";
        }

        @Override
        public String attack(String weaponName) {
            char first = Character.toLowerCase(weaponName.charAt(0));
            String article = (first == 'a' || first == 'e' || first == 'i' || first == 'o' || first == 'u') ? "an " : "a ";
            return name + " strikes with " + article + weaponName;
        }

        @Override
        public String defend() {
            return name + " raises a shield";
        }

        @Override
        public String getSpecialMove() {
            return name + " unleashes Whirlwind Slash";
        }
    }

    // Trap: Arena object implementing only Defendable, not a GameCharacter
    public static class Trap implements Defendable {
        private String trapType;

        public Trap(String trapType) {
            if (trapType == null || trapType.trim().isEmpty()) {
                throw new IllegalArgumentException("trapType cannot be blank.");
            }
            this.trapType = trapType;
        }

        public String getTrapType() {
            return trapType;
        }

        @Override
        public String defend() {
            return trapType + " triggers automatically";
        }
    }

    // Polymorphically resolves defense for all Defendables
    public static void resolveDefense(Defendable[] combatants) {
        if (combatants != null) {
            for (Defendable combatant : combatants) {
                if (combatant != null) {
                    System.out.println(combatant.defend());
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Warrior Actions ---");
        Warrior w = new Warrior("Kael");
        System.out.println(w.attack());
        System.out.println(w.attack("Iron Sword"));
        System.out.println(w.defend());
        System.out.println(w.getSpecialMove());

        System.out.println("\n--- Trap Defense ---");
        Trap t = new Trap("Spike Pit");
        System.out.println(t.defend());

        System.out.println("\n--- Polymorphic resolveDefense ---");
        resolveDefense(new Defendable[]{ w, t });
    }
}
