package barman;
import barman.Types.*;
import java.util.ArrayList;
import java.util.List;

public class empty_shot extends Action{

    private final Hand hand;
    private final Shot shot;
    private final Beverage beverage;

    public empty_shot(Hand hand, Shot shot, Beverage beverage){

        List<String> parameters = List.of(hand.getName(), shot.getName(), beverage.getName());

        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("holding", hand.getName(), shot.getName()));
        preconditions.addAll(List.of("contains", shot.getName(), beverage.getName()));

        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("empty", shot.getName()));
        effects.addAll(List.of("not", "contains", shot.getName(), beverage.getName()));

        super("empty-shot", parameters, preconditions, effects);
        this.hand = hand;
        this.shot = shot;
        this.beverage = beverage;
    }
}
