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
