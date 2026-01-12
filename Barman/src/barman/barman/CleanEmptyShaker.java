package barman;

import barman.Types.*;
import java.util.*;

/**
 * CleanEmptyShaker class that implements the Method interface.
 * It represents the method: CleanEmptyShaker
 */
public class CleanEmptyShaker extends Method {

    private final Shaker shaker;
    private final Hand handA;
    private final Hand handB;

    /**
     * Constructor for CleanEmptyShaker
     * 
     * @param shaker         The shaker to clean
     * @param handA          The hand to use
     * @param handB          The other hand to use
     * @param decomposedTask The decomposed task
     */
    public CleanEmptyShaker(Shaker shaker, Hand handA, Hand handB, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(shaker.getName(), handA.getName(), handB.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("not", "clean", shaker.getName()));
        preconditions.add(List.of("empty", shaker.getName()));
        preconditions.add(List.of("not", "=", handB.getName(), handA.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of(
                new AchieveHolding(handB, shaker),
                new AchieveHandEmpty(handA),
                new clean_shaker(shaker, handB, handA));

        super("CleanEmptyShaker", parameters, preconditions, subtasks, decomposedTask);

        this.shaker = shaker;
        this.handA = handA;
        this.handB = handB;
    }
}