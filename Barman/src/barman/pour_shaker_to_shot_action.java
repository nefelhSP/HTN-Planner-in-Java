package barman;

import barman.Types.*;
import java.util.*;

/**
 * The pour_shaker_to_shot_action class is a method that pours a shaker to a
 * shot.
 */
public class pour_shaker_to_shot_action extends Method {

    private final Shaker shaker;
    private final Shot shot;
    private final Cocktail cocktail;
    private final Level levelA;
    private final Hand hand;
    private final Level levelB;

    /**
     * Initializes the pour_shaker_to_shot_action method with the given shaker,
     * shot, cocktail, levelA, hand, levelB, and decomposed task.
     * 
     * @param shaker         The shaker to pour from
     * @param shot           The shot to pour into
     * @param cocktail       The cocktail to pour
     * @param levelA         The level of the shaker
     * @param hand           The hand to use
     * @param levelB         The level of the shot
     * @param decomposedTask The decomposed task
     */
    public pour_shaker_to_shot_action(Shaker shaker, Shot shot, Cocktail cocktail, Level levelA, Hand hand,
            Level levelB, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(shaker.getName(), shot.getName(), cocktail.getName(),
                levelA.getName(), hand.getName(), levelB.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("holding", hand.getName(), shaker.getName()));
        preconditions.add(List.of("shaked", shaker.getName()));
        preconditions.add(List.of("empty", shot.getName()));
        preconditions.add(List.of("clean", shot.getName()));
        preconditions.add(List.of("contains", shaker.getName(), cocktail.getName()));
        preconditions.add(List.of("shakerLevel", shaker.getName(), levelA.getName()));
        preconditions.add(List.of("next", levelB.getName(), levelA.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of(new pour_shaker_to_shot(cocktail, shot, hand, shaker, levelA, levelB));

        super("pour-shaker-to-shot_action", parameters, preconditions, subtasks, decomposedTask);

        this.shaker = shaker;
        this.shot = shot;
        this.cocktail = cocktail;
        this.levelA = levelA;
        this.hand = hand;
        this.levelB = levelB;

    }
}
