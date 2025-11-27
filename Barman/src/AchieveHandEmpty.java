package barman;
import barman.Types.*;
import java.util.List;

public class AchieveHandEmpty extends Task{

    private final Hand handA;

    public AchieveHandEmpty(Hand handA) {

        super("AchieveHandEmpty", List.of(handA.getName()));
        this.handA = handA;

        DomainHelper helper = DomainHelper.getHelper();

        Container container = helper.getDefaultObject(Container.class);
        Hand handB = helper.getDefaultObject(Hand.class);

        this.addMethod(new EmptyHand(handA, container));
        this.addMethod(new HandEmptyNull(handA, handB)); //Πρόβλημα του domain - αποστολή email
    }
}
