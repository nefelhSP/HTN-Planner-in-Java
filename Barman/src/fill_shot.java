package barman;
import barman.Types.*;
import java.util.ArrayList;
import java.util.List;

public class fill_shot extends Action{

    private final Shot shot;
    private final Ingredient ingredient;
    private final Hand handA;
    private final Hand handB;
    private final Dispenser dispenser;

    public fill_shot(Shot shot, Ingredient ingredient, Hand handA, Hand handB, Dispenser dispenser){
        List<String> parameters = List.of(shot.getName(), ingredient.getName(), handA.getName(), handB.getName(), dispenser.getName());

        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("holding", handA.getName(), shot.getName()));
        preconditions.addAll(List.of("handEmpty", handB.getName()));
        preconditions.addAll(List.of("empty", shot.getName()));
        preconditions.addAll(List.of("clean", shot.getName()));
        preconditions.addAll(List.of("dispenses", dispenser.getName(), ingredient.getName()));

        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("contains", shot.getName(), ingredient.getName()));
        effects.addAll(List.of("used", shot.getName(), ingredient.getName()));
        effects.addAll(List.of("not", "clean", shot.getName()));
        effects.addAll(List.of("not", "empty", shot.getName()));

        super("fill-shot", parameters, preconditions, effects);
        this.shot = shot;
        this.ingredient = ingredient;
        this.handA = handA;
        this.handB = handB;
        this.dispenser = dispenser;
    }
}
