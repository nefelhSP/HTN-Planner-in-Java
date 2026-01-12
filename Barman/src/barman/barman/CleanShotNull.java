package barman;

import java.util.*;
import barman.Types.*;

/**
 * CleanShotNull class that implements the Method interface.
 * It represents the method: CleanShotNull
 */
public class CleanShotNull extends Method {

    private final Shot shot;

    /**
     * Constructor for CleanShotNull
     * 
     * @param shot           The shot to clean
     * @param decomposedTask The decomposed task
     */
    public CleanShotNull(Shot shot, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(shot.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("clean", shot.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of();

        super("CleanShotNull", parameters, preconditions, subtasks, decomposedTask);

        this.shot = shot;
    }

}
