package barman;

import barman.Types.*;
import java.util.*;

/**
 * The fill_shot class is an action that fills a shot with an ingredient.
 */
public class fill_shot extends Action {

    private final Shot shot;
    private final Ingredient ingredient;
    private final Hand handA;
    private final Hand handB;
    private final Dispenser dispenser;

    /**
     * Initializes the fill_shot action with the given shot, ingredient, handA,
     * handB, and dispenser.
     * 
     * @param shot       The shot to fill
     * @param ingredient The ingredient to fill the shot with
     * @param handA      The hand to hold the shot
     * @param handB      The hand to hold the dispenser
     * @param dispenser  The dispenser to fill the shot from
     */
    public fill_shot(Shot shot, Ingredient ingredient, Hand handA, Hand handB, Dispenser dispenser) {
        // List that contains the parameters of the action
        List<String> parameters = List.of(shot.getName(), ingredient.getName(), handA.getName(), handB.getName(),
                dispenser.getName());

        // List that contains the preconditions of the action
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("holding", handA.getName(), shot.getName()));
        preconditions.add(List.of("handEmpty", handB.getName()));
        preconditions.add(List.of("empty", shot.getName()));
        preconditions.add(List.of("clean", shot.getName()));
        preconditions.add(List.of("dispenses", dispenser.getName(), ingredient.getName()));

        // List that contains the effects of the action
        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("contains", shot.getName(), ingredient.getName()));
        effects.addAll(List.of("used", shot.getName(), ingredient.getName()));
        effects.addAll(List.of("not", "clean", shot.getName()));
        effects.addAll(List.of("not", "empty", shot.getName()));

        super("fill-shot", parameters, preconditions, effects);
        this.shot = shot;
        this.ingredient = ingredient;
        this.handA = handA;
        this.handB = handB;
        this.dispenser = dispenser;
    }
}
