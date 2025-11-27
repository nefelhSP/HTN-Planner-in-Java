package barman;
import barman.Types.*;
import java.util.List;

public class AchieveCleanShaker extends Task {

    private final Shaker shaker;

    public AchieveCleanShaker(Shaker shaker) {

        super("AchieveCleanShaker", List.of(shaker.getName()));
        this.shaker = shaker;

        DomainHelper helper = DomainHelper.getHelper();

        Hand handA = helper.getDefaultObject(Hand.class);
        Hand handB = helper.getDefaultObject(Hand.class);
        Level levelA = helper.getDefaultObject(Level.class);
        Level levelB = helper.getDefaultObject(Level.class);
        Cocktail cocktail = helper.getDefaultObject(Cocktail.class);

        this.addMethod(new CleanEmptyShaker(shaker, handA, handB));
        this.addMethod(new CleanFullShaker(shaker, levelA, cocktail, handA,handB, levelB));
        this.addMethod(new CleanShakerNull(shaker));
    }
}
