package barman;
import barman.Types.*;
import java.util.List;

public class PutDown extends Method{

    private final Types.Hand hand;
    private final Types.Container container;
    private Task taskToDecompose;

    public PutDown(Container container, Hand hand){
        List<String> parameters = List.of(container.getName(), hand.getName());

        List<String> preconditions = List.of("holding", hand.getName(), container.getName());

        List<Task> subtasks = List.of(new drop(hand, container));

        Task taskToDecompose = new AchieveOnTable(container);
        super("PutDown", parameters, preconditions, subtasks, taskToDecompose);

        this.hand = hand;
        this.container = container;
        this.taskToDecompose = taskToDecompose;

    }
}

