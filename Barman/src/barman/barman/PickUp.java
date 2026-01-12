package barman;

import barman.Types.*;
import java.util.*;

/**
 * The PickUp class is a method that picks up a container.
 */
public class PickUp extends Method {

    private final Hand hand;
    private final Container container;

    /**
     * Initializes the PickUp method with the given hand, container, and decomposed
     * task.
     * 
     * @param hand           The hand to use
     * @param container      The container to pick up
     * @param decomposedTask The decomposed task
     */
    public PickUp(Hand hand, Container container, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(hand.getName(), container.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("not", "holding", hand.getName(), container.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of(
                new AchieveHandEmpty(hand),
                new AchieveOnTable(container),
                new grasp(hand, container));

        super("PickUp", parameters, preconditions, subtasks, decomposedTask);

        this.hand = hand;
        this.container = container;

    }
}
