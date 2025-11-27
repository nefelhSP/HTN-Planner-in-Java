package barman;
import java.util.ArrayList;
import java.util.List;
import barman.Types.*;

public class AddIngredientToUsedShaker extends Method{

    private final Shaker shaker;
    private final Ingredient ingredient;
    private final Level levelA;
    private final Level levelB;
    private final Shot shot;
    private final Hand hand;
    private final Task taskToDecompose;

    public AddIngredientToUsedShaker(Shaker shaker, Ingredient ingredient, Level levelA, Level levelB,Shot shot, Hand hand){
        List<String> parameters = List.of(shaker.getName(), ingredient.getName(), levelA.getName(),
                levelB.getName(), shot.getName(), hand.getName());
        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("not", "empty", shaker.getName()));
        preconditions.addAll(List.of("shakerLevel", shaker.getName(), levelA.getName()));
        preconditions.addAll(List.of("next", levelA.getName(), levelB.getName()));


        List<Task> subtasks = List.of(
                new AchieveContainsShotIngredient (shot, ingredient),
                new AchieveHolding (hand, shot),
                new pour_shot_to_used_shaker(shot, ingredient, shaker, hand, levelA, levelB));

        Task taskToDecompose = new AchieveContainsShakerIngredient (shaker, ingredient);
        super("AddIngredientToUsedShaker", parameters, preconditions, subtasks, taskToDecompose);

        this.shaker = shaker;
        this.ingredient = ingredient;
        this.levelA = levelA;
        this.levelB = levelB;
        this.shot = shot;
        this.hand = hand;
        this.taskToDecompose = taskToDecompose;
    }

}
