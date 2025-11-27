package barman;
import java.util.List;
import barman.Types.*;

public class MakeAndPourCocktail extends Method{

    private final Shot shot;
    private final Cocktail cocktail;
    private final Shaker shaker;
    private final Hand hand;
    private final Task taskToDecompose;

    public MakeAndPourCocktail(Shot shot, Cocktail cocktail, Shaker shaker, Hand hand) {

        List<String> parameters = List.of(shot.getName(), cocktail.getName(), shaker.getName(), hand.getName());
        List<String> preconditions = List.of("not", "contains", shot.getName(), cocktail.getName());
        List<Task> subtasks = List.of(
                new AchieveContainsShakerCocktail(shaker, cocktail),
                new AchieveCleanShot(shot),
                new AchieveHolding(hand, shaker),
                new DoPourShakerToShot(shaker, shot, cocktail));

        Task taskToDecompose = new AchieveContainsShotCocktail(shot, cocktail);
        super("MakeAndPourCocktail", parameters, preconditions, subtasks, taskToDecompose);

        this.shot = shot;
        this.cocktail = cocktail;
        this.shaker = shaker;
        this.hand = hand;
        this.taskToDecompose = taskToDecompose;
    }
}
