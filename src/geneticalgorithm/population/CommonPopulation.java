package geneticalgorithm.population;

import geneticalgorithm.chromosome.Chromosome;
import geneticalgorithm.chromosomefactory.ChromosomeFactory;
import geneticalgorithm.crossoverbehaviors.CrossoverBehavior;
import geneticalgorithm.processcontroller.GAProcessController;
import geneticalgorithm.scalingfunctions.ScalingFunction;
import basics.tools.NonredundantLinkedList;
import basics.tools.RouletteWheel;

import java.util.ArrayList;

public class CommonPopulation {

    private GAProcessController controller;

    private ArrayList<Chromosome> population;
    private ChromosomeFactory chromosomeFactory;
    private CrossoverBehavior crossoverBehavior;
    private ScalingFunction scalingFunction;

    private NonredundantLinkedList<Chromosome> optimals;

    public CommonPopulation(GAProcessController controller,
                            ChromosomeFactory chromosomeFactory,
                            CrossoverBehavior crossoverBehavior,
                            ScalingFunction scalingFunction) {
        this.controller = controller;
        this.chromosomeFactory = chromosomeFactory;
        this.crossoverBehavior = crossoverBehavior;
        this.scalingFunction = scalingFunction;
    }

    public void initialize() {
        population = new ArrayList<>(controller.getPopulationSize());
        for (int i = 0; i < controller.getPopulationSize(); i++) {
            population.add(chromosomeFactory.generateChromosome());
        }
        optimals = new NonredundantLinkedList<>();
    }

    /**
     * Proportional selection of individuals according to their scaled fitness
     * using the roulete wheel algorithm. Produce a list of index of individuals
     * which are preferable according to a specific criterion.
     *
     * @param N chromosomes size of the selected individuals
     * @return index of individuals selected
     */
    private int[] proportionalSelection(double[] fitnessList, int N) {
        int[] indexes = new int[N];
        RouletteWheel rouletteWheel = new RouletteWheel(fitnessList);
        for (int i = 0; i < N; i++) {
            indexes[i] = rouletteWheel.randomIndex();
        }
        return indexes;
    }

    private void singleGenerationEvolution() throws CloneNotSupportedException {
        double[] objectiveFunctionValues = new double[population.size()];
        for (int i = 0; i < population.size(); i++) {
            objectiveFunctionValues[i] = population.get(i).objectiveFunctionValue();
            if (controller.equalToCurrentOptimal(objectiveFunctionValues[i])) {
                optimals.add((Chromosome) population.get(i).clone());
            } else if (controller.betterThanCurrentOptimal(objectiveFunctionValues[i])) {
                optimals.clear();
                optimals.add((Chromosome) population.get(i).clone());
            }
        }
        double[] fitnessList = scalingFunction.scale(objectiveFunctionValues);

        int geneticPopulationSize = controller.getGeneticPopulationSize();
        int[] indexes = proportionalSelection(fitnessList, geneticPopulationSize);
        ArrayList<Chromosome> geneticPopulation = new ArrayList<>(geneticPopulationSize);
        for (int i = 0; i < geneticPopulationSize; i++) {
            geneticPopulation.add((Chromosome) population.get(indexes[i]).clone());
        }

        for (int i = 0; i < geneticPopulationSize; i = i + 2) {
            Chromosome chromosome1 = geneticPopulation.get(i);
            Chromosome chromosome2 = geneticPopulation.get(i + 1);
            crossoverBehavior.crossover(chromosome1, chromosome2);
        }
        for (Chromosome chromosome : geneticPopulation) {
            chromosome.mutate();
        }
        population = new ArrayList<>(geneticPopulation);
    }

    public void evolve() throws CloneNotSupportedException {
        for (controller.initialize(); !controller.reachStoppingCriterion(); controller.update()) {
            try {
                singleGenerationEvolution();
                System.out.println(population+"\n");
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < population.size(); i++) {
            if (controller.equalToCurrentOptimal(population.get(i).objectiveFunctionValue())) {
                optimals.add((Chromosome) population.get(i).clone());
            } else if (controller.betterThanCurrentOptimal(population.get(i).objectiveFunctionValue())) {
                optimals.clear();
                optimals.add((Chromosome) population.get(i).clone());
            }
        }
    }

    public NonredundantLinkedList<Chromosome> getOptimals() {
        return optimals;
    }
}
