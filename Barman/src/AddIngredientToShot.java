package barman;
import java.util.ArrayList;
import java.util.List;
import barman.Types.*;

public class AddIngredientToShot extends Method{

    private final Shot shot;
    private final Ingredient ingredient;
    private final Dispenser dispenser;
    private final Hand handA;
    private final Hand handB;
    private final Task taskToDecompose;

    public AddIngredientToShot(Shot shot, Ingredient ingredient, Dispenser dispenser, Hand handA, Hand handB){

        List<String> parameters = List.of(shot.getName(), ingredient.getName(), dispenser.getName(),
                handA.getName(), shot.getName(), handB.getName());
        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("not", "contains", shot.getName(), ingredient.getName()));
        preconditions.addAll(List.of("dispenses", dispenser.getName(), ingredient.getName()));
        if (!handA.getName().equals(handB.getName())) {
            preconditions.addAll(List.of("not_equal", handA.getName(), handB.getName()));
        } else {
            System.err.println("Error found, cant have two of the same hand");
        }

        List<Task> subtasks = List.of(
                new AchieveCleanShot (shot),
                new AchieveHolding  (handB, shot),
                new AchieveHandEmpty(handA),
                new fill_shot(shot, ingredient, handB, handA, dispenser));

        Task taskToDecompose = new AchieveContainsShotIngredient (shot, ingredient);
        super("AddIngredientToShot", parameters, preconditions, subtasks, taskToDecompose);

        this.shot = shot;
        this.ingredient = ingredient;
        this.dispenser = dispenser;
        this.handA = handA;
        this.handB = handB;
        this.taskToDecompose = taskToDecompose;

    }

}
