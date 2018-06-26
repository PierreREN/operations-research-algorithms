/*
 * Copyright 2018 Pierre REN.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package geneticalgorithm.chromosome;

import geneticalgorithm.code.BinaryCode;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pierre REN
 */
public abstract class TemplateBinaryChromosome implements Cloneable {

    protected BinaryCode gene;
    protected double fitness;

    public TemplateBinaryChromosome(int length) {
        gene = new BinaryCode(length);
        gene.random();
    }

    public int geneLength() {
        return gene.length();
    }

    public int[] getGene() {
        return gene.getCode();
    }

    public int sumGene() {
        return gene.sum();
    }

    public double getFitness() {
        return fitness;
    }

    public void mutation(double Pm) {
        gene.mutation(Pm);
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this) {
                return true;
            } else if (obj instanceof TemplateBinaryChromosome) {
                TemplateBinaryChromosome bc = (TemplateBinaryChromosome) obj;
                if (gene.equals(bc.gene) && fitness == bc.fitness) {
                    return true;
                }
            }
        }
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        TemplateBinaryChromosome bc = null;
        try {
            bc = (TemplateBinaryChromosome) super.clone();
            bc.gene = (BinaryCode) gene.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(BinaryCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bc;
    }

    public abstract double calculateFitness();

    public String toString() {
        return "BinaryChromosome Gene: " + " " + gene.toString() + " Fitness: " + fitness;
    }
}
