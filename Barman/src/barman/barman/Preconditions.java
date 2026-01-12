package barman;

import java.util.*;
import barman.Types.*;

/**
 * This class checks if preconditions are satisfied.
 * It compares the preconditions of an action/method with the current state.
 */
public class Preconditions {

    private Map<String, Anything> objectsMap;

    public Preconditions(Map<String, Anything> objectsMap) {
        if (objectsMap == null) {
            throw new IllegalArgumentException("An error has occurred! The objectsMap cannot be null");
        }
        this.objectsMap = objectsMap;
    }

    /**
     * Checks if an action's preconditions are satisfied.
     */
    public boolean checkPreconditions(Action action, WorldState currentState, Map<String, String> connections) {
        return checkAllPreconditions(action.getPreconditions(), currentState, connections);
    }

    /**
     * Checks if a method's preconditions are satisfied.
     */
    public boolean checkPreconditions(Method method, WorldState currentState, Map<String, String> connections) {
        return checkAllPreconditions(method.getPreconditions(), currentState, connections);
    }

    /**
     * Gets an object from the objectsMap.
     * If the name starts with "?" it means it's a variable, so I look it up in
     * connections.
     */
    private Anything getObject(String name, Map<String, String> connections) {
        String objectName = name;

        // Check if it's a variable
        if (name.startsWith("?")) {
            objectName = connections.get(name);
            if (objectName == null) {
                System.err.println("An error has occurred! Could not find variable: " + name);
                return null;
            }
        }
        return objectsMap.get(objectName);
    }

    /**
     * Checks all preconditions in the list.
     * Returns false if any precondition fails.
     */
    private boolean checkAllPreconditions(List<List<String>> preconditions, WorldState state,
            Map<String, String> connections) {

        if (preconditions == null || preconditions.isEmpty()) {
            return true;
        }

        // Check each precondition
        for (List<String> precondition : preconditions) {
            if (precondition.isEmpty()) {
                continue;
            }

            boolean satisfied = checkPrecondition(precondition, state, connections);
            if (!satisfied) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks one precondition.
     * Returns true if it is satisfied.
     */
    private boolean checkPrecondition(List<String> precondition, WorldState state, Map<String, String> connections) {

        String[] tokens = precondition.toArray(new String[0]);

        if (tokens.length == 0) {
            return true;
        }

        // Check for negativity ("not")
        boolean isNegative = tokens[0].equals("not");

        String predicate;
        String[] params;

        if (isNegative) {
            if (tokens.length < 2) {
                System.err.println("An error has occurred! Cannot parse this 'not' precondition: " + precondition);
                return false;
            }
            predicate = tokens[1];
            params = Arrays.copyOfRange(tokens, 2, tokens.length);
        } else {
            predicate = tokens[0];
            params = Arrays.copyOfRange(tokens, 1, tokens.length);
        }

        // Check the predicate
        boolean result = false;

        // Predicates with 1 parameter
        if (predicate.equals("clean")) {
            Container container = (Container) getObject(params[0], connections);
            result = state.clean.contains(container);

        } else if (predicate.equals("empty")) {
            Container container = (Container) getObject(params[0], connections);
            result = state.empty.contains(container);

        } else if (predicate.equals("handEmpty")) {
            Hand hand = (Hand) getObject(params[0], connections);
            result = state.handEmpty.contains(hand);

        } else if (predicate.equals("ontable")) {
            Container container = (Container) getObject(params[0], connections);
            result = state.onTable.contains(container);

        } else if (predicate.equals("ingredient")) {
            Beverage beverage = (Beverage) getObject(params[0], connections);
            result = state.ingredient.contains(beverage);

        } else if (predicate.equals("shaked")) {
            Shaker shaker = (Shaker) getObject(params[0], connections);
            result = state.shaked.contains(shaker);

        } else if (predicate.equals("unshaked")) {
            Shaker shaker = (Shaker) getObject(params[0], connections);
            result = state.unshaked.contains(shaker);

            // Predicates with 2 parameters
        } else if (predicate.equals("cocktailPart1")) {
            Cocktail cocktail = (Cocktail) getObject(params[0], connections);
            Ingredient expected = (Ingredient) getObject(params[1], connections);
            Ingredient actual = state.cocktailPart1.get(cocktail);
            result = (actual != null && actual.equals(expected));

        } else if (predicate.equals("cocktailPart2")) {
            Cocktail cocktail = (Cocktail) getObject(params[0], connections);
            Ingredient expected = (Ingredient) getObject(params[1], connections);
            Ingredient actual = state.cocktailPart2.get(cocktail);
            result = (actual != null && actual.equals(expected));

        } else if (predicate.equals("contains")) {
            Container container = (Container) getObject(params[0], connections);
            Beverage beverage = (Beverage) getObject(params[1], connections);
            Set<Beverage> contents = state.contains.get(container);
            result = (contents != null && contents.contains(beverage));

        } else if (predicate.equals("dispenses")) {
            Dispenser dispenser = (Dispenser) getObject(params[0], connections);
            Ingredient expected = (Ingredient) getObject(params[1], connections);
            Ingredient actual = state.dispenses.get(dispenser);
            result = (actual != null && actual.equals(expected));

        } else if (predicate.equals("holding")) {
            Hand hand = (Hand) getObject(params[0], connections);
            Container expected = (Container) getObject(params[1], connections);
            Container actual = state.holding.get(hand);
            result = (actual != null && actual.equals(expected));

        } else if (predicate.equals("shakerLevel")) {
            Shaker shaker = (Shaker) getObject(params[0], connections);
            Level expected = (Level) getObject(params[1], connections);
            Level actual = state.shakerLevel.get(shaker);
            result = (actual != null && actual.equals(expected));

        } else if (predicate.equals("next")) {
            Level level1 = (Level) getObject(params[0], connections);
            Level expected = (Level) getObject(params[1], connections);
            Level actual = state.next.get(level1);
            result = (actual != null && actual.equals(expected));

        } else if (predicate.equals("shakerEmptyLevel")) {
            Shaker shaker = (Shaker) getObject(params[0], connections);
            Level expected = (Level) getObject(params[1], connections);
            Level actual = state.shakerEmptyLevel.get(shaker);
            result = (actual != null && actual.equals(expected));

        } else if (predicate.equals("used")) {
            Container container = (Container) getObject(params[0], connections);
            Beverage expected = (Beverage) getObject(params[1], connections);
            Beverage actual = state.used.get(container);
            result = (actual != null && actual.equals(expected));

        } else if (predicate.equals("=")) {
            Anything obj1 = getObject(params[0], connections);
            Anything obj2 = getObject(params[1], connections);
            result = (obj1 != null && obj2 != null && obj1.getName().equals(obj2.getName()));

        } else {
            System.err.println("Unknown predicate: " + predicate);
            result = false;
        }

        // If the precondition was negative, reverse the result
        if (isNegative) {
            return !result;
        } else {
            return result;
        }
    }
}
