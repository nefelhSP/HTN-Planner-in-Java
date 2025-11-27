package barman;
import barman.Types.*;
import java.util.ArrayList;
import java.util.List;


public class pour_shaker_to_shot_action extends Method{

    private final Shaker shaker;
    private final Shot shot;
    private final Cocktail cocktail;
    private final Level levelA;
    private final Hand hand;
    private final Level levelB;
    private Task taskToDecompose;

    public pour_shaker_to_shot_action(Shaker shaker, Shot shot, Cocktail cocktail, Level levelA, Hand hand, Level levelB){

        List<String> parameters = List.of(shaker.getName(), shot.getName(), cocktail.getName(),
                levelA.getName(), hand.getName(), levelB.getName());

        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("holding", hand.getName(), shaker.getName()));
        preconditions.addAll(List.of("shaked", shaker.getName()));
        preconditions.addAll(List.of("empty", shot.getName()));
        preconditions.addAll(List.of("clean", shot.getName()));
        preconditions.addAll(List.of("contains", shot.getName(), cocktail.getName()));
        preconditions.addAll(List.of("shakerLevel", shaker.getName(), levelA.getName()));
        preconditions.addAll(List.of("next", levelA.getName(), levelB.getName()));


        List<Task> subtasks = List.of(new pour_shaker_to_shot(cocktail, shot, hand, shaker, levelA, levelB));

        Task taskToDecompose = new DoPourShakerToShot(shaker, shot, cocktail);

        super("pour-shaker-to-shot-action",parameters, preconditions, subtasks, taskToDecompose);

        this.shaker = shaker;
        this.shot = shot;
        this.cocktail = cocktail;
        this.levelA = levelA;
        this.hand = hand;
        this.levelB = levelB;
        this.taskToDecompose = taskToDecompose;

    }
}


