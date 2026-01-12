package barman;

import barman.Types.*;
import java.util.*;

/**
 * clean_shaker class that implements the Action interface.
 * It represents the action: clean-shaker
 */
public class clean_shaker extends Action {

    private final Shaker shaker;
    private final Hand handA;
    private final Hand handB;

    /**
     * Constructor for clean_shaker
     * 
     * @param shaker The shaker to clean
     * @param handA  The hand to use
     * @param handB  The other hand to use
     */
    public clean_shaker(Shaker shaker, Hand handA, Hand handB) {

        // List that contains the parameters of the action
        List<String> parameters = List.of(shaker.getName(), handA.getName(), handB.getName());

        // List that contains the preconditions of the action
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("holding", handA.getName(), shaker.getName()));
        preconditions.add(List.of("empty", shaker.getName()));
        preconditions.add(List.of("handEmpty", handB.getName()));

        // List that contains the effects of the action
        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("clean", shaker.getName()));

        super("clean-shaker", parameters, preconditions, effects);
        this.shaker = shaker;
        this.handA = handA;
        this.handB = handB;

    }
}
