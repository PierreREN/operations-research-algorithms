package geneticalgorithm.crossoverbehaviors;

import geneticalgorithm.chromosome.Chromosome;

import java.util.ArrayList;

public class CycleCrossover implements CrossoverBehavior {
    @Override
    public void crossover(Chromosome chromosome1, Chromosome chromosome2) {
        try {
            if (chromosome1.getGene().length != chromosome2.getGene().length) {
                throw new Exception("The lengths of codes are not equal.");
            }
            int length = chromosome1.getGene().length;
            ArrayList<Integer> newPiece1 = new ArrayList<>(length);
            ArrayList<Integer> newPiece2 = new ArrayList<>(length);
            ArrayList<Integer> gene1 = new ArrayList<>(length);
            ArrayList<Integer> gene2 = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                newPiece1.add(i, 0);
                newPiece2.add(i, 0);
                gene1.add(i, chromosome1.getGene()[i]);
                gene2.add(i, chromosome2.getGene()[i]);
            }
            int i = 0;
            boolean forward = true;
            while (i >= 0) {
                int j = 0;
                if (forward) {
                    newPiece1.set(i, chromosome1.getGene()[i]);
                    newPiece2.set(i, chromosome2.getGene()[i]);
                    j = gene1.indexOf(chromosome2.getGene()[i]);
                    while (j != i) {
                        newPiece1.set(j, chromosome1.getGene()[j]);
                        newPiece2.set(j, chromosome2.getGene()[j]);
                        j = gene1.indexOf(chromosome2.getGene()[j]);
                    }
                    i = newPiece2.indexOf(0);
                    forward = false;
                } else {
                    newPiece1.set(i, chromosome2.getGene()[i]);
                    newPiece2.set(i, chromosome1.getGene()[i]);
                    j = gene2.indexOf(chromosome1.getGene()[i]);
                    while (j != i) {
                        newPiece1.set(j, chromosome2.getGene()[j]);
                        newPiece2.set(j, chromosome1.getGene()[j]);
                        j = gene2.indexOf(chromosome1.getGene()[j]);
                    }
                    i = newPiece1.indexOf(0);
                    forward = true;
                }
            }
            for (int k = 0; k < length; k++) {
                chromosome1.getGene()[k] = newPiece1.get(k);
                chromosome2.getGene()[k] = newPiece2.get(k);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
