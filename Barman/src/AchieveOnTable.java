package barman;
import java.util.List;
import barman.Types.*;

public class AchieveOnTable extends Task{

    private final Container container;

    public AchieveOnTable(Container container) {

        super("AchieveOnTable", List.of(container.getName()));
        this.container = container;

        DomainHelper helper = DomainHelper.getHelper();

        Hand hand = helper.getDefaultObject(Hand.class);

        this.addMethod(new PutDown(container, hand));
        this.addMethod(new OnTableNull(container));
    }
}
