package simulatedannealing.processcontroller;

public interface TemperatureController {

    double getCurrentTemperature();

    int getRepetitionSchedule();

    boolean reachStoppingCriterion();

    double getChangedTemperature();

}
