package barman;
import barman.Types.*;
import java.util.ArrayList;
import java.util.List;

public class empty_shaker extends Action{

    private final Hand hand;
    private final Shaker shaker;
    private final Cocktail cocktail;
    private final Level levelA;
    private final Level levelB;

    public empty_shaker(Hand hand, Shaker shaker, Cocktail cocktail, Level levelA, Level levelB){

        List<String> parameters = List.of(hand.getName(), shaker.getName(),
                cocktail.getName(), levelA.getName(), levelB.getName());

        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("holding", hand.getName(), shaker.getName()));
        preconditions.addAll(List.of("contains", shaker.getName(), cocktail.getName()));
        preconditions.addAll(List.of("shaked", shaker.getName()));
        preconditions.addAll(List.of("shakerEmptyLevel", shaker.getName(), levelB.getName()));
        preconditions.addAll(List.of("shakerLevel", shaker.getName(), levelA.getName()));

        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("empty", shaker.getName()));
        effects.addAll(List.of("unshaked", shaker.getName()));
        effects.addAll(List.of("shakerLevel", shaker.getName(), levelB.getName()));
        effects.addAll(List.of("not", "contains", shaker.getName(), cocktail.getName()));
        effects.addAll(List.of("not", "shakerLevel", shaker.getName(), levelA.getName()));
        effects.addAll(List.of("not", "shaked", shaker.getName()));

        super("empty-shaker", parameters, preconditions, effects);
        this.hand = hand;
        this.shaker = shaker;
        this.cocktail = cocktail;
        this.levelA = levelA;
        this.levelB = levelB;
    }
}