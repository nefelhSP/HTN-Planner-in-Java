package barman;
import barman.Types.*;
import java.util.ArrayList;
import java.util.List;

public class pour_shot_to_clean_shaker extends Action{

    private final Shot shot;
    private final Ingredient ingredient;
    private final Shaker shaker;
    private final Hand hand;
    private final Level levelA;
    private final Level levelB;

    public pour_shot_to_clean_shaker(Shot shot, Ingredient ingredient, Shaker shaker, Hand hand, Level levelA, Level levelB){

        List<String> parameters = List.of(shot.getName(), ingredient.getName(), shaker.getName(), hand.getName(), levelA.getName(), levelB.getName());

        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("contains", shot.getName(), ingredient.getName()));
        preconditions.addAll(List.of("empty", shaker.getName()));
        preconditions.addAll(List.of("clean", shaker.getName()));
        preconditions.addAll(List.of("holding", hand.getName(), shot.getName()));
        preconditions.addAll(List.of("shakerLevel", shaker.getName(), levelA.getName()));
        preconditions.addAll(List.of("next", levelA.getName(), levelB.getName()));

        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("contains", shaker.getName(), ingredient.getName()));
        effects.addAll(List.of("shakerLevel", shaker.getName(), levelB.getName()));
        effects.addAll(List.of("unshaked", shaker.getName()));
        effects.addAll(List.of("empty", shot.getName()));
        effects.addAll(List.of("not", "clean", shaker.getName()));
        effects.addAll(List.of("not", "empty", shaker.getName()));
        effects.addAll(List.of("not", "contains", shot.getName(), ingredient.getName()));
        effects.addAll(List.of("not", "shakerLevel", shaker.getName(), levelA.getName()));

        super("pour-shot-to-clean-shaker", parameters, preconditions, effects);
        this.shot = shot;
        this.ingredient = ingredient;
        this.shaker = shaker;
        this.hand = hand;
        this.levelA = levelA;
        this.levelB = levelB;
    }
}