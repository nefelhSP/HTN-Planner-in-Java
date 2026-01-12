package barman;

import java.util.*;
import barman.Types.*;

/**
 * The WorldState class represents the state of the world at a specific time.
 * It contains all the facts that are true at that time, making it suitable for
 * checking preconditions and representing the world.
 * Each node in the search tree will contain a WorldState object.
 */
public class WorldState {

    /**
     * Sets are used to store unique objects
     * Using Sets to avoid duplicates
     */
    public Set<Container> clean = new HashSet<>();
    public Set<Container> empty = new HashSet<>();
    public Set<Container> onTable = new HashSet<>();
    public Set<Beverage> ingredient = new HashSet<>();
    public Set<Shaker> shaked = new HashSet<>();
    public Set<Shaker> unshaked = new HashSet<>();
    public Set<Hand> handEmpty = new HashSet<>();

    /**
     * Maps are used to store key-value pairs
     * Using Maps to use the first object as a unique key
     */
    public Map<Hand, Container> holding = new HashMap<>();
    public Map<Container, Set<Beverage>> contains = new HashMap<>();
    public Map<Container, Beverage> used = new HashMap<>();
    public Map<Shaker, Level> shakerLevel = new HashMap<>();
    public Map<Shaker, Level> shakerEmptyLevel = new HashMap<>();
    public Map<Cocktail, Ingredient> cocktailPart1 = new HashMap<>();
    public Map<Cocktail, Ingredient> cocktailPart2 = new HashMap<>();
    public Map<Dispenser, Ingredient> dispenses = new HashMap<>();
    public Map<Level, Level> next = new HashMap<>();

    public WorldState() {

        // Initializations
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

    /**
     * This method has the purpose of fully copying the currentState so that the
     * backtracking
     * of the steps works correctly in case of an error
     */
    public WorldState copyState() {
        WorldState state = new WorldState();

        state.clean = new HashSet<>(clean);
        state.empty = new HashSet<>(empty);
        state.onTable = new HashSet<>(onTable);
        state.ingredient = new HashSet<>(ingredient);
        state.shaked = new HashSet<>(shaked);
        state.unshaked = new HashSet<>(unshaked);
        state.handEmpty = new HashSet<>(handEmpty);

        state.holding = new HashMap<>(holding);
        for (Map.Entry<Container, Set<Beverage>> entry : contains.entrySet()) {
            state.contains.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }
        state.used = new HashMap<>(used);
        state.shakerLevel = new HashMap<>(shakerLevel);
        state.shakerEmptyLevel = new HashMap<>(shakerEmptyLevel);
        state.cocktailPart1 = new HashMap<>(cocktailPart1);
        state.cocktailPart2 = new HashMap<>(cocktailPart2);
        state.dispenses = new HashMap<>(dispenses);
        state.next = new HashMap<>(next);

        return state;
    }

    // Prints the initial state
    public void printInitState() {
        System.out.println(" --- Initial State --- ");
        System.out.println("On Table: " + onTable);
        System.out.println("Clean: " + clean);
        System.out.println("Empty: " + empty);
        System.out.println("Hands Empty: " + handEmpty);
        System.out.println("Dispenses: " + dispenses);
        System.out.println("Shaker Level: " + shakerLevel);
        System.out.println("Shaker Empty Level: " + shakerEmptyLevel);
        System.out.println("Next: " + next);
        System.out.println("Cocktail Part 1: " + cocktailPart1);
        System.out.println("Cocktail Part 2: " + cocktailPart2);
    }

    // Prints the current state
    public void printState() {
        System.out.println(" --- Current State --- ");
        System.out.println("On Table: " + onTable);
        System.out.println("Clean: " + clean);
        System.out.println("Empty: " + empty);
        System.out.println("Shaked: " + shaked); // Added
        System.out.println("Unshaked: " + unshaked); // Added
        System.out.println("Hands Empty: " + handEmpty);
        System.out.println("Holding: " + holding); // Added
        System.out.println("Contains: " + contains); // Added
        System.out.println("Used: " + used); // Added
        System.out.println("Dispenses: " + dispenses);
        System.out.println("Shaker Level: " + shakerLevel);
        System.out.println("Shaker Empty Level: " + shakerEmptyLevel);
        System.out.println("Next: " + next);
        System.out.println("Cocktail Part 1: " + cocktailPart1);
        System.out.println("Cocktail Part 2: " + cocktailPart2);
    }
}
