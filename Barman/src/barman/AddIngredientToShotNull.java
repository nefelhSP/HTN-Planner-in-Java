package barman;

import java.util.*;
import barman.Types.*;

/**
 * AddIngredientToShotNull class that implements the Method interface.
 * It represents the method: AddIngredientToShotNull
 */
public class AddIngredientToShotNull extends Method {

    private final Shot shot;
    private final Ingredient ingredient;

    /**
     * Constructor for AddIngredientToShotNull
     * 
     * @param shot           The shot to add the ingredient to
     * @param ingredient     The ingredient to add
     * @param decomposedTask The decomposed task
     */
    public AddIngredientToShotNull(Shot shot, Ingredient ingredient, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(shot.getName(), ingredient.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("contains", shot.getName(), ingredient.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of();

        super("AddIngredientToShotNull", parameters, preconditions, subtasks, decomposedTask);

        this.shot = shot;
        this.ingredient = ingredient;

    }

}
