package barman;

import barman.Types.*;
import java.util.*;

/**
 * This class applies the effects of an action to the world state.
 * Effects can either add or remove facts from the state.
 */
public class Effects {

    private List<String> effects;
    private Map<String, Anything> objectsMap;

    public Effects(List<String> effects, Map<String, Anything> objectsMap) {
        this.effects = effects;
        this.objectsMap = objectsMap;
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
        }

        if (objectName == null) {
            System.err.println("Could not find object: " + name);
            return null;
        }

        return objectsMap.get(objectName);
    }

    /**
     * Returns how many parameters a predicate has.
     */
    private int getParameterCount(String predicate) {
        // Predicates with 1 parameter
        if (predicate.equals("clean") || predicate.equals("empty") ||
                predicate.equals("handEmpty") || predicate.equals("ontable") ||
                predicate.equals("shaked") || predicate.equals("unshaked")) {
            return 1;
        }

        // Predicates with 2 parameters
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
    public void applyEffects(WorldState currentState, List<String> effects, Map<String, String> connections) {
        if (effects == null || effects.isEmpty()) {
            return;
        }

        int i = 0;
        while (i < effects.size()) {
            String token = effects.get(i);

            // Check for negativity ("not")
            boolean isNegative = token.equals("not");

            // Find where the predicate is based on the negative flag
            int predicateIndex;
            if (isNegative) {
                predicateIndex = i + 1;
            } else {
                predicateIndex = i;
            }

            if (predicateIndex >= effects.size()) {
                System.err.println("Error occurred, while parsing effects");
                return;
            }

            String predicate = effects.get(predicateIndex);
            int paramCount = getParameterCount(predicate);
            int endIndex = predicateIndex + paramCount + 1;

            if (endIndex > effects.size()) {
                System.err.println("Not enough parameters for predicate: " + predicate);
                return;
            }

            // Get the effect tokens and apply them
            List<String> singleEffect = effects.subList(i, endIndex);
            applyOneEffect(currentState, singleEffect, connections);

            i = endIndex;
        }
    }

    /**
     * Applies one effect to the state.
     * Each effect either adds or removes a fact.
     */
    private void applyOneEffect(WorldState state, List<String> effect, Map<String, String> connections) {
        // Convert to array for easier access
        String[] tokens = effect.toArray(new String[0]);

        // Check if negative (delete) or positive (add)
        boolean isNegative = tokens[0].equals("not");

        String predicate;
        String[] params;

        if (isNegative) {
            predicate = tokens[1];
            params = Arrays.copyOfRange(tokens, 2, tokens.length);
        } else {
            predicate = tokens[0];
            params = Arrays.copyOfRange(tokens, 1, tokens.length);
        }

        // Apply the effect based on the predicate type
        if (predicate.equals("clean")) {
            Container container = (Container) getObject(params[0], connections);
            if (container != null) {
                if (isNegative) {
                    state.clean.remove(container);
                } else {
                    state.clean.add(container);
                }
            }
        } else if (predicate.equals("empty")) {
            Container container = (Container) getObject(params[0], connections);
            if (container != null) {
                if (isNegative) {
                    state.empty.remove(container);
                } else {
                    state.empty.add(container);
                }
            }
        } else if (predicate.equals("handEmpty")) {
            Hand hand = (Hand) getObject(params[0], connections);
            if (hand != null) {
                if (isNegative) {
                    state.handEmpty.remove(hand);
                } else {
                    state.handEmpty.add(hand);
                }
            }
        } else if (predicate.equals("ontable")) {
            Container container = (Container) getObject(params[0], connections);
            if (container != null) {
                if (isNegative) {
                    state.onTable.remove(container);
                } else {
                    state.onTable.add(container);
                }
            }
        } else if (predicate.equals("shaked")) {
            Shaker shaker = (Shaker) getObject(params[0], connections);
            if (shaker != null) {
                if (isNegative) {
                    state.shaked.remove(shaker);
                } else {
                    state.shaked.add(shaker);
                }
            }
        } else if (predicate.equals("unshaked")) {
            Shaker shaker = (Shaker) getObject(params[0], connections);
            if (shaker != null) {
                if (isNegative) {
                    state.unshaked.remove(shaker);
                } else {
                    state.unshaked.add(shaker);
                }
            }
        } else if (predicate.equals("cocktailPart1")) {
            Cocktail cocktail = (Cocktail) getObject(params[0], connections);
            Ingredient ingredient = (Ingredient) getObject(params[1], connections);
            if (cocktail != null && ingredient != null) {
                if (isNegative) {
                    state.cocktailPart1.remove(cocktail);
                } else {
                    state.cocktailPart1.put(cocktail, ingredient);
                }
            }
        } else if (predicate.equals("cocktailPart2")) {
            Cocktail cocktail = (Cocktail) getObject(params[0], connections);
            Ingredient ingredient = (Ingredient) getObject(params[1], connections);
            if (cocktail != null && ingredient != null) {
                if (isNegative) {
                    state.cocktailPart2.remove(cocktail);
                } else {
                    state.cocktailPart2.put(cocktail, ingredient);
                }
            }
        } else if (predicate.equals("contains")) {
            Container container = (Container) getObject(params[0], connections);
            Beverage beverage = (Beverage) getObject(params[1], connections);
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
        } else if (predicate.equals("dispenses")) {
            Dispenser dispenser = (Dispenser) getObject(params[0], connections);
            Ingredient ingredient = (Ingredient) getObject(params[1], connections);
            if (dispenser != null && ingredient != null) {
                if (isNegative) {
                    state.dispenses.remove(dispenser);
                } else {
                    state.dispenses.put(dispenser, ingredient);
                }
            }
        } else if (predicate.equals("holding")) {
            Hand hand = (Hand) getObject(params[0], connections);
            Container container = (Container) getObject(params[1], connections);
            if (hand != null && container != null) {
                if (isNegative) {
                    state.holding.remove(hand);
                } else {
                    state.holding.put(hand, container);
                }
            }
        } else if (predicate.equals("used")) {
            Container container = (Container) getObject(params[0], connections);
            Beverage beverage = (Beverage) getObject(params[1], connections);
            if (container != null && beverage != null) {
                if (isNegative) {
                    state.used.remove(container);
                } else {
                    state.used.put(container, beverage);
                }
            }
        } else if (predicate.equals("shakerLevel")) {
            Shaker shaker = (Shaker) getObject(params[0], connections);
            Level level = (Level) getObject(params[1], connections);
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
        } else {
            System.err.println("Unknown predicate: " + predicate);
        }
    }
}