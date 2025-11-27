package barman;
import java.util.List;
import barman.Types.*;

public class OnTableNull extends Method{

    private final Types.Container container;
    private  Task taskToDecompose;

    public OnTableNull(Container container){
        List<String> parameters = List.of(container.getName());

        List<String> preconditions = List.of("ontable", container.getName());

        List<Task> subtasks = List.of();

        Task taskToDecompose = new AchieveOnTable(container);
        super("OnTableNull", parameters, preconditions, subtasks, taskToDecompose);

        this.container = container;
        this.taskToDecompose = taskToDecompose;
    }
}

