package barman;

import java.util.*;
import barman.Types.*;

/**
 * AddIngredientToShot class that implements the Method interface.
 * It represents the method: AddIngredientToShot
 */
public class AddIngredientToShot extends Method {

    private final Shot shot;
    private final Ingredient ingredient;
    private final Dispenser dispenser;
    private final Hand handA;
    private final Hand handB;

    /**
     * Constructor for AddIngredientToShot
     * 
     * @param shot           The shot to add the ingredient to
     * @param ingredient     The ingredient to add
     * @param dispenser      The dispenser to use
     * @param handA          The hand to use
     * @param handB          The other hand to use
     * @param decomposedTask The decomposed task
     */
    public AddIngredientToShot(Shot shot, Ingredient ingredient, Dispenser dispenser, Hand handA, Hand handB,
            Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(shot.getName(), ingredient.getName(), dispenser.getName(),
                handA.getName(), handB.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("not", "contains", shot.getName(), ingredient.getName()));
        preconditions.add(List.of("dispenses", dispenser.getName(), ingredient.getName()));
        preconditions.add(List.of("not", "=", handB.getName(), handA.getName()));

        if (handA.getName().equals(handB.getName())) {
            System.err.println("Error found, cant have two of the same hand");
        }

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of(
                new AchieveCleanShot(shot),
                new AchieveHolding(handB, shot),
                new AchieveHandEmpty(handA),
                new fill_shot(shot, ingredient, handB, handA, dispenser));

        super("AddIngredientToShot", parameters, preconditions, subtasks, decomposedTask);

        this.shot = shot;
        this.ingredient = ingredient;
        this.dispenser = dispenser;
        this.handA = handA;
        this.handB = handB;

    }

}
