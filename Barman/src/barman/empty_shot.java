package barman;

import barman.Types.*;
import java.util.*;

/**
 * The empty_shot class is an action that empties a shot.
 */
public class empty_shot extends Action {

    private final Hand hand;
    private final Shot shot;
    private final Beverage beverage;

    /**
     * Initializes the empty_shot action with the given hand, shot, and beverage.
     * 
     * @param hand     The hand to empty the shot from
     * @param shot     The shot to empty
     * @param beverage The beverage to empty from the shot
     */
    public empty_shot(Hand hand, Shot shot, Beverage beverage) {

        // List that contains the parameters of the action
        List<String> parameters = List.of(hand.getName(), shot.getName(), beverage.getName());

        // List that contains the preconditions of the action
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("holding", hand.getName(), shot.getName()));
        preconditions.add(List.of("contains", shot.getName(), beverage.getName()));

        // List that contains the effects of the action
        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("empty", shot.getName()));
        effects.addAll(List.of("not", "contains", shot.getName(), beverage.getName()));

        super("empty-shot", parameters, preconditions, effects);
        this.hand = hand;
        this.shot = shot;
        this.beverage = beverage;
    }
}
