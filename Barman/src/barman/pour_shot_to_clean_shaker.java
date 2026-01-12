package barman;

import barman.Types.*;
import java.util.*;

/**
 * The pour_shot_to_clean_shaker class is an action that pours a shot to a clean
 * shaker.
 */
public class pour_shot_to_clean_shaker extends Action {

    private final Shot shot;
    private final Ingredient ingredient;
    private final Shaker shaker;
    private final Hand hand;
    private final Level levelA;
    private final Level levelB;

    /**
     * Initializes the pour_shot_to_clean_shaker action with the given shot,
     * ingredient, shaker, hand, levelA, levelB, and decomposed task.
     * 
     * @param shot       The shot to pour from
     * @param ingredient The ingredient to pour
     * @param shaker     The shaker to pour into
     * @param hand       The hand to use
     * @param levelA     The level of the shaker
     * @param levelB     The level of the shot
     */
    public pour_shot_to_clean_shaker(Shot shot, Ingredient ingredient, Shaker shaker, Hand hand, Level levelA,
            Level levelB) {

        // List that contains the parameters of the action
        List<String> parameters = List.of(shot.getName(), ingredient.getName(), shaker.getName(), hand.getName(),
                levelA.getName(), levelB.getName());

        // List that contains the preconditions of the action
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("contains", shot.getName(), ingredient.getName()));
        preconditions.add(List.of("empty", shaker.getName()));
        preconditions.add(List.of("clean", shaker.getName()));
        preconditions.add(List.of("holding", hand.getName(), shot.getName()));
        preconditions.add(List.of("shakerLevel", shaker.getName(), levelA.getName()));
        preconditions.add(List.of("next", levelA.getName(), levelB.getName()));

        // List that contains the effects of the action
        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("not", "shakerLevel", shaker.getName(), levelA.getName())); // Delete first
        effects.addAll(List.of("contains", shaker.getName(), ingredient.getName()));
        effects.addAll(List.of("shakerLevel", shaker.getName(), levelB.getName())); // Add second
        effects.addAll(List.of("unshaked", shaker.getName()));
        effects.addAll(List.of("empty", shot.getName()));
        effects.addAll(List.of("not", "clean", shaker.getName()));
        effects.addAll(List.of("not", "empty", shaker.getName()));
        effects.addAll(List.of("not", "contains", shot.getName(), ingredient.getName()));

        super("pour-shot-to-clean-shaker", parameters, preconditions, effects);
        this.shot = shot;
        this.ingredient = ingredient;
        this.shaker = shaker;
        this.hand = hand;
        this.levelA = levelA;
        this.levelB = levelB;
    }
}