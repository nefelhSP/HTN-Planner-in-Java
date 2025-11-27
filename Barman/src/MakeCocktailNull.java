package barman;
import java.util.ArrayList;
import java.util.List;
import barman.Types.*;

public class MakeCocktailNull extends Method{

    private final Shaker shaker;
    private final Cocktail cocktail;
    private final Task taskToDecompose;

    public MakeCocktailNull(Shaker shaker, Cocktail cocktail) {
        List<String> parameters = List.of(shaker.getName(), cocktail.getName());
        List<String> preconditions = List.of("contains", shaker.getName(), cocktail.getName());
        List<Task> subtasks = List.of();

        Task taskToDecompose = new AchieveContainsShakerCocktail(shaker, cocktail);
        super("MakeCocktailNull", parameters, preconditions, subtasks, taskToDecompose);

        this.shaker = shaker;
        this.cocktail = cocktail;
        this.taskToDecompose = taskToDecompose;
    }

}
