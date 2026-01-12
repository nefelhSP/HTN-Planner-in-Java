package barman;

import barman.Types.*;
import java.util.List;

/**
 * AchieveHandEmpty class that implements the Task interface.
 * It represents the task: AchieveHandEmpty
 */
public class AchieveHandEmpty extends Task {

    private final Hand hand;

    /**
     * Constructor for AchieveHandEmpty
     * 
     * @param hand The hand to be emptied
     */
    public AchieveHandEmpty(Hand hand) {
        super("AchieveHandEmpty", List.of(hand.getName()));
        this.hand = hand;
    }

    @Override
    protected void generateMethods() {
        DomainHelper helper = DomainHelper.getHelper();

        List<Container> allContainers = helper.getAllContainers();

        // The method HandEmptyNull requires a hand
        this.addMethod(new HandEmptyNull(hand, this));

        // The method EmptyHand requires a hand and a container
        for (Container container : allContainers) {
            this.addMethod(new EmptyHand(hand, container, this));
        }
    }
}
