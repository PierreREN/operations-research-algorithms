package geneticalgorithm.crossoverbehaviors;

import geneticalgorithm.chromosome.Chromosome;
import basics.tools.Random2Cutting;

public class DoublePointCrossover implements CrossoverBehavior {
    @Override
    public void crossover(Chromosome chromosome1, Chromosome chromosome2) {
        try {
            if (chromosome1.getGene().length != chromosome2.getGene().length) {
                throw new Exception("The lengths of codes are not equal.");
            }
            int L = chromosome1.getGene().length;
            if (L == 1) {
                int sub = chromosome1.getGene()[0];
                chromosome1.getGene()[0] = chromosome2.getGene()[0];
                chromosome2.getGene()[0] = sub;
            } else {
                Random2Cutting r2c = new Random2Cutting(L);
                int cut1 = r2c.cutting1;
                int cut2 = r2c.cutting2;
                for (int i = cut1; i < cut2; i++) {
                    int sub = chromosome1.getGene()[i];
                    chromosome1.getGene()[i] = chromosome2.getGene()[i];
                    chromosome2.getGene()[i] = sub;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
