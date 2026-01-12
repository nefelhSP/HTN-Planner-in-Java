package barman;

import java.util.*;
import barman.Types.*;

/**
 * The MakeAndPourCocktail class is a method that makes a cocktail and pours it
 * into a shot.
 */
public class MakeAndPourCocktail extends Method {

    private final Shot shot;
    private final Cocktail cocktail;
    private final Shaker shaker;
    private final Hand hand;

    /**
     * Initializes the MakeAndPourCocktail method with the given shot, cocktail,
     * shaker, hand, and decomposed task.
     * 
     * @param shot           The shot to pour the cocktail into
     * @param cocktail       The cocktail to pour into the shot
     * @param shaker         The shaker to pour the cocktail from
     * @param hand           The hand to hold the shaker
     * @param decomposedTask The decomposed task
     */
    public MakeAndPourCocktail(Shot shot, Cocktail cocktail, Shaker shaker, Hand hand, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(shot.getName(), cocktail.getName(), shaker.getName(), hand.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("not", "contains", shot.getName(), cocktail.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of(
                new AchieveContainsShakerCocktail(shaker, cocktail),
                new AchieveCleanShot(shot),
                new AchieveHolding(hand, shaker),
                new DoPourShakerToShot(shaker, shot, cocktail));

        super("MakeAndPourCocktail", parameters, preconditions, subtasks, decomposedTask);

        this.shot = shot;
        this.cocktail = cocktail;
        this.shaker = shaker;
        this.hand = hand;

    }
}
