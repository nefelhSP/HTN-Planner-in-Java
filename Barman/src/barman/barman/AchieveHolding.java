package barman;

import barman.Types.*;
import java.util.List;

/**
 * AchieveHolding class that implements the Task interface.
 * It represents the task: AchieveHolding
 */
public class AchieveHolding extends Task {

    private final Container container;
    private final Hand hand;

    /**
     * Constructor for AchieveHolding
     * 
     * @param hand      The hand to be holding
     * @param container The container to be held
     */
    public AchieveHolding(Hand hand, Container container) {
        super("AchieveHolding", List.of(hand.getName(), container.getName()));
        this.hand = hand;
        this.container = container;
    }

    @Override
    protected void generateMethods() {
        // The method PickUp requires a hand and a container
        this.addMethod(new PickUp(hand, container, this));
        // The method HoldingNull requires a hand and a container
        this.addMethod(new HoldingNull(hand, container, this));
    }
}
