package barman;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import barman.Types.*;

/* Περιέχει όλά τα facts τα οποία ισχύουν τη συγκεκριμένη χρονική στιγμή, άρα η κλάση
WorldState είναι κατάλληλη για τον έλεγχο των preconditions και την αναπαράσταση του κόσμου.
Κάθε κόμβος στο δέντρο αναζήτησης θα περιέχει ένα WorldState αντικείμενο */

public class WorldState{

    /* Έχω Set για facts με μοναδικά αντικείμενα.
    Χρησιμοποιώ Sets, καθώς δε με ενδιαφέρει η σειρά και θέλω να αποφύγω τα διπλότυπα */
    public Set<Container> clean = new HashSet<>();
    public Set<Container> empty = new HashSet<>();
    public Set<Container> onTable = new HashSet<>();
    public Set<Beverage> ingredient = new HashSet<>();
    public Set<Shaker> shaked = new HashSet<>();
    public Set<Shaker> unshaked = new HashSet<>();
    public Set<Hand> handEmpty = new HashSet<>();

    /* Έχω Maps για σχέσεις μεταξύ αντικειμένων.
    Χρησιμοποιώ Map όταν το πρώτο αντικείμενο είναι μοναδικό κλειδί  */
    public Map<Hand, Container> holding = new HashMap<>();
    public Map<Container, Beverage> contains = new HashMap<>();
    public Map<Container, Beverage> used = new HashMap<>();
    public Map<Shaker, Level> shakerLevel = new HashMap<>();
    public Map<Shaker, Level> shakerEmptyLevel = new HashMap<>();
    public Map<Cocktail, Ingredient> cocktailPart1 = new HashMap<>();
    public Map<Cocktail, Ingredient> cocktailPart2 = new HashMap<>();
    public Map<Dispenser, Ingredient> dispenses = new HashMap<>();
    public Map<Level, Level> next = new HashMap<>();


    public WorldState(){

        //Αρχικοποιήσεις
        this.clean = new HashSet<>();
        this.empty = new HashSet<>();
        this.onTable = new HashSet<>();
        this.ingredient = new HashSet<>();
        this.shaked = new HashSet<>();
        this.unshaked = new HashSet<>();
        this.handEmpty = new HashSet<>();

        this.holding = new HashMap<>();
        this.contains = new HashMap<>();
        this.used = new HashMap<>();
        this.shakerLevel = new HashMap<>();
        this.shakerEmptyLevel = new HashMap<>();
        this.cocktailPart1 = new HashMap<>();
        this.cocktailPart2 = new HashMap<>();
        this.dispenses = new HashMap<>();
        this.next = new HashMap<>();
    }

    /* Αυτή η μέθοδος έχει σκοπό την πλήρη αντιγραφή του currentState έτσι ώστε να λειτουργεί
    σωστά η οπισθοχώρηση των σταδίων σε περίπτωση λάθους */
    public WorldState copyState(){
        WorldState newState = new WorldState();

        newState.clean = new HashSet<>(clean);
        newState.empty = new HashSet<>(empty);
        newState.onTable = new HashSet<>(onTable);
        newState.ingredient = new HashSet<>(ingredient);
        newState.shaked = new HashSet<>(shaked);
        newState.unshaked = new HashSet<>(unshaked);
        newState.handEmpty = new HashSet<>(handEmpty);

        newState.holding = new HashMap<>(holding);
        newState.contains = new HashMap<>(contains);
        newState.used = new HashMap<>(used);
        newState.shakerLevel = new HashMap<>(shakerLevel);
        newState.shakerEmptyLevel = new HashMap<>(shakerEmptyLevel);
        newState.cocktailPart1 = new HashMap<>(cocktailPart1);
        newState.cocktailPart2 = new HashMap<>(cocktailPart2);
        newState.dispenses = new HashMap<>(dispenses);
        newState.next = new HashMap<>(next);

        return newState;
    }

    // Εκτυπώνει την τρέχουσα κατάσταση, είναι προσωρινή μέθοδος για check
    public void printState(){
        System.out.println("        Current State");
        System.out.println("On Table: " + onTable);
        System.out.println("Clean: " + clean);
        System.out.println("Empty: " + empty);
        System.out.println("Shaked: " + shaked);           // Added
        System.out.println("Unshaked: " + unshaked);       // Added
        System.out.println("Hands Empty: " + handEmpty);
        System.out.println("Holding: " + holding);         // Added
        System.out.println("Contains: " + contains);       // Added
        System.out.println("Used: " + used);               // Added
        System.out.println("Dispenses: " + dispenses);
        System.out.println("Shaker Level: " + shakerLevel);
        System.out.println("Shaker Empty Level: " + shakerEmptyLevel);
        System.out.println("Next: " + next);
        System.out.println("Cocktail Part 1: " + cocktailPart1);
        System.out.println("Cocktail Part 2: " + cocktailPart2);
    }
}
