package antcolonyalgorithm.ant;

public interface Ant<Node> {

    void initialize();

    Node getCurrentNode();

    boolean moveToNextNode();

    void leavePheromoneLocally();

    void leavePheromonesGlobally();

}
