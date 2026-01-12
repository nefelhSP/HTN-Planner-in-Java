package barman;

import java.util.*;
import barman.Types.*;

/**
 * The HandEmptyNull class is a method that checks if a hand is empty.
 */
public class HandEmptyNull extends Method {

    private final Hand hand;

    /**
     * Initializes the HandEmptyNull method with the given hand and decomposed task.
     * 
     * @param hand           The hand to check if it is empty
     * @param decomposedTask The decomposed task
     */
    public HandEmptyNull(Hand hand, Task decomposedTask) {

        // List that contains the parameters of the method
        List<String> parameters = List.of(hand.getName());

        // List that contains the preconditions of the method
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("handEmpty", hand.getName()));

        // List that contains the subtasks of the method
        List<Task> subtasks = List.of();

        super("HandEmptyNull", parameters, preconditions, subtasks, decomposedTask);

        this.hand = hand;
    }
}
