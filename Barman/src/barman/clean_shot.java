package barman;

import barman.Types.*;
import java.util.*;

/**
 * clean_shot class that implements the Action interface.
 * It represents the action: clean-shot
 */
public class clean_shot extends Action {

    private final Shot shot;
    private final Beverage beverage;
    private final Hand handA;
    private final Hand handB;

    /**
     * Constructor for clean_shot
     * 
     * @param shot     The shot to clean
     * @param beverage The beverage to clean
     * @param handA    The hand to use
     * @param handB    The other hand to use
     */
    public clean_shot(Shot shot, Beverage beverage, Hand handA, Hand handB) {

        // List that contains the parameters of the action
        List<String> parameters = List.of(shot.getName(), beverage.getName(), handA.getName(), handB.getName());

        // List that contains the preconditions of the action
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("holding", handA.getName(), shot.getName()));
        preconditions.add(List.of("handEmpty", handB.getName()));
        preconditions.add(List.of("empty", shot.getName()));
        preconditions.add(List.of("used", shot.getName(), beverage.getName()));

        // List that contains the effects of the action
        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("clean", shot.getName()));
        effects.addAll(List.of("not", "used", shot.getName(), beverage.getName()));

        super("clean-shot", parameters, preconditions, effects);
        this.shot = shot;
        this.beverage = beverage;
        this.handA = handA;
        this.handB = handB;

    }
}
