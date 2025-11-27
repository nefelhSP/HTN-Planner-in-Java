package barman;
import java.util.ArrayList;
import java.util.List;
import barman.Types.*;

public class CleanFullShot extends Method{

    private final Shot shot;
    private final Hand handA;
    private final Beverage beverage;
    private final Hand handB;
    private final Task taskToDecompose;

    public CleanFullShot(Shot shot,Hand handA, Beverage beverage,Hand handB){

        List<String> parameters = List.of(shot.getName(), handA.getName(),
                                            beverage.getName(), handB.getName());

        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("contains", shot.getName(), beverage.getName()));
        if (!handA.getName().equals(handB.getName())) {
            preconditions.addAll(List.of("not_equal", handA.getName(), handB.getName()));
        } else {
            System.err.println("Error found, cant have two of the same hand");
        }

        List<Task> subtasks = List.of(
                new AchieveHolding (handB, shot),
                new empty_shot(handB, shot, beverage),
                new AchieveHandEmpty(handA),
                new clean_shot(shot, beverage, handB, handA));

        Task taskToDecompose = new AchieveCleanShot  (shot);
        super("CleanFullShot", parameters, preconditions, subtasks, taskToDecompose);

        this.shot = shot;
        this.handA = handA;
        this.beverage = beverage;
        this.handB = handB;
        this.taskToDecompose = taskToDecompose;

    }

}
