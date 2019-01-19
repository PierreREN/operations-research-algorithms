package geneticalgorithm.chromosome;

public interface Chromosome {

    void initialize();

    double objectiveFunctionValue();

    int[] getGene();

    void mutate();

    boolean equals(Object object);

    Object clone() throws CloneNotSupportedException;

    String toString();

}
