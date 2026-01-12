package barman;

import barman.Types.*;
import java.util.*;

/**
 * The empty_shaker class is an action that empties a shaker.
 */
public class empty_shaker extends Action {

    private final Hand hand;
    private final Shaker shaker;
    private final Cocktail cocktail;
    private final Level levelA;
    private final Level levelB;

    /**
     * Initializes the empty_shaker action with the given hand, shaker, cocktail,
     * levelA, and levelB.
     * 
     * @param hand     The hand to empty the shaker from
     * @param shaker   The shaker to empty
     * @param cocktail The cocktail to empty from the shaker
     * @param levelA   The level of the shaker
     * @param levelB   The level of the shaker
     */
    public empty_shaker(Hand hand, Shaker shaker, Cocktail cocktail, Level levelA, Level levelB) {

        // List that contains the parameters of the action
        List<String> parameters = List.of(hand.getName(), shaker.getName(),
                cocktail.getName(), levelA.getName(), levelB.getName());

        // List that contains the preconditions of the action
        List<List<String>> preconditions = new ArrayList<>();
        preconditions.add(List.of("holding", hand.getName(), shaker.getName()));
        preconditions.add(List.of("contains", shaker.getName(), cocktail.getName()));
        preconditions.add(List.of("shaked", shaker.getName()));
        preconditions.add(List.of("shakerEmptyLevel", shaker.getName(), levelB.getName()));
        preconditions.add(List.of("shakerLevel", shaker.getName(), levelA.getName()));

        // List that contains the effects of the action
        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("not", "shakerLevel", shaker.getName(), levelA.getName()));
        effects.addAll(List.of("empty", shaker.getName()));
        effects.addAll(List.of("unshaked", shaker.getName()));
        effects.addAll(List.of("shakerLevel", shaker.getName(), levelB.getName()));
        effects.addAll(List.of("not", "contains", shaker.getName(), cocktail.getName()));
        effects.addAll(List.of("not", "shaked", shaker.getName()));

        super("empty-shaker", parameters, preconditions, effects);
        this.hand = hand;
        this.shaker = shaker;
        this.cocktail = cocktail;
        this.levelA = levelA;
        this.levelB = levelB;
    }
}