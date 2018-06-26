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

import geneticalgorithm.code.IntegerCode;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pierre REN
 */
public abstract class TemplateIntegerChromosome implements Cloneable {

    protected IntegerCode gene;
    protected double fitness;

    public TemplateIntegerChromosome(int length, int Nmin, int Nmax) {
        gene = new IntegerCode(length);
        gene.setBound(Nmin, Nmax);
        gene.random();
    }

    public TemplateIntegerChromosome(int[] Nmax) {
        gene = new IntegerCode(Nmax.length);
        gene.setBound(Nmax);
        gene.random();
    }

    public int geneLength() {
        return gene.length();
    }

    public int sumGene() {
        return gene.sum();
    }

    public int[] getGene() {
        return gene.getCode();
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
            } else if (obj instanceof TemplateIntegerChromosome) {
                TemplateIntegerChromosome ic = (TemplateIntegerChromosome) obj;
                if (gene.equals(ic.gene) && fitness == ic.fitness) {
                    return true;
                }
            }
        }
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        TemplateIntegerChromosome bc = null;
        try {
            bc = (TemplateIntegerChromosome) super.clone();
            bc.gene = (IntegerCode) gene.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(IntegerCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bc;
    }

    public abstract double calculateFitness();

    public String toString() {
        return "IntegerChromosome Gene: " + " " + gene.toString() + " Fitness: " + fitness;
    }
}
