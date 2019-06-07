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

package geneticalgorithm.chromosomefactory;

import basics.codefactory.CodeFactory;
import geneticalgorithm.chromosome.Chromosome;
import geneticalgorithm.chromosome.ChromosomeUsingCode;

public class ChromosomeFactoryUsingCodeFactory implements ChromosomeFactory {

    private CodeFactory codeFactory;

    public ChromosomeFactoryUsingCodeFactory(CodeFactory codeFactory) {
        this.codeFactory = codeFactory;
    }

    @Override
    public Chromosome generateChromosome() {
        Chromosome chromosome = new ChromosomeUsingCode(codeFactory.generateCode());
        chromosome.initialize();
        return chromosome;
    }
}
