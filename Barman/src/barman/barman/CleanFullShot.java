package barman;

import java.util.*;
import barman.Types.*;

/**
 * CleanFullShot class that implements the Method interface.
 * It represents the method: CleanFullShot
 */
public class CleanFullShot extends Method {

    private final Shot shot;
    private final Hand handA;
    private final Beverage beverage;
    private final Hand handB;

    /**
     * Constructor for CleanFullShot
     * 
     * @param shot           The shot to clean
     * @param handA          The hand to use
     * @param beverage       The beverage to clean
     * @param handB          The other hand to use
     * @param decomposedTask The decomposed task
     */
    public CleanFullShot(Shot shot, Hand handA, Beverage beverage, Hand handB, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(shot.getName(), handA.getName(),
                beverage.getName(), handB.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("contains", shot.getName(), beverage.getName()));
        preconditions.add(List.of("not", "=", handB.getName(), handA.getName()));

        if (handA.getName().equals(handB.getName())) {
            System.err.println("Error found, cant have two of the same hand");
        }

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of(
                new AchieveHolding(handB, shot),
                new empty_shot(handB, shot, beverage),
                new AchieveHandEmpty(handA),
                new clean_shot(shot, beverage, handA, handB));

        super("CleanFullShot", parameters, preconditions, subtasks, decomposedTask);

        this.shot = shot;
        this.handA = handA;
        this.beverage = beverage;
        this.handB = handB;

    }

}
