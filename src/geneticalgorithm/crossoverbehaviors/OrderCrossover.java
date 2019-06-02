package geneticalgorithm.crossoverbehaviors;

import basics.tools.Random2Cutting;
import geneticalgorithm.chromosome.Chromosome;

import java.util.ArrayList;

public class OrderCrossover implements CrossoverBehavior {
    @Override
    public void crossover(Chromosome chromosome1, Chromosome chromosome2) {
        try {
            if (chromosome1.getGene().length != chromosome2.getGene().length) {
                throw new Exception("The lengths of codes are not equal.");
            }
            int L = chromosome1.getGene().length;
            Random2Cutting r2c = new Random2Cutting(L);
            int cut1 = r2c.cutting1;
            int cut2 = r2c.cutting2;
            //cut2-cut1的新染色体中
            ArrayList<Integer> cutPiece1 = new ArrayList<>(cut2 - cut1);
            ArrayList<Integer> cutPiece2 = new ArrayList<>(cut2 - cut1);
            for (int i = 0; i < cut2 - cut1; i++) {
                cutPiece1.add(i, chromosome1.getGene()[i + cut1]);
                cutPiece2.add(i, chromosome2.getGene()[i + cut1]);
            }
            //从第二个切点后按原顺序去掉已有基因，将未重复的基因按顺序插入
            int j = cut2 == L ? 0 : cut2;
            int k = j;
            //cut2后至尾部
            for (int i = cut2; i < L; i++) {
                while (cutPiece2.contains(chromosome1.getGene()[j])) {
                    j = (j + 1 == L) ? 0 : j + 1;
                }
                chromosome1.getGene()[i] = chromosome1.getGene()[j];
                j = (j + 1 == L) ? 0 : j + 1;
                while (cutPiece1.contains(chromosome2.getGene()[k])) {
                    k = (k + 1 == L) ? 0 : k + 1;
                }
                chromosome2.getGene()[i] = chromosome2.getGene()[k];
                k = (k + 1 == L) ? 0 : k + 1;
            }
            //头部至cut1
            for (int i = 0; i <= cut1; i++) {
                while (cutPiece2.contains(chromosome1.getGene()[j])) {
                    j = (j + 1 == L) ? 0 : j + 1;
                }
                chromosome1.getGene()[i] = chromosome1.getGene()[j];
                j = (j + 1 == L) ? 0 : j + 1;
                while (cutPiece1.contains(chromosome2.getGene()[k])) {
                    k = (k + 1 == L) ? 0 : k + 1;
                }
                chromosome2.getGene()[i] = chromosome2.getGene()[k];
                k = (k + 1 == L) ? 0 : k + 1;
            }
            //补全交叉位置上的基因
            for (int i = 0; i < cut2 - cut1; i++) {
                chromosome1.getGene()[i + cut1] = cutPiece2.get(i);
                chromosome2.getGene()[i + cut1] = cutPiece1.get(i);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
