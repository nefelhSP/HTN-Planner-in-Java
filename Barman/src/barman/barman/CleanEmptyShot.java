package barman;

import java.util.*;
import barman.Types.*;

/**
 * CleanEmptyShot class that implements the Method interface.
 * It represents the method: CleanEmptyShot
 */
public class CleanEmptyShot extends Method {

    private final Shot shot;
    private final Hand handA;
    private final Beverage beverage;
    private final Hand handB;

    /**
     * Constructor for CleanEmptyShot
     * 
     * @param shot           The shot to clean
     * @param handA          The hand to use
     * @param beverage       The beverage to clean
     * @param handB          The other hand to use
     * @param decomposedTask The decomposed task
     */
    public CleanEmptyShot(Shot shot, Hand handA, Beverage beverage, Hand handB, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(shot.getName(), handA.getName(),
                beverage.getName(), handB.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("empty", shot.getName()));
        preconditions.add(List.of("used", shot.getName(), beverage.getName()));
        preconditions.add(List.of("not", "=", handB.getName(), handA.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of(
                new AchieveHolding(handB, shot),
                new AchieveHandEmpty(handA),
                new clean_shot(shot, beverage, handB, handA));

        super("CleanEmptyShot", parameters, preconditions, subtasks, decomposedTask);

        this.shot = shot;
        this.handA = handA;
        this.beverage = beverage;
        this.handB = handB;
    }
}