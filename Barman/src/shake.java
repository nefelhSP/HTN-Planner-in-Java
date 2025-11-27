package barman;
import barman.Types.*;
import java.util.ArrayList;
import java.util.List;

public class shake extends Action{

    private final Cocktail cocktail;
    private final Ingredient ingredientA;
    private final Ingredient ingredientB;
    private final Shaker shaker;
    private final Hand handA;
    private final Hand handB;

    public shake(Cocktail cocktail, Ingredient ingredientA, Ingredient ingredientB, Shaker shaker, Hand handA, Hand handB){

        List<String> parameters = List.of(cocktail.getName(), ingredientA.getName(), ingredientB.getName(), shaker.getName(), handA.getName(), handB.getName());

        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("handEmpty", handB.getName()));
        preconditions.addAll(List.of("holding", handA.getName(), shaker.getName()));
        preconditions.addAll(List.of("contains", shaker.getName(), ingredientA.getName()));
        preconditions.addAll(List.of("contains", shaker.getName(), ingredientB.getName()));
        preconditions.addAll(List.of("unshaked", shaker.getName()));
        preconditions.addAll(List.of("cocktailPart1", cocktail.getName(), ingredientA.getName()));
        preconditions.addAll(List.of("cocktailPart2", cocktail.getName(), ingredientB.getName()));

        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("shaked", shaker.getName()));
        effects.addAll(List.of("contains", shaker.getName(), cocktail.getName()));
        effects.addAll(List.of("not", "unshaked", shaker.getName()));
        effects.addAll(List.of("not", "contains", shaker.getName(), ingredientA.getName()));
        effects.addAll(List.of("not", "contains", shaker.getName(), ingredientB.getName()));

        super("shake", parameters, preconditions, effects);
        this.cocktail = cocktail;
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.shaker = shaker;
        this.handA = handA;
        this.handB = handB;
    }
}