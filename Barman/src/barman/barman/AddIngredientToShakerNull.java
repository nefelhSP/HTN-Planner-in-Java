package barman;

import java.util.*;
import barman.Types.*;

/**
 * AddIngredientToShakerNull class that implements the Method interface.
 * It represents the method: AddIngredientToShakerNull
 */
public class AddIngredientToShakerNull extends Method {

    private final Shaker shaker;
    private final Ingredient ingredient;

    /**
     * Constructor for AddIngredientToShakerNull
     * 
     * @param shaker         The shaker to add the ingredient to
     * @param ingredient     The ingredient to add
     * @param decomposedTask The decomposed task
     */
    public AddIngredientToShakerNull(Shaker shaker, Ingredient ingredient, Task decomposedTask) {
        // List that contains the parameters of the method
        List<String> parameters = List.of(shaker.getName(), ingredient.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("contains", shaker.getName(), ingredient.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of();
        super("AddIngredientToShakerNull", parameters, preconditions, subtasks, decomposedTask);

        this.shaker = shaker;
        this.ingredient = ingredient;

    }

}
