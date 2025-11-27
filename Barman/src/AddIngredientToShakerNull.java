package barman;
import java.util.ArrayList;
import java.util.List;
import barman.Types.*;

public class AddIngredientToShakerNull extends Method{

    private final Shaker shaker;
    private final Ingredient ingredient;
    private final Task taskToDecompose;

    public AddIngredientToShakerNull(Shaker shaker, Ingredient ingredient){
        List<String> parameters = List.of(shaker.getName(), ingredient.getName());
        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("contains", shaker.getName(), ingredient.getName()));

        List<Task> subtasks = List.of();
        Task taskToDecompose = new AchieveContainsShakerIngredient (shaker, ingredient);
        super("AddIngredientToShakerNull", parameters, preconditions, subtasks, taskToDecompose);

        this.shaker = shaker;
        this.ingredient = ingredient;
        this.taskToDecompose = taskToDecompose;
    }

}
