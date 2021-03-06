/*
 * Copyright 2019 Pierre REN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
                newPiece1.add(i, -1);
                newPiece2.add(i, -1);
                gene1.add(i, chromosome1.getGene()[i]);
                gene2.add(i, chromosome2.getGene()[i]);
            }
            int i = 0;
            boolean forward = true;
            while (i >= 0) {
                int j;
                if (forward) {
                    newPiece1.set(i, chromosome1.getGene()[i]);
                    newPiece2.set(i, chromosome2.getGene()[i]);
                    j = gene1.indexOf(chromosome2.getGene()[i]);
                    while (j != i) {
                        newPiece1.set(j, chromosome1.getGene()[j]);
                        newPiece2.set(j, chromosome2.getGene()[j]);
                        j = gene1.indexOf(chromosome2.getGene()[j]);
                    }
                    i = newPiece2.indexOf(-1);
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
                    i = newPiece1.indexOf(-1);
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
