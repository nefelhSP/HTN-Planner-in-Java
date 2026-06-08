package barman;

import barman.Types.*;
import java.util.*;

/**
 * This class applies the effects of an action to the world state.
 * Effects can either add or remove facts from the state.
 */
public class Effects {

    DomainHelper helper = DomainHelper.getHelper();

    public Effects() {
    }

    /**
     * Returns how many parameters a predicate has.
     */
    private int getParameterCount(String predicate) {
        // Predicates with 1 parameter
        // For predicates with one parameter, there are going to be used Sets
        if (predicate.equals("clean") || predicate.equals("empty") ||
                predicate.equals("handEmpty") || predicate.equals("ontable") ||
                predicate.equals("shaked") || predicate.equals("unshaked")) {
            return 1;
        }

        // Predicates with 2 parameters
        // For predicates with two parameters, there are going to be used Maps
        if (predicate.equals("contains") || predicate.equals("dispenses") ||
                predicate.equals("holding") || predicate.equals("used") ||
                predicate.equals("shakerLevel") || predicate.equals("next") ||
                predicate.equals("shakerEmptyLevel") ||
                predicate.equals("cocktailPart1") || predicate.equals("cocktailPart2")) {
            return 2;
        }
        return 0;
    }

    /**
     * Applies all effects to the current state.
     */
    public void applyEffects(WorldState currentState, List<String> effects) {
        if (effects == null || effects.isEmpty()) {
            return;
        }

        int i = 0;
        while (i < effects.size()) {
            String token = effects.get(i);

            // Check for predicate's negativity
            boolean isNegative = token.equals("not");

            // Find where the predicate is based on the negative flag
            int predicateIndex;
            if (isNegative) {
                predicateIndex = i + 1;
            } else {
                predicateIndex = i;
            }

            // Check for index error
            if (predicateIndex >= effects.size()) {
                System.err.println("An error has occurred, while parsing effects");
                return;
            }

            String predicate = effects.get(predicateIndex);
            int paramCount = getParameterCount(predicate);
            int endIndex = predicateIndex + paramCount + 1;

            // Check for index error
            if (endIndex > effects.size()) {
                System.err.println("Not enough parameters for predicate: " + predicate);
                return;
            }

            // Get the effect tokens and apply them
            List<String> effect = effects.subList(i, endIndex);
            effectApplier(currentState, effect);

            i = endIndex;
        }
    }

    /**
     * Applies an effect to the current state.
     * Each effect either adds or removes a fact.
     */
    private void effectApplier(WorldState state, List<String> effect) {

        // Check if negative (delete) or positive (add)
        boolean isNegative = effect.get(0).equals("not");

        int start = 0;
        if (isNegative) {
            start = 1;
        }

        String predicate = effect.get(start);
        List<String> params = effect.subList(start + 1, effect.size());

        // Apply the effect based on the predicate type
        switch (predicate) {
            case ("clean"):
                updateSet(params.get(0), Container.class, isNegative, state.clean);
                break;

            case ("empty"):
                updateSet(params.get(0), Container.class, isNegative, state.empty);
                break;

            case ("handEmpty"):
                updateSet(params.get(0), Hand.class, isNegative, state.handEmpty);
                break;

            case ("ontable"):
                updateSet(params.get(0), Container.class, isNegative, state.onTable);
                break;

            case ("shaked"):
                updateSet(params.get(0), Shaker.class, isNegative, state.shaked);
                break;

            case ("unshaked"):
                updateSet(params.get(0), Shaker.class, isNegative, state.unshaked);
                break;

            case ("cocktailPart1"):
                updateMap(params.get(0), params.get(1), Cocktail.class, Ingredient.class, isNegative,
                        state.cocktailPart1);
                break;

            case ("cocktailPart2"):
                updateMap(params.get(0), params.get(1), Cocktail.class, Ingredient.class, isNegative,
                        state.cocktailPart2);
                break;

            case ("dispenses"):
                updateMap(params.get(0), params.get(1), Dispenser.class, Ingredient.class, isNegative, state.dispenses);
                break;

            case ("holding"):
                updateMap(params.get(0), params.get(1), Hand.class, Container.class, isNegative, state.holding);
                break;

            case ("used"):
                updateMap(params.get(0), params.get(1), Container.class, Beverage.class, isNegative, state.used);
                break;

            case ("contains"): {
                Container container = helper.getItem(params.get(0), Container.class);
                Beverage beverage = helper.getItem(params.get(1), Beverage.class);
                if (container != null && beverage != null) {
                    if (isNegative) {
                        Set<Beverage> contents = state.contains.get(container);
                        if (contents != null) {
                            contents.remove(beverage);
                        }
                    } else {
                        // If there's no set for this container yet, create one
                        if (!state.contains.containsKey(container)) {
                            state.contains.put(container, new HashSet<>());
                        }
                        state.contains.get(container).add(beverage);
                    }
                }
                break;
            }

            case ("shakerLevel"): {
                Shaker shaker = helper.getItem(params.get(0), Shaker.class);
                Level level = helper.getItem(params.get(1), Level.class);
                if (shaker != null && level != null) {
                    if (isNegative) {
                        // Only remove if it matches the current level
                        Level currentLevel = state.shakerLevel.get(shaker);
                        if (currentLevel != null && currentLevel.equals(level)) {
                            state.shakerLevel.remove(shaker);
                        }
                    } else {
                        state.shakerLevel.put(shaker, level);
                    }
                }
                break;
            }

            default:
                System.err.println("Unknown predicate: " + predicate);
        }
    }

    /**
     * Updates a set by adding or removing an item.
     */
    private <Item extends Anything> void updateSet(String name, Class<Item> type, boolean isNegative, Set<Item> set) {
        Item item = helper.getItem(name, type);
        if (item != null) {
            if (isNegative) {
                set.remove(item);
            } else {
                set.add(item);
            }
        }
    }

    /**
     * Updates a map by adding or removing a key-value pair.
     */
    private <Key extends Anything, Value extends Anything> void updateMap(String keyName, String valueName,
            Class<Key> keyType, Class<Value> valueType, boolean isNegative, Map<Key, Value> map) {
        Key key = helper.getItem(keyName, keyType);
        Value value = helper.getItem(valueName, valueType);

        if (key != null && value != null) {
            if (isNegative) {
                map.remove(key, value);
            } else {
                map.put(key, value);
            }
        }
    }
}