package barman;
import java.util.ArrayList;
import java.util.List;
import barman.Types.*;

public class AddIngredientToShotNull extends Method{

    private final Shot shot;
    private final Ingredient ingredient;
    private final Task taskToDecompose;

    public AddIngredientToShotNull(Shot shot, Ingredient ingredient){

        List<String> parameters = List.of(shot.getName(), ingredient.getName());
        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("contains", shot.getName(), ingredient.getName()));


        List<Task> subtasks = List.of();

        Task taskToDecompose = new AchieveContainsShotIngredient (shot, ingredient);
        super("AddIngredientToShotNull", parameters, preconditions, subtasks, taskToDecompose);

        this.shot = shot;
        this.ingredient = ingredient;
        this.taskToDecompose = taskToDecompose;

    }

}
