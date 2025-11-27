package barman;
import barman.Types.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* Αυτή η κλάση είναι υπεύθυνη για τη διαχείριση των effect ενός Action, δηλαδή
αλλαγές add/delete στο currentState */

public class Effects {

    //Λίστα με τα effect και χάρτης αντιστοίχησης ονομάτων αντικςιμένων
    private final List<String> effects;
    private final Map<String, Anything> objectsMap;

    public Effects(List<String> effects, Map<String, Anything> objectsMap){

        //Αρχικοποιήσεις
        this.effects = effects;
        this.objectsMap = objectsMap;
    }

    /* Βοηθητική μέθοδος για το Map connections, στην ουσία ταιριάζει την παράμετρο του problem με
     την παράμετρο στον κώδικα μας, δηλαδή ταιριάζει πx το ?x_0 με το shot2 */
    private Anything getObject(String name, Map<String, String> connections){
        String objectName = name;
        if (name.startsWith("?")){
            objectName = connections.get(name);
        }
        if (objectName == null){
            System.err.println("Error with variable name " +name);
            return null;
        }
        return objectsMap.get(objectName);
    }

    //Μέθοδος για την εφαρμογή των effect στο currentState
    public void applyEffects(WorldState currentState, List<String> effects, Map<String, String> connections){

        if(effects == null || effects.isEmpty()){
            return;
        }

        // Επεξεργασία κάθε effect ξεχωριστά
        int i = 0;
        while (i < effects.size()) {
            String firstToken = effects.get(i);

            // Έλεγχος αν το effect είναι αρνητικό
            boolean isNegative = firstToken.equals("not");
            int predicateIndex = isNegative ? i + 1 : i;

            if (predicateIndex >= effects.size()) {
                System.err.println("Error occurred while parsing the effects");
                return;
            }

            String predicate = effects.get(predicateIndex);

            // Προσδιορισμός αριθμού παραμέτρων για το predicate
            int paramCount = getParameterCount(predicate);
            int endIndex = predicateIndex + paramCount + 1;

            if (endIndex > effects.size()) {
                System.err.println("Malformed effect for predicate '" + predicate + "': expected " + paramCount + " parameters");
                return;
            }

            // Εξαγωγή του συγκεκριμένου effect
            List<String> singleEffect = effects.subList(i, endIndex);
            applySingleEffect(currentState, singleEffect, connections);

            i = endIndex; // Μετάβαση στο επόμενο effect
        }
    }

    // Βοηθητική μέθοδος για την εφαρμογή ενός μόνο effect
    private void applySingleEffect(WorldState currentState, List<String> effect, Map<String, String> connections) {
        String[] effectArray = effect.toArray(new String[0]);

        boolean isNegative = false; //flag για να ξέρω αν θα έχω add (false) ή delete (true)
        String predicate; // Το ίδιο το predicate, clean, empty, κλπ
        String[] parts; // Τα κομμάτια που προέκυψαν από το κόψιμο με το predicate, πχ shaker 1, shot 2 κλπ

        //Ελέγχω αν το effect είναι αρνητικό
        if(effectArray[0].equals("not")){
            isNegative = true;
            predicate = effectArray[1];
            parts = new String[effectArray.length - 2];
            //Οι παράμετροι είναι τα στοιχεία από το index 2 και μετά
            parts = Arrays.copyOfRange(effectArray, 2, effectArray.length);
        }else{
        /* Αν δεν είναι αρνητικό, σημαίνει ότι το predicate είναι το πρώτο
        κομμάτι (index 0) της γραμμής, έναντι του not */
            predicate = effectArray[0];
            parts = new String[effectArray.length - 1];
            parts = Arrays.copyOfRange(effectArray, 1, effectArray.length);
        }

        /* Εδώ είναι που γίνονται οι αλλαγές, εντός της switch τα δεδομένα της currentState αλλάζουν
         με βάση τα effect */
        switch (predicate) {
            case "clean":
                Container containerC = (Container) getObject(parts[0], connections);
                if (containerC != null) {
                    if (isNegative) {
                        currentState.clean.remove(containerC);
                    } else {
                        currentState.clean.add(containerC);
                    }
                }
                break;
            case "empty":
                Container containerE = (Container) getObject(parts[0], connections);
                if (containerE != null) {
                    if (isNegative) {
                        currentState.empty.remove(containerE);
                    } else {
                        currentState.empty.add(containerE);
                    }
                }
                break;
            case "handEmpty":
                Hand handE = (Hand) getObject(parts[0], connections);
                if (handE != null) {
                    if (isNegative) {
                        currentState.handEmpty.remove(handE);
                    } else {
                        currentState.handEmpty.add(handE);
                    }
                }
                break;
            case "ontable":
                Container containerO = (Container) getObject(parts[0], connections);
                if (containerO != null) {
                    if (isNegative) {
                        currentState.onTable.remove(containerO);
                    } else {
                        currentState.onTable.add(containerO);
                    }
                }
                break;
            case "shaked":
                Shaker shaker = (Shaker) getObject(parts[0], connections);
                if (shaker != null) {
                    if (isNegative) {
                        currentState.shaked.remove(shaker);
                    } else {
                        currentState.shaked.add(shaker);
                    }
                }
                break;
            case "unshaked":
                Shaker shakerU = (Shaker) getObject(parts[0], connections);
                if (shakerU != null) {
                    if (isNegative) {
                        currentState.unshaked.remove(shakerU);
                    } else {
                        currentState.unshaked.add(shakerU);
                    }
                }
                break;
            case "cocktailPart1":
                Cocktail cocktail1 = (Cocktail) getObject(parts[0], connections);
                Ingredient ingredient1 = (Ingredient) getObject(parts[1], connections);
                if (cocktail1 != null && ingredient1 != null) {
                    if (isNegative) {
                        currentState.cocktailPart1.remove(cocktail1);
                    } else {
                        currentState.cocktailPart1.put(cocktail1, ingredient1);
                    }
                }
                break;
            case "cocktailPart2":
                Cocktail cocktail2 = (Cocktail) getObject(parts[0], connections);
                Ingredient ingredient2 = (Ingredient) getObject(parts[1], connections);
                if (cocktail2 != null && ingredient2 != null) {
                    if (isNegative) {
                        currentState.cocktailPart2.remove(cocktail2);
                    } else {
                        currentState.cocktailPart2.put(cocktail2, ingredient2);
                    }
                }
                break;
            case "contains":
                Container container = (Container) getObject(parts[0], connections);
                Beverage content = (Beverage) getObject(parts[1], connections);
                if (container != null && content != null) {
                    if (isNegative) {
                        currentState.contains.remove(container);
                    } else {
                        currentState.contains.put(container, content);
                    }
                }
                break;
            case "dispenses":
                Dispenser dispenser = (Dispenser) getObject(parts[0], connections);
                Ingredient ingredient = (Ingredient) getObject(parts[1], connections);
                if (dispenser != null && ingredient != null) {
                    if (isNegative) {
                        currentState.dispenses.remove(dispenser);
                    } else {
                        currentState.dispenses.put(dispenser, ingredient);
                    }
                }
                break;
            case "holding":
                Hand hand = (Hand) getObject(parts[0], connections);
                Container item = (Container) getObject(parts[1], connections);
                if (hand != null && item != null) {
                    if (isNegative){
                        currentState.holding.remove(hand);
                    }else{
                        currentState.holding.put(hand, item);
                    }
                }
                break;
            case "used":
                Container containerU = (Container) getObject(parts[0], connections);
                Ingredient ingredient3 = (Ingredient) getObject(parts[1], connections);
                if (containerU != null && ingredient3 != null) {
                    if (isNegative) {
                        currentState.used.remove(containerU);
                    } else {
                        currentState.used.put(containerU, ingredient3);
                    }
                }
                break;
            case "shakerLevel":
                Shaker shaker2 = (Shaker) getObject(parts[0], connections);
                Level level = (Level) getObject(parts[1], connections);
                if (shaker2 != null && level != null) {
                    if (isNegative) {
                        currentState.shakerLevel.remove(shaker2);
                    } else {
                        currentState.shakerLevel.put(shaker2, level);
                    }
                }
                break;
            default:
                System.err.println("Unknown predicate in effects: " + predicate);
                break;
        }
    }

    //Βοηθητική μέθοδος για τον προσδιορισμό αριθμού παραμέτρων
    private int getParameterCount(String predicate) {
        switch(predicate) {
            case "clean": case "empty": case "handEmpty": case "ontable":
            case "shaked": case "unshaked":
                return 1;
            case "contains": case "dispenses": case "holding": case "used":
            case "shakerLevel": case "next": case "shakerEmptyLevel":
            case "cocktailPart1": case "cocktailPart2":
                return 2;
            default:
                return 0;
        }
    }
}