package geneticalgorithm.crossoverbehaviors;

import geneticalgorithm.chromosome.Chromosome;

import java.util.Random;

public class SinglePointCrossover implements CrossoverBehavior {
    @Override
    public void crossover(Chromosome chromosome1, Chromosome chromosome2) {
        try {
            if (chromosome1.getGene().length != chromosome2.getGene().length) {
                throw new Exception("The lengths of codes are not equal.");
            }
            int L = chromosome1.getGene().length;
            Random r = new Random();
            int cut = 1 + r.nextInt(L - 1);
            for (int i = cut; i < L; i++) {
                int sub = chromosome1.getGene()[i];
                chromosome1.getGene()[i] = chromosome2.getGene()[i];
                chromosome2.getGene()[i] = sub;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
