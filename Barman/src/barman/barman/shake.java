package barman;

import barman.Types.*;
import java.util.*;

/**
 * The shake class extends the Action class and represents the shake action.
 * It is used to represent the action of shaking a shaker with two ingredients.
 */
public class shake extends Action {

    private final Cocktail cocktail;
    private final Ingredient ingredientA;
    private final Ingredient ingredientB;
    private final Shaker shaker;
    private final Hand handA;
    private final Hand handB;

    /**
     * Initializes the shake action with the given parameters.
     * 
     * @param cocktail    The cocktail to be shaken
     * @param ingredientA The first ingredient
     * @param ingredientB The second ingredient
     * @param shaker      The shaker
     * @param handA       The first hand
     * @param handB       The second hand
     */
    public shake(Cocktail cocktail, Ingredient ingredientA, Ingredient ingredientB, Shaker shaker, Hand handA,
            Hand handB) {

        // List that contains the parameters of the action
        List<String> parameters = List.of(cocktail.getName(), ingredientA.getName(), ingredientB.getName(),
                shaker.getName(), handA.getName(), handB.getName());

        // List that contains the preconditions of the action
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("handEmpty", handB.getName()));
        preconditions.add(List.of("holding", handA.getName(), shaker.getName()));
        preconditions.add(List.of("contains", shaker.getName(), ingredientA.getName()));
        preconditions.add(List.of("contains", shaker.getName(), ingredientB.getName()));
        preconditions.add(List.of("unshaked", shaker.getName()));
        preconditions.add(List.of("cocktailPart1", cocktail.getName(), ingredientA.getName()));
        preconditions.add(List.of("cocktailPart2", cocktail.getName(), ingredientB.getName()));

        // List that contains the effects of the action
        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("shaked", shaker.getName()));
        effects.addAll(List.of("contains", shaker.getName(), cocktail.getName()));
        effects.addAll(List.of("not", "unshaked", shaker.getName()));
        effects.addAll(List.of("not", "contains", shaker.getName(), ingredientA.getName()));
        effects.addAll(List.of("not", "contains", shaker.getName(), ingredientB.getName()));

        super("shake", parameters, preconditions, effects);
        this.cocktail = cocktail;
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.shaker = shaker;
        this.handA = handA;
        this.handB = handB;
    }
}