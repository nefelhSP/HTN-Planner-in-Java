package barman;
import barman.Types.*;
import java.util.ArrayList;
import java.util.List;

public class pour_shaker_to_shot extends Action{

    private final Cocktail cocktail;
    private final Shot shot;
    private final Hand hand;
    private final Shaker shaker;
    private final Level levelA;
    private final Level levelB;

    public pour_shaker_to_shot(Cocktail cocktail, Shot shot, Hand hand, Shaker shaker, Level levelA, Level levelB){

        List<String> parameters = List.of(cocktail.getName(), shot.getName(), hand.getName(), shaker.getName(), levelA.getName(), levelB.getName());

        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("holding", hand.getName(), shaker.getName()));
        preconditions.addAll(List.of("contains", shaker.getName(), cocktail.getName()));
        preconditions.addAll(List.of("shaked", shaker.getName()));
        preconditions.addAll(List.of("clean", shot.getName()));
        preconditions.addAll(List.of("empty", shot.getName()));
        preconditions.addAll(List.of("shakerLevel", shaker.getName(), levelA.getName()));
        preconditions.addAll(List.of("next", levelB.getName(), levelA.getName()));

        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("contains", shot.getName(), cocktail.getName()));
        effects.addAll(List.of("used", shot.getName(), cocktail.getName()));
        effects.addAll(List.of("shakerLevel", shaker.getName(), levelB.getName()));
        effects.addAll(List.of("not", "clean", shot.getName()));
        effects.addAll(List.of("not", "empty", shot.getName()));
        effects.addAll(List.of("not", "shakerLevel", shaker.getName(), levelA.getName()));

        super("pour-shaker-to-shot", parameters, preconditions, effects);
        this.cocktail = cocktail;
        this.shot = shot;
        this.hand = hand;
        this.shaker = shaker;
        this.levelA = levelA;
        this.levelB = levelB;
    }
}