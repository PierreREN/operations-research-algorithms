package simulatedannealing.state;

public interface State extends Cloneable {

    void initialize();

    State getNextState(double T);

    double getEnergy();

    boolean equals(Object object);

    Object clone() throws CloneNotSupportedException;

}
