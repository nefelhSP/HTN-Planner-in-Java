package barman;
import barman.Types.*;

import java.util.List;

public class CleanShakerNull extends Method {
    private final Shaker shaker;
    private  Task taskToDecompose;

    public CleanShakerNull(Shaker shaker) {

        List<String> parameters = List.of(shaker.getName());
        List<String> preconditions = List.of("clean", shaker.getName());
        List<Task> subtasks = List.of();

        Task taskToDecompose = new AchieveCleanShaker(shaker);

        super("CleanShakerNull", parameters, preconditions, subtasks, taskToDecompose);

        this.shaker = shaker;
        this.taskToDecompose = taskToDecompose;
    }
}

