package simulatedannealing.annealingsimulator;

import simulatedannealing.state.State;

public interface AnnealingSimulator {

    void setState(State initialState);

    void initialize();

    void anneal();

}
