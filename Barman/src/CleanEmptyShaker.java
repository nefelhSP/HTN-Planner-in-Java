package barman;
import barman.Types.*;

import java.util.ArrayList;
import java.util.List;

public class CleanEmptyShaker extends Method{

    private final Shaker shaker;
    private final Hand handA;
    private final Hand handB;
    private final Task taskToDecompose;

    public CleanEmptyShaker(Shaker shaker, Hand handA, Hand handB){

        DomainHelper helper = DomainHelper.getHelper();

         handA = helper.getDefaultObject(Hand.class);
         handB = helper.getDefaultObject(Hand.class);

        List<String> parameters = List.of(shaker.getName(), handA.getName(), handB.getName());

        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("not","clean", shaker.getName()));
        preconditions.addAll(List.of("empty", shaker.getName()));
        if (!handA.getName().equals(handB.getName())) {
            preconditions.addAll(List.of("not_equal", handA.getName(), handB.getName()));
        } else {
            System.err.println("Error found, cant have two of the same hand");
        }

        List<Task> subtasks = List.of(
                new AchieveHolding (handB, shaker),
                new AchieveHandEmpty(handA),
                new clean_shaker(shaker, handB, handA));

        Task taskToDecompose = new AchieveCleanShaker (shaker);
        super("CleanEmptyShaker", parameters, preconditions, subtasks, taskToDecompose);

        this.shaker = shaker;
        this.handA = handA;
        this.handB = handB;
        this.taskToDecompose = taskToDecompose;
    }
}

