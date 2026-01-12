package barman;

import barman.Types.*;
import java.util.*;

/**
 * The grasp class is an action that grasps a container.
 */
public class grasp extends Action {

    private final Hand hand;
    private final Container container;

    /**
     * Initializes the grasp action with the given hand and container.
     * 
     * @param hand      The hand to grasp the container with
     * @param container The container to grasp
     */
    public grasp(Hand hand, Container container) {

        // List that contains the parameters of the action
        List<String> parameters = List.of(hand.getName(), container.getName());

        // List that contains the preconditions of the action
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("ontable", container.getName()));
        preconditions.add(List.of("handEmpty", hand.getName()));

        // List that contains the effects of the action
        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("holding", hand.getName(), container.getName()));
        effects.addAll(List.of("not", "handEmpty", hand.getName()));
        effects.addAll(List.of("not", "ontable", container.getName()));

        super("grasp", parameters, preconditions, effects);
        this.hand = hand;
        this.container = container;
    }
}