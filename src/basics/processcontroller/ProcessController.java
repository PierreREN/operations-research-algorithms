package basics.processcontroller;

public interface ProcessController {

    void initialize();

    boolean reachStoppingCriterion();

    void update();

}
