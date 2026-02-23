package barman;

import barman.Types.*;
import java.util.*;

/**
 * The PutDown class extends the Method class and represents the PutDown action.
 * It is used to represent the action of putting down a container that is
 * currently being held by a hand.
 */
public class PutDown extends Method {

    private final Types.Hand hand;
    private final Types.Container container;

    /**
     * @param container
     * @param hand
     * @param decomposedTask
     */
    public PutDown(Container container, Hand hand, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(container.getName(), hand.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("holding", hand.getName(), container.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of(new drop(hand, container));

        super("PutDown", parameters, preconditions, subtasks, decomposedTask);

        this.hand = hand;
        this.container = container;

    }
}