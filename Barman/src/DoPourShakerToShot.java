package barman;
import java.util.List;
import barman.Types.*;

public class DoPourShakerToShot extends Task{

    private final Shaker shaker;
    private final Shot shot;
    private final Cocktail cocktail;

    public DoPourShakerToShot(Shaker shaker, Shot shot, Cocktail cocktail) {

        super("DoPourShakerToShot", List.of(shaker.getName(), shot.getName(), cocktail.getName()));
        this.shaker = shaker;
        this.shot = shot;
        this.cocktail = cocktail;

        DomainHelper helper = DomainHelper.getHelper();

        Hand hand = helper.getDefaultObject(Hand.class);
        Level levelA = helper.getDefaultObject(Level.class);
        Level levelB = helper.getDefaultObject(Level.class);

        this.addMethod(new pour_shaker_to_shot_action(shaker, shot, cocktail,levelA, hand, levelB));

    }
}
