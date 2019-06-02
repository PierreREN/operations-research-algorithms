package geneticalgorithm.solver;

import basics.codefactory.BinaryCodeFactory;
import basics.codefactory.CodeFactory;
import basics.mutationbehaviors.MutationBehavior;
import basics.mutationbehaviors.TransposeMutation;
import basics.objectivefunctions.ObjectiveFunction;
import geneticalgorithm.chromosomefactory.ChromosomeFactory;
import geneticalgorithm.chromosomefactory.ChromosomeFactoryUsingCodeFactory;
import geneticalgorithm.crossoverbehaviors.CrossoverBehavior;
import geneticalgorithm.crossoverbehaviors.DoublePointCrossover;
import geneticalgorithm.population.CommonPopulation;
import geneticalgorithm.processcontroller.GAProcessController;
import geneticalgorithm.scalingfunctions.LinearScaling;
import geneticalgorithm.scalingfunctions.ScalingFunction;

public class BinarySolver {

    private int maxIteration;
    private int populationSize;
    private int geneticPopulationSize;
    private int codeLength;

    private MutationBehavior mutator;
    private CrossoverBehavior crossover;
    private ScalingFunction scalingFunction;
    private ObjectiveFunction objectiveFunction;

    private CommonPopulation population;

    public static void main(String[] args) {
        BinarySolver binarySolver = new BinarySolver();
        binarySolver.initialize(50,
                10,
                10,
                4,
                new TransposeMutation(),
                new DoublePointCrossover(),
                new LinearScaling(),
                new BinaryEvaluator());

        binarySolver.run();
        binarySolver.showResult();
    }

    public void initialize(int maxIteration,
                           int populationSize,
                           int geneticPopulationSize,
                           int codeLength,
                           MutationBehavior mutator,
                           CrossoverBehavior crossover,
                           ScalingFunction scalingFunction,
                           ObjectiveFunction objectiveFunction
    ) {
        this.maxIteration = maxIteration;
        this.populationSize = populationSize;
        this.geneticPopulationSize = geneticPopulationSize;
        this.codeLength = codeLength;
        this.mutator = mutator;
        this.crossover = crossover;
        this.scalingFunction = scalingFunction;
        this.objectiveFunction = objectiveFunction;

        GAProcessController controller = new GAProcessController(
                this.maxIteration,
                this.populationSize,
                this.geneticPopulationSize);
        CodeFactory codeFactory = new BinaryCodeFactory(this.codeLength,
                this.mutator,
                this.objectiveFunction);
        ChromosomeFactory chromosomeFactory =
                new ChromosomeFactoryUsingCodeFactory(codeFactory);

        population = new CommonPopulation(controller,
                chromosomeFactory,
                this.crossover,
                this.scalingFunction);
        population.initialize();
    }

    public void run() {
        try {
            population.evolve();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void showResult() {
        System.out.println(population.getOptimums());
    }
}

class BinaryEvaluator implements ObjectiveFunction {

    @Override
    public double evaluate(int[] code) {
        double value = 0;
        int[] processingTime = {8, 18, 5, 15};
        int n = code.length;
        for (int i = 0; i < code.length; i++) {
            value += processingTime[i] * code[i];
        }
        return value;
    }
}