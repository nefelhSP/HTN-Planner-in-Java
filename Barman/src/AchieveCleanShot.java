package barman;
import java.util.List;
import barman.Types.*;

public class AchieveCleanShot extends Task{

    private final Shot shot;

    public AchieveCleanShot(Shot shot) {

        super("AchieveCleanShot", List.of(shot.getName()));
        this.shot = shot;

        DomainHelper helper = DomainHelper.getHelper();

        Beverage beverage = helper.getDefaultObject(Beverage.class);
        Hand handA = helper.getDefaultObject(Hand.class);
        Hand handB = helper.getDefaultObject(Hand.class);

        this.addMethod(new CleanFullShot(shot, handA, beverage, handB));
        this.addMethod(new CleanEmptyShot(shot, handA, beverage, handB));
        this.addMethod(new CleanShotNull(shot));
    }

}
