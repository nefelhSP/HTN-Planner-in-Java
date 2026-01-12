package barman;

import barman.Types.*;
import java.util.*;

/**
 * CleanShakerNull class that implements the Method interface.
 * It represents the method: CleanShakerNull
 */
public class CleanShakerNull extends Method {
    private final Shaker shaker;
    private Task taskToDecompose;

    /**
     * Constructor for CleanShakerNull
     * 
     * @param shaker         The shaker to clean
     * @param decomposedTask The decomposed task
     */
    public CleanShakerNull(Shaker shaker, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(shaker.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("clean", shaker.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of();

        super("CleanShakerNull", parameters, preconditions, subtasks, decomposedTask);

        this.shaker = shaker;
    }
}
