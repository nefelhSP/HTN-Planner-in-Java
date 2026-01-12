package barman;

import java.util.List;
import barman.Types.*;

/**
 * AchieveOnTable class that implements the Task interface.
 * It represents the task: AchieveOnTable
 */
public class AchieveOnTable extends Task {

    private final Container container;

    /**
     * Constructor for AchieveOnTable
     * 
     * @param container The container to be put on the table
     */
    public AchieveOnTable(Container container) {
        super("AchieveOnTable", List.of(container.getName()));
        this.container = container;
    }

    @Override
    protected void generateMethods() {
        DomainHelper helper = DomainHelper.getHelper();
        List<Hand> allHands = helper.getAllHands();

        // The method PutDown requires a container and a hand
        for (Hand hand : allHands) {
            this.addMethod(new PutDown(container, hand, this));
        }

        // The method OnTableNull requires a container
        this.addMethod(new OnTableNull(container, this));
    }
}
