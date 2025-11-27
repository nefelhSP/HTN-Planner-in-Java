package barman;
import barman.Types.*;
import java.util.List;
import java.util.ArrayList;

public class clean_shot extends Action{

    private final Shot shot;
    private final Beverage beverage;
    private final Hand handA;
    private final Hand handB;

    public clean_shot(Shot shot, Beverage beverage,  Hand handA, Hand handB){

        List<String> parameters = List.of(shot.getName(),beverage.getName(), handA.getName(), handB.getName());

        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("holding", handA.getName(), shot.getName()));
        preconditions.addAll(List.of("handEmpty", handB.getName()));
        preconditions.addAll(List.of("empty", shot.getName()));
        preconditions.addAll(List.of("used", shot.getName(), beverage.getName()));

        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("clean", shot.getName()));
        effects.addAll(List.of("not", "used", shot.getName(), beverage.getName()));

        super("clean-shot", parameters, preconditions, effects);
        this.shot = shot;
        this.beverage = beverage;
        this.handA = handA;
        this.handB = handB;

    }
}
