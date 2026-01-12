package barman;

import java.util.*;
import barman.Types.*;

public class HoldingNull extends Method {

    private final Hand hand;
    private final Container container;

    /**
     * Initializes the HoldingNull method with the given hand, container, and
     * decomposed task.
     * 
     * @param hand           The hand to check if it is holding the container
     * @param container      The container to check if it is being held
     * @param decomposedTask The decomposed task
     */
    public HoldingNull(Hand hand, Container container, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(hand.getName(), container.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("holding", hand.getName(), container.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of();

        super("HoldingNull", parameters, preconditions, subtasks, decomposedTask);

        this.hand = hand;
        this.container = container;

    }
}
