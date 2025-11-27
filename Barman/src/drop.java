package barman;
import barman.Types.*;
import java.util.ArrayList;
import java.util.List;


public class drop extends Action{

    private final Hand hand;
    private final Container container;

    public drop(Hand hand, Container container){

        List<String> parameters = List.of(hand.getName(), container.getName());

        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("holding", hand.getName(), container.getName()));

        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("ontable", container.getName()));
        effects.addAll(List.of("handEmpty", hand.getName()));
        effects.addAll(List.of("not", "holding", hand.getName(), container.getName()));

        super("drop", parameters, preconditions, effects);
        this.hand = hand;
        this.container = container;
    }
}
