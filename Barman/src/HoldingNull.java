package barman;
import java.util.List;
import barman.Types.*;

public class HoldingNull extends Method{

    private final Types.Hand hand;
    private final Types.Container container;
    private  Task taskToDecompose;

    public HoldingNull(Hand hand, Container container){
        List<String> parameters = List.of(hand.getName(), container.getName());

        List<String> preconditions = List.of("holding", hand.getName(), container.getName());

        List<Task> subtasks = List.of();

        Task taskToDecompose = new AchieveHolding(hand, container);
        super("HoldingNull", parameters, preconditions, subtasks, taskToDecompose);

        this.hand = hand;
        this.container = container;
        this.taskToDecompose = taskToDecompose;
    }
}

