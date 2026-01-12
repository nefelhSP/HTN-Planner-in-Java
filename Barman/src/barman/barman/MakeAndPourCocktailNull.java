package barman;

import java.util.*;
import barman.Types.*;

/**
 * The MakeAndPourCocktailNull class is a method that makes a cocktail and pours
 * it into a shot.
 */
public class MakeAndPourCocktailNull extends Method {

    private final Shot shot;
    private final Cocktail cocktail;

    /**
     * Initializes the MakeAndPourCocktailNull method with the given shot, cocktail,
     * and decomposed task.
     * 
     * @param shot           The shot to pour the cocktail into
     * @param cocktail       The cocktail to pour into the shot
     * @param decomposedTask The decomposed task
     */
    public MakeAndPourCocktailNull(Shot shot, Cocktail cocktail, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(shot.getName(), cocktail.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("contains", shot.getName(), cocktail.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of();

        super("MakeAndPourCocktailNull", parameters, preconditions, subtasks, decomposedTask);

        this.shot = shot;
        this.cocktail = cocktail;

    }
}
