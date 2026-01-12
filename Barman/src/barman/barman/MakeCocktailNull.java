package barman;

import java.util.*;
import barman.Types.*;

/**
 * The MakeCocktailNull class is a method that makes a cocktail.
 */
public class MakeCocktailNull extends Method {

    private final Shaker shaker;
    private final Cocktail cocktail;

    /**
     * Initializes the MakeCocktailNull method with the given shaker, cocktail, and
     * decomposed task.
     * 
     * @param shaker         The shaker to pour the cocktail from
     * @param cocktail       The cocktail to pour into the shaker
     * @param decomposedTask The decomposed task
     */
    public MakeCocktailNull(Shaker shaker, Cocktail cocktail, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(shaker.getName(), cocktail.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("contains", shaker.getName(), cocktail.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of();

        super("MakeCocktailNull", parameters, preconditions, subtasks, decomposedTask);

        this.shaker = shaker;
        this.cocktail = cocktail;

    }

}
