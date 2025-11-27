package barman;
import barman.Types.*;

import java.util.List;

public class PickUp extends Method{

    private final Hand hand;
    private final Container container;
    private Task taskToDecompose;

    public PickUp(Hand hand, Container container){
        List<String> parameters = List.of(hand.getName(), container.getName());

        List<String> preconditions = List.of("not", "holding", hand.getName(), container.getName());

        List<Task> subtasks = List.of(
                new AchieveHandEmpty(hand),
                new AchieveOnTable(container),
                new grasp(hand, container));

        Task taskToDecompose = new AchieveHolding(hand, container);
        super("PickUp", parameters, preconditions, subtasks, taskToDecompose);

        this.hand = hand;
        this.container = container;
        this.taskToDecompose = taskToDecompose;

    }
}
