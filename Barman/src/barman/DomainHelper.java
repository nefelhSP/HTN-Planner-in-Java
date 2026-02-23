package barman;

import barman.Types.*;
import java.util.*;

/**
 * The DomainHelper class is responsible for holding all the objects of the
 * problem and also providing access to them.
 */

public class DomainHelper {

    // private static to preserve only one instance of DomainHelper
    private static DomainHelper instance;
    private WorldState currentState;
    private Map<String, Anything> objectsMap;

    /**
     * Initializes the DomainHelper with the given objects and current state.
     * 
     * @param objectsMap   The map of the objects in the problem
     * @param currentState The current state of the world
     */
    DomainHelper(Map<String, Anything> objectsMap, WorldState currentState) {
        if (instance != null)
            throw new IllegalStateException("Helper has already been initialized!");
        instance = this;
        this.objectsMap = objectsMap;
        this.currentState = currentState;
    }

    /**
     * Retrieves the DomainHelper instance.
     * 
     * @return The DomainHelper instance
     */
    public static DomainHelper getHelper() {
        return instance;
    }

    /**
     * Retrieves the current state of the world.
     * 
     * @return The current state of the world
     */
    public WorldState getCurrentState() {
        return currentState;
    }

    /**
     * Retrieves an item of a specific type by name
     * 
     * @param type The type of the item
     * @param name The requested item's name
     * @return The item with the given name
     * @throws IllegalArgumentException if the item isn't found
     */
    public <Item extends Anything> Item getItem(String name, Class<Item> type) {
        Anything item = objectsMap.get(name);
        if (type.isInstance(item)) {
            return type.cast(item);
        }
        throw new IllegalArgumentException("Object " + name + " isn't a " + type + ", (or not found).");
    }

    /**
     * Retrieves all hands.
     * Sorted to give back the same sequence everytime (left>right)
     * 
     * @return A list of all hands
     */
    public List<Hand> getAllHands() {
        List<Hand> result = new ArrayList<>();
        for (Anything obj : objectsMap.values()) {
            if (obj instanceof Hand) {
                Hand hand = ((Hand) obj);
                if (hand.getName().equals("left")) {
                    result.addFirst(hand);
                } else {
                    result.add(hand);
                }
            }
        }
        return result;
    }

    /**
     * Retrieves all items of a specific type
     * 
     * @param type The asked type
     * @return All items of the asked type
     */
    public <Item extends Anything> List<Item> getAllItems(Class<Item> type) {
        List<Item> result = new ArrayList<>();
        for (Anything item : objectsMap.values()) {
            if (type.isInstance(item)) {
                result.add(type.cast(item));
            }
        }
        return result;
    }

    /**
     * Retrieves the ingredient for a specific cocktail and part.
     * 
     * @param cocktail The cocktail to retrieve the ingredient for
     * @param isPart1  Whether to retrieve the ingredient for part 1 or part 2
     * @return The ingredient for the specified cocktail and part
     */
    public Ingredient getCocktailIngredient(Cocktail cocktail, boolean isPart1) {
        if (isPart1)
            return currentState.cocktailPart1.get(cocktail);
        else
            return currentState.cocktailPart2.get(cocktail);
    }
}