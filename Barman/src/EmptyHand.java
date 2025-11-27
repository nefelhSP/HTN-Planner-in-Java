package barman;
import java.util.List;
import barman.Types.*;

public class EmptyHand extends Method{

    private final Hand hand;
    private final Container container;
    private  Task taskToDecompose;

    public EmptyHand(Hand hand, Container container){

        List<String> parameters = List.of(hand.getName(), container.getName());

        List<String> preconditions = List.of("holding", hand.getName(), container.getName());

        List<Task> subtasks = List.of(new drop(hand, container));

        Task taskToDecompose = new AchieveHandEmpty(hand);
        super("EmptyHand", parameters, preconditions, subtasks, taskToDecompose);

        this.hand = hand;
        this.container = container;
        this.taskToDecompose = taskToDecompose;
    }
}
