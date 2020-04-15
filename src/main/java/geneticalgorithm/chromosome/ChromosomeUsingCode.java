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

package geneticalgorithm.chromosome;

import basics.code.Code;

public class ChromosomeUsingCode implements Chromosome, Cloneable {

    private Code gene;

    public ChromosomeUsingCode(Code code) {
        gene = code;
    }

    @Override
    public void initialize() {
        gene.initialize();
    }

    @Override
    public double objectiveFunctionValue() {
        return gene.objectiveFunctionValue();
    }

    @Override
    public int[] getGene() {
        return gene.getArray();
    }

    @Override
    public void mutate() {
        gene.mutate();
    }

    @Override
    public boolean equals(Object object) {
        if (object != null) {
            if (object == this) {
                return true;
            } else if (object instanceof ChromosomeUsingCode) {
                ChromosomeUsingCode c = (ChromosomeUsingCode) object;
                return c.gene.equals(gene);
            }
        }
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ChromosomeUsingCode clone = (ChromosomeUsingCode) super.clone();
        clone.gene = (Code) this.gene.clone();
        return clone;
    }

    @Override
    public String toString() {
        return "Gene: " + gene.toString()
                + "\tObjective Function Value: "
                + gene.objectiveFunctionValue();
    }
}
