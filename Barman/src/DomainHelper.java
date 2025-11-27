package barman;
import barman.Types.*;
import barman.ProblemParser.*;
import java.util.ArrayList;
import java.util.*;
import barman.WorldState.*;

// Η κλάση αυτή, είναι υπεύθυνη να κρατάει όλα τα αντικείμενα του προβλήματος και παρέχει πρόσβαση σε αυτά

public class DomainHelper {

    //private static για να εξασφαλίσω μια μοναδική ύπαρξη DomainHelper
    private static DomainHelper instance;
    private WorldState currentState;
    private Map<String, Anything> objectsMap;

    // Αρχικοποίηση DomainHelper με όλα τα δεδομένα του προβλήματος
     DomainHelper(Map<String, Anything> objectsMap, WorldState currentState) {
        if (instance != null) {
            // Θα δημιουργηθεί μόνο μία φορά
            throw new IllegalStateException("DomainHelper is already initialized");
        }
        instance = this;
        this.objectsMap = objectsMap;
        this.currentState = currentState;
    }

    // Δημόσιος Getter για πρόσβαση σε κάθε κλάση
    public static DomainHelper getHelper() {
        return instance;
    }

    // Εύρεση object βάσει name και type, πχ getObject("shaker1", Shaker)
    public <Something extends Anything> Something getObject(String name, Class<Something> type) {
        Anything obj = objectsMap.get(name);
        if (obj == null) {
            throw new IllegalArgumentException("Object " + name + " not found.");
        } else if(!type.isInstance(obj)){
            throw new IllegalArgumentException("Wrong type given for the object: " + name);
        }else
            return type.cast(obj);
    }

    /*Επιστρέφει το πρώτο ελεύθερο αντικείμενο του τύπου που δίνεται, δημιουργήθηκε για
    την αποφυγή hardcode ονομάτων στα Tasks, πχ getDefaultObject(Hand.class) - left ή right */
    public <Something extends Anything> Something getDefaultObject(Class<Something> type) {
        for (Anything obj : objectsMap.values()) {
            if (type.isInstance(obj)) {
                return (Something) obj;
            }
        }
        throw new IllegalArgumentException("No object found");
    }

    //Επιστρέφει όλα τα αντικείμενα του τύπου που δίνεται, πχ getAllObjects(Shot,class) - shot1, shot2, ...
    public <Something extends Anything> List<Something> getAllObjects(Class<Something> type) {
        List<Something> result = new ArrayList<>();
        for (Anything obj : objectsMap.values()) {
            if (type.isInstance(obj)) {
                result.add(type.cast(obj));
            }
        }
        return result;
    }

    //Βοηθητική μέθοδος για την αντιμετώπιση ενός error στην υλοποίηση
    public Ingredient getCocktailIngredient(Cocktail cocktail, boolean isPart1) {
        if (isPart1){
            return currentState.cocktailPart1.get(cocktail);
        } else{
            return currentState.cocktailPart2.get(cocktail);
        }
    }

    public Hand getRandomEmptyHand() {
        List<Hand> allHands = getAllObjects(Hand.class);

        Collections.shuffle(allHands);


        for (Hand hand : allHands) {
            if (currentState.handEmpty.contains(hand)) {
                return hand; // Found a random, available hand
            }
        }

        throw new IllegalStateException("No empty hands are currently available!");
    }

}
