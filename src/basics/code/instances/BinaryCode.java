package basics.code.instances;

import basics.code.Code;
import basics.code.CommonCode;
import basics.mutationbehaviors.MutationBehavior;
import basics.objectivefunctions.ObjectiveFunction;

public class BinaryCode extends CommonCode implements Code {

    public BinaryCode(int length, MutationBehavior mutator,
                      ObjectiveFunction objectiveFunction) {
        super(length);
        this.mutator = mutator;
        this.objectiveFunction = objectiveFunction;
    }

    @Override
    public void initialize() {
        random();
    }

    private void random() {
        for (int i = 0; i < array.length; i++) {
            if (Math.random() > 0.5) {
                array[i] = 1;
            } else {
                array[i] = 0;
            }
        }
    }
}
