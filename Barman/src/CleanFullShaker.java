package barman;
import java.util.List;
import barman.Types.*;

public class CleanFullShaker extends Method {

    private final Shaker shaker;
    private final Level levelA;
    private final Cocktail cocktail;
    private final Hand handA;
    private final Hand handB;
    private final Level levelB;
    private final Task taskToDecompose;

    public CleanFullShaker(Shaker shaker, Level levelA, Cocktail cocktail, Hand handA, Hand handB, Level levelB) {
        List<String> parameters = List.of(shaker.getName(), levelA.getName(), cocktail.getName(), handA.getName(), handB.getName(), levelB.getName());

        List<String> preconditions = List.of(
                "contains", shaker.getName(), cocktail.getName(),
                "shaked", shaker.getName(),
                "shakerEmptyLevel", shaker.getName(), levelA.getName(),
                "shakerLevel", shaker.getName(), levelB.getName(),
                "not", "=", handB.getName(), handA.getName()
        );

        List<Task> subtasks = List.of(
                new AchieveHolding(handB, shaker),
                new empty_shaker(handB, shaker, cocktail, levelB, levelA),
                new AchieveHandEmpty(handA),
                new clean_shaker(shaker, handB, handA)
        );

        Task taskToDecompose = new AchieveCleanShaker(shaker);
        super("CleanFullShaker", parameters, preconditions, subtasks, taskToDecompose);

        this.shaker = shaker;
        this.levelA = levelA;
        this.cocktail = cocktail;
        this.handA = handA;
        this.handB = handB;
        this.levelB = levelB;
        this.taskToDecompose = taskToDecompose;
    }
}
