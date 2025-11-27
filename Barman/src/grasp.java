package barman;
import barman.Types.*;
import java.util.ArrayList;
import java.util.List;

public class grasp extends Action{

    private final Hand hand;
    private final Container container;

    public grasp(Hand hand, Container container){

        List<String> parameters = List.of(hand.getName(), container.getName());

        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("ontable", container.getName()));
        preconditions.addAll(List.of("handEmpty", hand.getName()));

        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("holding", hand.getName(), container.getName()));
        effects.addAll(List.of("not", "handEmpty", hand.getName()));
        effects.addAll(List.of("not", "ontable", container.getName()));

        super("grasp", parameters, preconditions, effects);
        this.hand = hand;
        this.container = container;
    }
}