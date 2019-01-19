package geneticalgorithm.crossoverbehaviors;

import geneticalgorithm.chromosome.Chromosome;

public interface CrossoverBehavior {

    void crossover(Chromosome chromosome1, Chromosome chromosome2);

}
