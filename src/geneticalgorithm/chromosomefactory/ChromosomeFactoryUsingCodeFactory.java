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
