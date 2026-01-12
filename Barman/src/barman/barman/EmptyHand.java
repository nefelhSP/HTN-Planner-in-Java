package barman;

import java.util.*;
import barman.Types.*;

/**
 * The EmptyHand class is a method that empties a container from a hand.
 */
public class EmptyHand extends Method {

    private final Hand hand;
    private final Container container;

    /**
     * Initializes the EmptyHand method with the given hand, container, and
     * decomposed task.
     * 
     * @param hand           The hand to empty the container from
     * @param container      The container to empty
     * @param decomposedTask The decomposed task
     */
    public EmptyHand(Hand hand, Container container, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(hand.getName(), container.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("holding", hand.getName(), container.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of(new drop(hand, container));

        super("EmptyHand", parameters, preconditions, subtasks, decomposedTask);

        this.hand = hand;
        this.container = container;
    }
}
