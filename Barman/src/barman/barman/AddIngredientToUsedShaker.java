package barman;

import java.util.*;
import barman.Types.*;

/**
 * AddIngredientToUsedShaker class that implements the Method interface.
 * It represents the method: AddIngredientToUsedShaker
 */
public class AddIngredientToUsedShaker extends Method {

        private final Shaker shaker;
        private final Ingredient ingredient;
        private final Level levelA;
        private final Level levelB;
        private final Shot shot;
        private final Hand hand; // Restored
        private final Hand otherHand; // NEW

        /**
         * Constructor for AddIngredientToUsedShaker
         * 
         * @param shaker         The shaker to add the ingredient to
         * @param ingredient     The ingredient to add
         * @param levelA         The level of the shaker
         * @param levelB         The level of the shaker
         * @param shot           The shot to add the ingredient to
         * @param hand           The hand to use
         * @param otherHand      The other hand to use
         * @param decomposedTask The decomposed task
         */
        public AddIngredientToUsedShaker(Shaker shaker, Ingredient ingredient, Level levelA, Level levelB, Shot shot,
                        Hand hand, Hand otherHand, Task decomposedTask) {

                // List that contains the parameters of the method
                List<String> parameters = List.of(shaker.getName(), ingredient.getName(), levelA.getName(),
                                levelB.getName(), shot.getName(), hand.getName());

                // List that contains the preconditions of the method
                List<List<String>> preconditions = new ArrayList<>();
                preconditions.add(List.of("not", "empty", shaker.getName()));
                preconditions.add(List.of("shakerLevel", shaker.getName(), levelA.getName()));
                preconditions.add(List.of("next", levelA.getName(), levelB.getName()));

                // List that contains the subtasks of the method
                List<Task> subtasks = List.of(
                                new AchieveContainsShotIngredient(shot, ingredient, otherHand),
                                new pour_shot_to_used_shaker(shot, ingredient, shaker, otherHand, levelA, levelB));

                super("AddIngredientToUsedShaker", parameters, preconditions, subtasks, decomposedTask);

                this.shaker = shaker;
                this.ingredient = ingredient;
                this.levelA = levelA;
                this.levelB = levelB;
                this.shot = shot;
                this.hand = hand;
                this.otherHand = otherHand;
        }
}