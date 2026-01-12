package barman;

import java.util.*;
import barman.Types.*;

/**
 * The OnTableNull class is a method that checks if a container is on the table.
 */
public class OnTableNull extends Method {

    private final Types.Container container;

    /**
     * Initializes the OnTableNull method with the given container and decomposed
     * task.
     * 
     * @param container      The container to check
     * @param decomposedTask The decomposed task
     */
    public OnTableNull(Container container, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(container.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("ontable", container.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of();

        super("OnTableNull", parameters, preconditions, subtasks, decomposedTask);

        this.container = container;

    }
}
