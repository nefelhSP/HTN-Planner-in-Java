package barman;
import java.util.ArrayList;
import java.util.List;
import barman.Types.*;

public class MakeCocktail extends Method{

    private final Shaker shaker;
    private final Cocktail cocktail;
    private final Ingredient ingredientA;
    private final Hand handA;
    private final Hand handB;
    private final Ingredient ingredientB;
    private final Task taskToDecompose;

    public MakeCocktail(Shaker shaker, Cocktail cocktail, Ingredient ingredientA, Hand handA, Hand handB, Ingredient ingredientB) {
        List<String> parameters = List.of(shaker.getName(), cocktail.getName(), ingredientA.getName(),
                handA.getName(), handB.getName(), ingredientB.getName());
        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("cocktailPart1", cocktail.getName(), ingredientB.getName()));
        preconditions.addAll(List.of("cocktailPart2", cocktail.getName(), ingredientA.getName()));
        if (handA.getName().equals(handB.getName())) {
            throw new IllegalArgumentException("Can't use the same hand twice: " + handA.getName());
        }
        if (ingredientA.getName().equals(ingredientB.getName())) {
            throw new IllegalArgumentException("Ingredients must be different: " + ingredientA.getName());
        }
        preconditions.addAll(List.of("not", "=", handA.getName(), handB.getName()));

        List<Task> subtasks = List.of(new AchieveCleanShaker(shaker),
                new AchieveContainsShakerIngredient(shaker, ingredientB),
                new AchieveContainsShakerIngredient(shaker, ingredientA),
                new AchieveHolding(handB, shaker),
                new AchieveHandEmpty(handA),
                new shake(cocktail, ingredientB, ingredientA, shaker, handB, handA));

        Task taskToDecompose = new AchieveContainsShakerCocktail(shaker, cocktail);
        super("MakeCocktail", parameters, preconditions, subtasks, taskToDecompose);

        this.shaker = shaker;
        this.cocktail = cocktail;
        this.ingredientA = ingredientA;
        this.handA = handA;
        this.handB = handB;
        this.ingredientB = ingredientB;
        this.taskToDecompose = taskToDecompose;
    }

}
