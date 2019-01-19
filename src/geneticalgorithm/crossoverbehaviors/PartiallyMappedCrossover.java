package geneticalgorithm.crossoverbehaviors;

import geneticalgorithm.chromosome.Chromosome;
import basics.tools.Random2Cutting;

import java.util.ArrayList;

public class PartiallyMappedCrossover implements CrossoverBehavior {
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
            //System.out.println(cutting1+" "+cutting2);
            ArrayList<Integer> cutPiece1 = new ArrayList(cut2 - cut1);
            ArrayList<Integer> cutPiece2 = new ArrayList(cut2 - cut1);
            for (int i = 0; i < cut2 - cut1; i++) {
                cutPiece1.add(i, chromosome1.getGene()[i + cut1]);
                cutPiece2.add(i, chromosome2.getGene()[i + cut1]);
                chromosome1.getGene()[i + cut1] = cutPiece2.get(i);
                chromosome2.getGene()[i + cut1] = cutPiece1.get(i);
            }
            //按照交叉产生的映射关系将重复的位置换掉，而交换的子串部分保持不变。
            for (int i = 0; i < cut1; i++) {
                int index1 = cutPiece2.indexOf(chromosome1.getGene()[i]);
                while (index1 >= 0) {
                    chromosome1.getGene()[i] = cutPiece1.get(index1);
                    index1 = cutPiece2.indexOf(chromosome1.getGene()[i]);
                }
                int index2 = cutPiece1.indexOf(chromosome2.getGene()[i]);
                while (index2 >= 0) {
                    chromosome2.getGene()[i] = cutPiece2.get(index2);
                    index2 = cutPiece1.indexOf(chromosome2.getGene()[i]);
                }
            }
            for (int i = cut2; i < L; i++) {
                int index1 = cutPiece2.indexOf(chromosome1.getGene()[i]);
                while (index1 >= 0) {
                    chromosome1.getGene()[i] = cutPiece1.get(index1);
                    index1 = cutPiece2.indexOf(chromosome1.getGene()[i]);
                }
                int index2 = cutPiece1.indexOf(chromosome2.getGene()[i]);
                while (index2 >= 0) {
                    chromosome2.getGene()[i] = cutPiece2.get(index2);
                    index2 = cutPiece1.indexOf(chromosome2.getGene()[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
