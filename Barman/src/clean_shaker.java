package barman;
import barman.Types.*;
import java.util.ArrayList;
import java.util.List;

public class clean_shaker extends Action{

    private final Shaker shaker;
    private final Hand handA;
    private final Hand handB;

    public clean_shaker(Shaker shaker, Hand handA, Hand handB){

        List<String> parameters = List.of(shaker.getName(), handA.getName(), handB.getName());

        List<String> preconditions = new ArrayList<>();
        preconditions.addAll(List.of("holding", handA.getName(), shaker.getName()));
        preconditions.addAll(List.of("empty", shaker.getName()));
        preconditions.addAll(List.of("handEmpty", handB.getName()));

        List<String> effects = new ArrayList<>();
        effects.addAll(List.of("empty", shaker.getName()));


        super("clean-shaker", parameters, preconditions, effects);
        this.shaker = shaker;
        this.handA = handA;
        this.handB = handB;

    }
}

