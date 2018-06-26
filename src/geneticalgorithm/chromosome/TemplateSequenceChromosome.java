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

import geneticalgorithm.code.SequenceCode;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pierre REN
 */
public abstract class TemplateSequenceChromosome implements Cloneable {

    protected SequenceCode gene;
    protected double fitness;

    public TemplateSequenceChromosome(int length) {
        gene = new SequenceCode(length);
        gene.random();
    }

    public boolean testSequence() {
        return gene.testSequence();
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

    public int[] getSequenceIndex() {
        int[] sequenceIndex = gene.getCode().clone();
        for (int i = 0; i < sequenceIndex.length; i++) {
            sequenceIndex[i]--;
        }
        return sequenceIndex;
    }

    public double getFitness() {
        return fitness;
    }

    public void shiftMutation(double Pm) {
        gene.shiftMutation(Pm);
    }

    public void transpositionMutation(double Pm) {
        gene.transpositionMutation(Pm);
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this) {
                return true;
            } else if (obj instanceof TemplateSequenceChromosome) {
                TemplateSequenceChromosome sc = (TemplateSequenceChromosome) obj;
                if (gene.equals(sc.gene) && fitness == sc.fitness) {
                    return true;
                }
            }
        }
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        TemplateSequenceChromosome sc = null;
        try {
            sc = (TemplateSequenceChromosome) super.clone();
            sc.gene = (SequenceCode) gene.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(SequenceCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sc;
    }

    public abstract double calculateFitness();

    public String toString() {
        return "SequenceChromosome Gene: " + " " + gene.toString() + " Fitness: " + fitness;
    }
}
