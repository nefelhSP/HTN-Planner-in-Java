package barman;
import barman.Types.*;
import java.util.List;

public class AchieveHolding extends Task{

    private final Container container;
    private final Hand hand;

    public AchieveHolding(Hand hand, Container container) {

        super("AchieveHolding", List.of(hand.getName(), container.getName()));
        this.hand = hand;
        this.container = container;

        this.addMethod(new PickUp(hand, container));
        this.addMethod(new HoldingNull(hand, container));
    }
}
