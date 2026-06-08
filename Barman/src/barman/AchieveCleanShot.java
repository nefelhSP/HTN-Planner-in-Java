package barman;

import java.util.List;
import barman.Types.*;

/**
 * AchieveCleanShot class that implements the Task interface.
 * It represents the task: AchieveCleanShot
 */
public class AchieveCleanShot extends Task {

    private final Shot shot;

    /**
     * Constructor for AchieveCleanShot
     * @param shot The shot to be cleaned
     */
    public AchieveCleanShot(Shot shot) {
        super("AchieveCleanShot", List.of(shot.getName()));
        this.shot = shot;
    }

    // Generates the methods for the task
    @Override
    protected void generateMethods() {
        DomainHelper helper = DomainHelper.getHelper();

        List<Hand> allHands = helper.getAllHands();
        List<Beverage> allBeverages = helper.getAllItems(Beverage.class);

        // The method CleanShotNull checks if the shot is already clean and requires just a shot
        this.addMethod(new CleanShotNull(shot, this));

        // The method CleanFullShot requires two hands, a beverage and a shot
        for (Hand hand1 : allHands) {
            for (Hand hand2 : allHands) {
                if (hand1.equals(hand2))
                    continue; //Making sure I have different hands

                for (Beverage beverage : allBeverages) {
                    this.addMethod(new CleanFullShot(shot, hand1, beverage, hand2, this));
                }
            }
        }

        // The method CleanEmptyShot requires two hands, a beverage and a shot
        for (Hand hand1 : allHands) {
            for (Hand hand2 : allHands) {
                if (hand1.equals(hand2))
                    continue; //Making sure I have different hands

                for (Beverage beverage : allBeverages) {
                    this.addMethod(new CleanEmptyShot(shot, hand1, beverage, hand2, this));
                }
            }
        }
    }
}
