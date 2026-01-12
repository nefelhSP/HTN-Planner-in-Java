package barman;

import barman.Types.*;
import java.util.*;

/**
 * The DomainHelper class is responsible for holding all the objects of the
 * problem and providing access to them.
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
            throw new IllegalStateException("Helper already initialized");
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
     * Sets the current state of the world.
     * 
     * @param state The new state of the world
     */
    public void setCurrentState(WorldState state) {
        this.currentState = state;
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
     * Retrieves a hand by name.
     * 
     * @param name The name of the hand
     * @return The hand with the given name
     * @throws IllegalArgumentException if the hand is not found
     */
    public Hand getHand(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Hand) {
            return (Hand) obj;
        }
        throw new IllegalArgumentException("Object " + name + " is not a Hand (or not found).");
    }

    /**
     * Retrieves a shaker by name.
     * 
     * @param name The name of the shaker
     * @return The shaker with the given name
     * @throws IllegalArgumentException if the shaker is not found
     */
    public Shaker getShaker(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Shaker) {
            return (Shaker) obj;
        }
        throw new IllegalArgumentException("Object " + name + " is not a Shaker (or not found).");
    }

    /**
     * Retrieves a shot by name.
     * 
     * @param name The name of the shot
     * @return The shot with the given name
     * @throws IllegalArgumentException if the shot is not found
     */
    public Shot getShot(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Shot) {
            return (Shot) obj;
        }
        throw new IllegalArgumentException("Object " + name + " is not a Shot (or not found).");
    }

    /**
     * Retrieves a cocktail by name.
     * 
     * @param name The name of the cocktail
     * @return The cocktail with the given name
     * @throws IllegalArgumentException if the cocktail is not found
     */
    public Cocktail getCocktail(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Cocktail) {
            return (Cocktail) obj;
        }
        throw new IllegalArgumentException("Object " + name + " is not a Cocktail (or not found).");
    }

    /**
     * Retrieves an ingredient by name.
     * 
     * @param name The name of the ingredient
     * @return The ingredient with the given name
     * @throws IllegalArgumentException if the ingredient is not found
     */
    public Ingredient getIngredient(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Ingredient) {
            return (Ingredient) obj;
        }
        throw new IllegalArgumentException("Object " + name + " is not an Ingredient (or not found).");
    }

    /**
     * Retrieves a dispenser by name.
     * 
     * @param name The name of the dispenser
     * @return The dispenser with the given name
     * @throws IllegalArgumentException if the dispenser is not found
     */
    public Dispenser getDispenser(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Dispenser) {
            return (Dispenser) obj;
        }
        throw new IllegalArgumentException("Object " + name + " is not a Dispenser (or not found).");
    }

    /**
     * Retrieves a level by name.
     * 
     * @param name The name of the level
     * @return The level with the given name
     * @throws IllegalArgumentException if the level is not found
     */
    public Level getLevel(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Level) {
            return (Level) obj;
        }
        throw new IllegalArgumentException("Object " + name + " is not a Level (or not found).");
    }

    /**
     * Retrieves a beverage by name.
     * 
     * @param name The name of the beverage
     * @return The beverage with the given name
     * @throws IllegalArgumentException if the beverage is not found
     */
    public Beverage getBeverage(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Beverage) {
            return (Beverage) obj;
        }
        throw new IllegalArgumentException("Object " + name + " is not a Beverage (or not found).");
    }

    /**
     * Retrieves a container by name.
     * 
     * @param name The name of the container
     * @return The container with the given name
     * @throws IllegalArgumentException if the container is not found
     */
    public Container getContainer(String name) {
        Anything obj = objectsMap.get(name);
        if (obj instanceof Container) {
            return (Container) obj;
        }
        throw new IllegalArgumentException("Object " + name + " is not a Container (or not found).");
    }

    /**
     * Retrieves all hands.
     * 
     * @return A list of all hands
     */
    public List<Hand> getAllHands() {
        List<Hand> result = new ArrayList<>();
        for (Anything obj : objectsMap.values()) {
            if (obj instanceof Hand) {
                result.add((Hand) obj);
            }
        }
        return result;
    }

    /**
     * Retrieves all beverages.
     * 
     * @return A list of all beverages
     */
    public List<Beverage> getAllBeverages() {
        List<Beverage> result = new ArrayList<>();
        for (Anything obj : objectsMap.values()) {
            if (obj instanceof Beverage) {
                result.add((Beverage) obj);
            }
        }
        return result;
    }

    /**
     * Retrieves all levels.
     * 
     * @return A list of all levels
     */
    public List<Level> getAllLevels() {
        List<Level> result = new ArrayList<>();
        for (Anything obj : objectsMap.values()) {
            if (obj instanceof Level) {
                result.add((Level) obj);
            }
        }
        return result;
    }

    /**
     * Retrieves all shots.
     * 
     * @return A list of all shots
     */
    public List<Shot> getAllShots() {
        List<Shot> result = new ArrayList<>();
        for (Anything obj : objectsMap.values()) {
            if (obj instanceof Shot) {
                result.add((Shot) obj);
            }
        }
        return result;
    }

    /**
     * Retrieves all dispensers.
     * 
     * @return A list of all dispensers
     */
    public List<Dispenser> getAllDispensers() {
        List<Dispenser> result = new ArrayList<>();
        for (Anything obj : objectsMap.values()) {
            if (obj instanceof Dispenser) {
                result.add((Dispenser) obj);
            }
        }
        return result;
    }

    /**
     * Retrieves all shakers.
     * 
     * @return A list of all shakers
     */
    public List<Shaker> getAllShakers() {
        List<Shaker> result = new ArrayList<>();
        for (Anything obj : objectsMap.values()) {
            if (obj instanceof Shaker) {
                result.add((Shaker) obj);
            }
        }
        return result;
    }

    /**
     * Retrieves all containers.
     * 
     * @return A list of all containers
     */
    public List<Container> getAllContainers() {
        List<Container> result = new ArrayList<>();
        for (Anything obj : objectsMap.values()) {
            if (obj instanceof Container) {
                result.add((Container) obj);
            }
        }
        return result;
    }

    /**
     * Retrieves all cocktails.
     * 
     * @return A list of all cocktails
     */
    public List<Cocktail> getAllCocktails() {
        List<Cocktail> result = new ArrayList<>();
        for (Anything obj : objectsMap.values()) {
            if (obj instanceof Cocktail) {
                result.add((Cocktail) obj);
            }
        }
        return result;
    }

    /**
     * Retrieves all ingredients.
     * 
     * @return A list of all ingredients
     */
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> result = new ArrayList<>();
        for (Anything obj : objectsMap.values()) {
            if (obj instanceof Ingredient) {
                result.add((Ingredient) obj);
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