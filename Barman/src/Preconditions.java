package barman;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import barman.Types.*;

/* Αυτή η κλάση είναι υπεύθυνη για τον έλεγχο προϋποθέσεων των Actions/Methods. Συγκρίνει τα
preconditions με το currentState και καθορίζει αν μπορεί να εκτελεστεί ή όχι */

public class Preconditions {

    //Public final field για να είναι ορατό αλλά αμετάβλητο, αντιστοιχίες όνομα-αντικείμενο
    public final Map<String, Anything> objectsMap;

    public Preconditions(Map<String, Anything> objectsMap) {
        if (objectsMap == null) {
            throw new IllegalArgumentException("Error occurred while checking the preconditions");
        }
        this.objectsMap = objectsMap;
    }

    //Οι μέθοδοι ελέγχουν αν τηρούνται τα preconditions του Action/της Method με βάση το currentState
    public boolean checkPreconditions(Action action, WorldState currentState, Map<String, String> connections) {
        List<String> preconditions = action.getPreconditions();
        return checkPreconditionsList(preconditions, currentState, connections);
    }

    public boolean checkPreconditions(Method method, WorldState currentState, Map<String, String> connections) {
        List<String> preconditions = method.getPreconditions();
        return checkPreconditionsList(preconditions, currentState, connections);
    }

    // Μοναδική μέθοδος για τη διαχείριση και των δύο περιπτώσεων
    private boolean checkPreconditionsList(List<String> preconditions, WorldState currentState, Map<String, String> connections) {
        if (preconditions == null || preconditions.isEmpty()) {
            return true;
        }

        // Επεξεργασία κάθε precondition ξεχωριστά
        int i = 0;
        while (i < preconditions.size()) {
            String firstToken = preconditions.get(i);

            // Έλεγχος αν το precondition είναι αρνητικό
            boolean isNegative = firstToken.equals("not");
            int predicateIndex = isNegative ? i + 1 : i;

            if (predicateIndex >= preconditions.size()) {
                System.err.println("Error occurred while checking the preconditions");
                return false;
            }

            String predicate = preconditions.get(predicateIndex);

            // Προσδιορισμός αριθμού παραμέτρων για το predicate
            int paramCount = getParameterCount(predicate);

            if (paramCount == 0) {
                System.err.println("Unknown predicate in preconditions: " + predicate);
                return false;
            }

            int endIndex = predicateIndex + paramCount + 1;

            if (endIndex > preconditions.size()) {
                System.err.println("Error occurred while checking the preconditions");
                return false;
            }

            // Εξαγωγή του  precondition
            List<String> singlePrecondition = preconditions.subList(i, endIndex);

            // Έλεγχος του precondition - αν αποτύχει, επιστροφή false
            if (!checkSinglePrecondition(singlePrecondition, currentState, connections)) {
                return false;
            }

            i = endIndex; // Μετάβαση στο επόμενο precondition
        }

        return true; // Όλα τα precondition ικανοποιήθηκαν
    }

    /* Βοηθητική μέθοδος για την αντιστοίχηση ονόματος μεταβλητής (?x_0)
    σε αντικείμενο (shot2) μέσω του connections Map */
    private Anything getObject(String name, Map<String, String> connections){
        String objectName = name;

        if (name.startsWith("?")) {
            //Αναζήτηση στο connections Map
            objectName = connections.get(name);
            if (objectName == null) {
                System.err.println("Error with variable name " + name);
                return null;
            }
        }
        //Άμεση αναζήτηση
        return objectsMap.get(objectName);
    }

    // Βοηθητική μέθοδος για τον έλεγχο ενός μόνο precondition
    private boolean checkSinglePrecondition(List<String> precondition, WorldState currentState, Map<String, String> connections) {
        String[] preconditionArray = precondition.toArray(new String[0]);

        boolean isNegative = false; //Ελέγχω αν το precondition είναι αρνητικό πχ (not (contains ?x_0 ?x_1))
        String predicate;
        String[] parts;
        boolean result = false;

        //Με βάση την αρνητικότητα κάνω ανάλυση του precondition
        if(preconditionArray[0].equals("not")){
            isNegative = true;
            predicate = preconditionArray[1];
            parts = Arrays.copyOfRange(preconditionArray, 2, preconditionArray.length);
        }else{
            predicate = preconditionArray[0];
            parts = Arrays.copyOfRange(preconditionArray, 1, preconditionArray.length);
        }

        //Έλεγχος βάσει του τύπου predicate
        switch(predicate){
            //Predicates με ένα αντικείμενο
            case "clean":
                result = currentState.clean.contains((Container) getObject(parts[0], connections));
                break;
            case "empty":
                result = currentState.empty.contains((Container) getObject(parts[0], connections));
                break;
            case "handEmpty":
                result = currentState.handEmpty.contains((Hand) getObject(parts[0], connections));
                break;
            case "ontable":
                result = currentState.onTable.contains((Container) getObject(parts[0], connections));
                break;
            case "ingredient":
                result = currentState.ingredient.contains((Beverage) getObject(parts[0], connections));
                break;
            case "shaked":
                result = currentState.shaked.contains((Shaker) getObject(parts[0], connections));
                break;
            case "unshaked":
                result = currentState.unshaked.contains((Shaker) getObject(parts[0], connections));
                break;

            //Predicates με σχέσεις δύο αντικειμένων
            case "cocktailPart1":
                Cocktail cocktail1 = (Cocktail) getObject(parts[0], connections);
                Ingredient ingredient1 = currentState.cocktailPart1.get(cocktail1);
                Ingredient ingredient1ToCompare = (Ingredient) getObject(parts[1], connections);
                result = (ingredient1 != null && ingredient1.equals(ingredient1ToCompare));
                break;
            case "cocktailPart2":
                Cocktail cocktail2 = (Cocktail) getObject(parts[0], connections);
                Ingredient ingredient2 = currentState.cocktailPart2.get(cocktail2);
                Ingredient ingredient2ToCompare = (Ingredient) getObject(parts[1], connections);
                result = (ingredient2 != null && ingredient2.equals(ingredient2ToCompare));
                break;
            case "contains":
                Container container = (Container) getObject(parts[0], connections);
                Beverage content = currentState.contains.get(container);
                Beverage beverageToCompare = (Beverage) getObject(parts[1], connections);
                result = (content != null && content.equals(beverageToCompare));
                break;
            case "dispenses":
                Dispenser dispenser = (Dispenser) getObject(parts[0], connections);
                Ingredient dispensedIngredient = currentState.dispenses.get(dispenser);
                Ingredient IngredientToCompare = (Ingredient) getObject(parts[1], connections);
                result = (dispensedIngredient != null && dispensedIngredient.equals(IngredientToCompare));
                break;
            case "holding":
                Hand hand = (Hand) getObject(parts[0], connections);
                Container heldItem = currentState.holding.get(hand);
                Container containerToCompare = (Container) getObject(parts[1], connections);
                result = (heldItem != null && heldItem.equals(containerToCompare));
                break;
            case "shakerLevel":
                Shaker shaker = (Shaker) getObject(parts[0], connections);
                Level level = currentState.shakerLevel.get(shaker);
                Level levelToCompare = (Level) getObject(parts[1], connections);
                result = (level != null && level.equals(levelToCompare));
                break;
            case "next":
                Level l1 = (Level) getObject(parts[0], connections);
                Level l2 = currentState.next.get(l1);
                Level l2ToCompare = (Level) getObject(parts[1], connections);
                result = (l2 != null && l2.equals(l2ToCompare));
                break;
            case "shakerEmptyLevel":
                Shaker shakerEmpty = (Shaker) getObject(parts[0], connections);
                Level emptyLevel = currentState.shakerEmptyLevel.get(shakerEmpty);
                Level emptyLevelToCompare = (Level) getObject(parts[1], connections);
                result = (emptyLevel != null && emptyLevel.equals(emptyLevelToCompare));
                break;
            case "used":
                Container usedContainer = (Container) getObject(parts[0], connections);
                Beverage usedBeverage = currentState.used.get(usedContainer);
                Beverage usedBeverageToCompare = (Beverage) getObject(parts[1], connections);
                result = (usedBeverage != null && usedBeverage.equals(usedBeverageToCompare));
                break;
            default:
                System.err.println("Error: Unknown predicate " + predicate);
                result = false;
                break;
        }

        //Εφαρμογή αρνητικότητας, εφόσον περιέχεται το not
        return result != isNegative;
    }

    // Βοηθητική μέθοδος για τον προσδιορισμό αριθμού παραμέτρων
    private int getParameterCount(String predicate) {
        //Predicates με μια παρ'αμετρο
        if (predicate.equals("clean") || predicate.equals("empty") || predicate.equals("handEmpty") ||
                predicate.equals("ontable") || predicate.equals("ingredient") || predicate.equals("shaked") ||
                predicate.equals("unshaked")) {
            return 1;
        }
        //Predicates με δύο παραμέτρους
        else if (predicate.equals("contains") || predicate.equals("dispenses") || predicate.equals("holding") ||
                predicate.equals("used") || predicate.equals("shakerLevel") || predicate.equals("next") ||
                predicate.equals("shakerEmptyLevel") || predicate.equals("cocktailPart1") ||
                predicate.equals("cocktailPart2")) {
            return 2;
        }
        //Default
        else {
            return 0;
        }
    }
}
