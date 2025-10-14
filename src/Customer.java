import java.util.Timer;

public class Customer extends Player {

    ProgressBar timeToBeServed;
    String [] reactions;

    public Customer() {
        super(100, 100);
        timeToBeServed = new ProgressBar();
    }

}
