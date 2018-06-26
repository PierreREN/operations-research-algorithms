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
import geneticalgorithm.code.IntegerCode;
import geneticalgorithm.code.SequenceCode;

/**
 *
 * @author Pierre REN
 */
public class Crossover {

    public static void singlePoint(TemplateBinaryChromosome bc1, TemplateBinaryChromosome bc2) {
        BinaryCode.singlePointCrossover(bc1.gene, bc2.gene);
    }

    public static void doublePoint(TemplateBinaryChromosome bc1, TemplateBinaryChromosome bc2) {
        BinaryCode.doublePointCrossover(bc1.gene, bc2.gene);
    }

    public static void singlePoint(TemplateIntegerChromosome ic1, TemplateIntegerChromosome ic2) {
        IntegerCode.singlePointCrossover(ic1.gene, ic2.gene);
    }

    public static void doublePoint(TemplateIntegerChromosome ic1, TemplateIntegerChromosome ic2) {
        IntegerCode.doublePointCrossover(ic1.gene, ic2.gene);
    }

    public static void partiallyMapped(TemplateSequenceChromosome sc1, TemplateSequenceChromosome sc2) {
        SequenceCode.partiallyMappedCrossover(sc1.gene, sc2.gene);
    }

    public static void order(TemplateSequenceChromosome sc1, TemplateSequenceChromosome sc2) {
        SequenceCode.orderCrossover(sc1.gene, sc2.gene);
    }

    public static void cycle(TemplateSequenceChromosome sc1, TemplateSequenceChromosome sc2) {
        SequenceCode.cycleCrossover(sc1.gene, sc2.gene);
    }
}
