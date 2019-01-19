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
                if (c.gene.equals(gene)) {
                    return true;
                }
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
        return this.getClass().toString()
                + "\tGene: " + gene.toString()
                + "\tObjective Function Value: "
                + gene.objectiveFunctionValue();
    }
}
