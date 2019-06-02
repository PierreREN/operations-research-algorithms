package antcolonyalgorithm.antsystemcontroller;

import basics.processcontroller.ProcessController;

import java.util.HashMap;

public class AntsController implements ProcessController {

    private HashMap<String, Integer> integerParameters = new HashMap<>();
    private HashMap<String, Double> doubleParameters = new HashMap<>();

    private int currentIteration;
    private int maxIteration;

    @Override
    public void initialize() {
        currentIteration = 0;
        maxIteration = integerParameters.getOrDefault("maxIteration", 100);
    }

    @Override
    public boolean reachStoppingCriterion() {
        return currentIteration >= maxIteration;
    }

    @Override
    public void update() {
        currentIteration++;
    }

    public void setIntegerParameters(String key, int value) {
        integerParameters.put(key, value);
    }

    public void setDoubleParameters(String key, double value) {
        doubleParameters.put(key, value);
    }

    public int getIntegerParameter(String string) {
        return integerParameters.getOrDefault(string, 0);
    }

    public double getDoubleParameter(String string) {
        return doubleParameters.getOrDefault(string, 0.0);
    }

    public int getCurrentIteration() {
        return currentIteration;
    }
}
