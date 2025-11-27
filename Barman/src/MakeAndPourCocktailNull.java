package barman;
import java.util.List;
import barman.Types.*;

public class MakeAndPourCocktailNull extends Method{

    private final Shot shot;
    private final Cocktail cocktail;
    private final Task taskToDecompose;

    public MakeAndPourCocktailNull(Shot shot, Cocktail cocktail) {
        List<String> parameters = List.of(shot.getName(), cocktail.getName());
        List<String> preconditions = List.of("contains", shot.getName(), cocktail.getName());
        List<Task> subtasks = List.of();

        Task taskToDecompose = new AchieveContainsShotCocktail(shot, cocktail);
        super("MakeAndPourCocktailNull", parameters, preconditions, subtasks, taskToDecompose);

        this.shot = shot;
        this.cocktail = cocktail;
        this.taskToDecompose = taskToDecompose;
    }
}
