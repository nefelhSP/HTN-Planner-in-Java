package barman;
import java.util.List;
import barman.Types.*;

public class HandEmptyNull extends Method{

    private final Hand handA;
    private final Hand handB;
    private  Task taskToDecompose;

    public HandEmptyNull(Hand handA, Hand handB){

        List<String> parameters = List.of(handA.getName(), handB.getName());

        List<String> preconditions = List.of("handEmpty", handB.getName());

        List<Task> subtasks = List.of();

        Task taskToDecompose = new AchieveHandEmpty(handA);
        super("HandEmptyNull", parameters, preconditions, subtasks, taskToDecompose);

        this.handA = handA;
        this.handB = handB;
        this.taskToDecompose = taskToDecompose;
    }
}
