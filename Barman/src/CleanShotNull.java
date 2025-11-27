package barman;
import java.util.List;
import barman.Types.*;
public class CleanShotNull extends Method {

    private final Shot shot;
    private  Task taskToDecompose;

    public CleanShotNull(Shot shot) {

        List<String> parameters = List.of(shot.getName());
        List<String> preconditions = List.of("clean", shot.getName());
        List<Task> subtasks = List.of();

        Task taskToDecompose = new AchieveCleanShot(shot);

        super("CleanShotNull", parameters, preconditions, subtasks, taskToDecompose);

        this.shot = shot;
        this.taskToDecompose = taskToDecompose;
    }

}


