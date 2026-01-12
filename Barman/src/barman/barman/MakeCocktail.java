package barman;

import java.util.*;
import barman.Types.*;

/**
 * The MakeCocktail class is a method that makes a cocktail.
 */
public class MakeCocktail extends Method {

        private final Shaker shaker;
        private final Cocktail cocktail;
        private final Ingredient ingredientA;
        private final Hand handA;
        private final Hand handB;
        private final Ingredient ingredientB;

        /**
         * Initializes the MakeCocktail method with the given shaker, cocktail,
         * ingredientA, handA, handB, ingredientB, and decomposed task.
         * 
         * @param shaker         The shaker to pour the cocktail from
         * @param cocktail       The cocktail to pour into the shaker
         * @param ingredientA    The first ingredient to pour into the shaker
         * @param handA          The first hand to hold the shaker
         * @param handB          The second hand to hold the shaker
         * @param ingredientB    The second ingredient to pour into the shaker
         * @param decomposedTask The decomposed task
         */

        public MakeCocktail(Shaker shaker, Cocktail cocktail, Ingredient ingredientA, Hand handA, Hand handB,
                        Ingredient ingredientB, Task decomposedTask) {

                // List that contains the parameters of the method
                List<String> parameters = List.of(shaker.getName(), cocktail.getName(), ingredientA.getName(),
                                handA.getName(), handB.getName(), ingredientB.getName());

                // List that contains the preconditions of the method
                List<List<String>> preconditions = new ArrayList<>();
                preconditions.add(List.of("cocktailPart1", cocktail.getName(), ingredientB.getName()));
                preconditions.add(List.of("cocktailPart2", cocktail.getName(), ingredientA.getName()));
                preconditions.add(List.of("not", "=", handB.getName(), handA.getName()));

                // List that contains the subtasks of the method
                List<Task> subtasks = List.of(
                                new AchieveCleanShaker(shaker),
                                new AchieveContainsShakerIngredient(shaker, ingredientB),
                                new AchieveContainsShakerIngredient(shaker, ingredientA),
                                new AchieveHolding(handB, shaker),
                                new AchieveHandEmpty(handA),
                                new shake(cocktail, ingredientB, ingredientA, shaker, handB, handA));

                super("MakeCocktail", parameters, preconditions, subtasks, decomposedTask);

                this.shaker = shaker;
                this.cocktail = cocktail;
                this.ingredientA = ingredientA;
                this.handA = handA;
                this.handB = handB;
                this.ingredientB = ingredientB;
        }
}