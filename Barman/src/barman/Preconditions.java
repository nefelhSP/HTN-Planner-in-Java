package barman;

import barman.Types.*;
import java.util.*;

/**
 * This class checks if preconditions are satisfied.
 * It compares the preconditions of an action/method with the current state.
 */
public class Preconditions {

    DomainHelper helper = DomainHelper.getHelper();

    public Preconditions() {
    }

    /**
     * Checks if a type that implements the hasPreconditions interface (Action and Method)
     * satisfies its preconditions
     */
    public boolean checkPreconditions(hasPreconditions type, WorldState state) {
        List<List<String>> preconditions = type.getPreconditions();

        if (preconditions == null || preconditions.isEmpty()) {
            return true;
        }

        // Check every non-empty precondition
        for (List<String> precondition : preconditions) {
            if (precondition.isEmpty()) {
                continue;
            }
            if (!checkPrecondition(precondition, state)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks a single precondition.
     * @return true if satisfied.
     */
    private boolean checkPrecondition(List<String> precondition, WorldState state) {

        if (precondition == null) {
            return true;
        }

        // Check for negativity ("not")
        boolean isNegative = precondition.get(0).equals("not");

        int start = 0;
        if (isNegative){
            start = 1;
        }

        // Locate predicate based on the precondition's negativity
        String predicate =  precondition.get(start);
        List<String> params = precondition.subList(start + 1, precondition.size());


        // Check the predicate based on its type
        switch (predicate) {
            case ("clean"):
                return checkSet(params.get(0), Container.class, isNegative, state.clean);

            case ("empty"):
                return checkSet(params.get(0), Container.class, isNegative, state.empty);

            case ("handEmpty"):
                return checkSet(params.get(0), Hand.class, isNegative, state.handEmpty);

            case ("ontable"):
                return checkSet(params.get(0), Container.class, isNegative, state.onTable);

            case ("ingredient"):
                return checkSet(params.get(0), Beverage.class, isNegative, state.ingredient);

            case ("shaked"):
                return checkSet(params.get(0), Shaker.class, isNegative, state.shaked);

            case ("unshaked"):
                return checkSet(params.get(0), Shaker.class, isNegative, state.unshaked);

            case ("cocktailPart1"):
                return checkMap(params.get(0), params.get(1), Cocktail.class, Ingredient.class, isNegative, state.cocktailPart1);

            case ("cocktailPart2"):
                return checkMap(params.get(0), params.get(1), Cocktail.class, Ingredient.class, isNegative, state.cocktailPart2);

            case ("dispenses"):
                return checkMap(params.get(0), params.get(1), Dispenser.class, Ingredient.class, isNegative, state.dispenses);

            case ("holding"):
                return checkMap(params.get(0), params.get(1), Hand.class, Container.class, isNegative, state.holding);

            case ("shakerLevel"):
                return checkMap(params.get(0), params.get(1), Shaker.class, Level.class, isNegative, state.shakerLevel);

            case ("next"):
                return checkMap(params.get(0), params.get(1), Level.class, Level.class, isNegative, state.next);

            case ("shakerEmptyLevel"):
                return checkMap(params.get(0), params.get(1), Shaker.class, Level.class, isNegative, state.shakerEmptyLevel);

            case ("used"):
                return checkMap(params.get(0), params.get(1), Container.class, Beverage.class, isNegative, state.used);

            case ("contains"): {
                Container container = helper.getItem(params.get(0), Container.class);
                Beverage beverage = helper.getItem(params.get(1), Beverage.class);
                if (container != null && beverage != null) {
                    Set<Beverage> contents = state.contains.get(container);
                    boolean result = (contents != null && contents.contains(beverage));
                    return isNegative ? !result : result;
                }
                return false;
            }
            case ("="): {
                Anything obj1 = helper.getItem(params.get(0), Anything.class);
                Anything obj2 = helper.getItem(params.get(1), Anything.class);
                boolean result = (obj1 != null && obj2 != null && obj1.getName().equals(obj2.getName()));
                if (isNegative){
                    return !result;
                }else{
                    return result;
                }
            }
            default:
                System.err.println("Unknown predicate: " + predicate);
                return false;
        }
    }

    /**
     * Checks if an item is in a set.
     */
    private <Item extends Anything> boolean checkSet(String name, Class<Item> type, boolean isNegative, Set<Item> set) {
        Item item = helper.getItem(name, type);
        if (item != null) {
            if (isNegative) {
                //For negative conditions, I want the set to not contain this item
                return !set.contains(item);
            } else {
                //For positive conditions, I want the set to contain this item
                return set.contains(item);
            }
        }
        return false;
    }

    /**
     * Checks if a key-value pair exists in a map.
     */
    private <Key extends Anything, Value extends Anything> boolean checkMap(String keyName, String valueName,
            Class<Key> keyType, Class<Value> valueType, boolean isNegative, Map<Key, Value> map) {
        Key key = helper.getItem(keyName, keyType);
        Value expectedValue = helper.getItem(valueName, valueType);

        if (key != null && expectedValue != null) {
            Value trueValue = map.get(key);
            //Check if the current value in the map matches with the expected one
            boolean match = (trueValue != null && trueValue.equals(expectedValue));
            if (isNegative){
                // If the map doesn't have this mapping the precondition is satisfied
               return !match;
            }else{
                // If the map does have this mapping the precondition is satisfied
                return match;
            }
        }
        return false;
    }
}
