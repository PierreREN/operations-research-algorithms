package geneticalgorithm.processcontroller;

import basics.processcontroller.ProcessController;

public class GAProcessController implements ProcessController {

    private int currentIteration;
    private int maxIteration;
    private int populationSize;
    private int geneticPopulationSize;
    private double optimalObjectiveFunctionValues;
    private boolean executeSecondaryChoice;

    public GAProcessController(int maxIteration, int populationSize,
                               int geneticPopulationSize) {
        this.maxIteration = maxIteration;
        this.populationSize = populationSize;
        this.geneticPopulationSize = geneticPopulationSize;
        optimalObjectiveFunctionValues = Double.POSITIVE_INFINITY;
    }

    @Override
    public void initialize() {
        currentIteration = 0;
    }

    @Override
    public boolean reachStoppingCriterion() {
        return currentIteration >= maxIteration;
    }

    @Override
    public void update() {
        currentIteration++;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getGeneticPopulationSize() {
        return geneticPopulationSize;
    }

    public boolean equalToCurrentOptimal(double objectiveFunctionValue) {
        return objectiveFunctionValue == optimalObjectiveFunctionValues;
    }

    public boolean betterThanCurrentOptimal(double objectiveFunctionValue) {
        if (objectiveFunctionValue < optimalObjectiveFunctionValues) {
            optimalObjectiveFunctionValues = objectiveFunctionValue;
            return true;
        } else
            return false;
    }
}
