package barman;

import barman.Types.*;
import java.util.*;

/**
 * The drop class is an action that drops a container from a hand.
 */
public class drop extends Action {

    private final Hand hand;
    private final Container container;

    /**
     * Initializes the drop action with the given hand and container.
     * 
     * @param hand      The hand to drop the container from
     * @param container The container to drop
     */
    public drop(Hand hand, Container container) {

        // List that contains the parameters of the action
        List<String> parameters = List.of(hand.getName(), container.getName());

        // List that contains the preconditions of the action
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("holding", hand.getName(), container.getName()));

        // List that contains the effects of the action
        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("ontable", container.getName()));
        effects.addAll(List.of("handEmpty", hand.getName()));
        effects.addAll(List.of("not", "holding", hand.getName(), container.getName()));

        super("drop", parameters, preconditions, effects);
        this.hand = hand;
        this.container = container;
    }
}
