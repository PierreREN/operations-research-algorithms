package geneticalgorithm.solver;

import basics.codefactory.CodeFactory;
import basics.codefactory.IntegerCodeFactory;
import basics.mutationbehaviors.MutationBehavior;
import basics.mutationbehaviors.TransposeMutation;
import basics.objectivefunctions.ObjectiveFunction;
import basics.tools.Ranges;
import geneticalgorithm.chromosomefactory.ChromosomeFactory;
import geneticalgorithm.chromosomefactory.ChromosomeFactoryUsingCodeFactory;
import geneticalgorithm.crossoverbehaviors.CrossoverBehavior;
import geneticalgorithm.crossoverbehaviors.DoublePointCrossover;
import geneticalgorithm.population.CommonPopulation;
import geneticalgorithm.processcontroller.GAProcessController;
import geneticalgorithm.scalingfunctions.LinearScaling;
import geneticalgorithm.scalingfunctions.ScalingFunction;

public class IntegerSolver {

    private int maxIteration;
    private int populationSize;
    private int geneticPopulationSize;
    private Ranges ranges;

    private MutationBehavior mutator;
    private CrossoverBehavior crossover;
    private ScalingFunction scalingFunction;
    private ObjectiveFunction objectiveFunction;

    private CommonPopulation population;

    public void initialize(int maxIteration,
                           int populationSize,
                           int geneticPopulationSize,
                           Ranges ranges,
                           MutationBehavior mutator,
                           CrossoverBehavior crossover,
                           ScalingFunction scalingFunction,
                           ObjectiveFunction objectiveFunction
    ){
        this.maxIteration = maxIteration;
        this.populationSize = populationSize;
        this.geneticPopulationSize = geneticPopulationSize;
        this.ranges = ranges;
        this.mutator = mutator;
        this.crossover = crossover;
        this.scalingFunction = scalingFunction;
        this.objectiveFunction = objectiveFunction;

        GAProcessController controller = new GAProcessController(
                this.maxIteration,
                this.populationSize,
                this.geneticPopulationSize);
        CodeFactory codeFactory = new IntegerCodeFactory(this.ranges,
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

    public void run(){
        try {
            population.evolve();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void showResult(){
        System.out.println(population.getOptimals());
    }

    public static void main(String[] args) {

        Ranges ranges  = new Ranges(4, 1, 4);

        IntegerSolver integerSolver = new IntegerSolver();
        integerSolver.initialize(50,
                10,
                10,
                ranges,
                new TransposeMutation(),
                new DoublePointCrossover(),
                new LinearScaling(),
                new IntegerEvaluator());

        integerSolver.run();
        integerSolver.showResult();
    }
}

class IntegerEvaluator implements ObjectiveFunction {

    @Override
    public double evaluate(int[] code) {
        double value = 0;
        int[] processingTime = {8, 18, 5, 15};
        int n = code.length;
        for (int i = 0; i < code.length; i++) {
            value += processingTime[code[i] - 1] * n--;
        }
        return value;
    }
}