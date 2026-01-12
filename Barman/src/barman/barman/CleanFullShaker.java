package barman;

import java.util.*;
import barman.Types.*;

/**
 * CleanFullShaker class that implements the Method interface.
 * It represents the method: CleanFullShaker
 */
public class CleanFullShaker extends Method {

        private final Shaker shaker;
        private final Level levelA;
        private final Cocktail cocktail;
        private final Hand handA;
        private final Hand handB;
        private final Level levelB;

        /**
         * Constructor for CleanFullShaker
         * 
         * @param shaker         The shaker to clean
         * @param levelA         The level of the shaker
         * @param cocktail       The cocktail to clean
         * @param handA          The hand to use
         * @param handB          The other hand to use
         * @param levelB         The other level of the shaker
         * @param decomposedTask The decomposed task
         */
        public CleanFullShaker(Shaker shaker, Level levelA, Cocktail cocktail, Hand handA, Hand handB, Level levelB,
                        Task decomposedTask) {

                // List that contains the parameters of the method
                List<String> parameters = List.of(shaker.getName(), levelA.getName(), cocktail.getName(),
                                handA.getName(),
                                handB.getName(), levelB.getName());

                // List that contains the preconditions of the method
                List<List<String>> preconditions = new ArrayList<>();
                preconditions.add(List.of("contains", shaker.getName(), cocktail.getName()));
                preconditions.add(List.of("shaked", shaker.getName()));
                preconditions.add(List.of("shakerEmptyLevel", shaker.getName(), levelA.getName()));
                preconditions.add(List.of("shakerLevel", shaker.getName(), levelB.getName()));
                preconditions.add(List.of("not", "=", handB.getName(), handA.getName()));

                // List that contains the subtasks of the method
                List<Task> subtasks = List.of(
                                new AchieveHolding(handB, shaker),
                                new empty_shaker(handB, shaker, cocktail, levelB, levelA),
                                new AchieveHandEmpty(handA),
                                new clean_shaker(shaker, handB, handA));

                super("CleanFullShaker", parameters, preconditions, subtasks, decomposedTask);

                this.shaker = shaker;
                this.levelA = levelA;
                this.cocktail = cocktail;
                this.handA = handA;
                this.handB = handB;
                this.levelB = levelB;
        }
}