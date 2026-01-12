package barman;

import barman.Types.*;
import java.util.*;

/**
 * The pour_shaker_to_shot class is an action that pours a shaker to a shot.
 */
public class pour_shaker_to_shot extends Action {

    private final Cocktail cocktail;
    private final Shot shot;
    private final Hand hand;
    private final Shaker shaker;
    private final Level levelA;
    private final Level levelB;

    /**
     * Initializes the pour_shaker_to_shot action with the given cocktail, shot,
     * hand, shaker, levelA, levelB, and decomposed task.
     * 
     * @param cocktail The cocktail to pour
     * @param shot     The shot to pour into
     * @param hand     The hand to use
     * @param shaker   The shaker to pour from
     * @param levelA   The level of the shaker
     * @param levelB   The level of the shot
     */
    public pour_shaker_to_shot(Cocktail cocktail, Shot shot, Hand hand, Shaker shaker, Level levelA, Level levelB) {

        // List that contains the parameters of the action
        List<String> parameters = List.of(cocktail.getName(), shot.getName(), hand.getName(), shaker.getName(),
                levelA.getName(), levelB.getName());

        // List that contains the preconditions of the action
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("holding", hand.getName(), shaker.getName()));
        preconditions.add(List.of("contains", shaker.getName(), cocktail.getName()));
        preconditions.add(List.of("shaked", shaker.getName()));
        preconditions.add(List.of("clean", shot.getName()));
        preconditions.add(List.of("empty", shot.getName()));
        preconditions.add(List.of("shakerLevel", shaker.getName(), levelA.getName()));
        preconditions.add(List.of("next", levelB.getName(), levelA.getName()));

        // List that contains the effects of the action
        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("contains", shot.getName(), cocktail.getName()));
        effects.addAll(List.of("used", shot.getName(), cocktail.getName()));
        effects.addAll(List.of("shakerLevel", shaker.getName(), levelB.getName()));
        effects.addAll(List.of("not", "clean", shot.getName()));
        effects.addAll(List.of("not", "empty", shot.getName()));
        effects.addAll(List.of("not", "shakerLevel", shaker.getName(), levelA.getName()));

        super("pour-shaker-to-shot", parameters, preconditions, effects);
        this.cocktail = cocktail;
        this.shot = shot;
        this.hand = hand;
        this.shaker = shaker;
        this.levelA = levelA;
        this.levelB = levelB;
    }
}