package basics.codefactory;

import basics.code.Code;
import basics.code.instances.SequenceCode;
import basics.mutationbehaviors.MutationBehavior;
import basics.objectivefunctions.ObjectiveFunction;

public class SequenceCodeFactory implements CodeFactory {

    private int codeLength;
    private MutationBehavior mutator;
    private ObjectiveFunction objectiveFunction;

    public SequenceCodeFactory(int codeLength,
                               MutationBehavior mutator,
                               ObjectiveFunction objectiveFunction){
        this.codeLength = codeLength;
        this.mutator = mutator;
        this.objectiveFunction = objectiveFunction;
    }

    @Override
    public Code generateCode() {
        return new SequenceCode(codeLength, mutator, objectiveFunction);
    }

    @Override
    public boolean equals(Object object) {
        if (object != null) {
            if (object == this) {
                return true;
            } else if (object instanceof SequenceCodeFactory) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}