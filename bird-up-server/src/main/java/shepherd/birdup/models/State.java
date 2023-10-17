package shepherd.birdup.models;

import java.util.Objects;

public class State {
    private int stateId;
    private String stateName;
    private String stateAbbrv;

    public State(int stateId, String stateName, String stateAbbrv) {
        this.stateId = stateId;
        this.stateName = stateName;
        this.stateAbbrv = stateAbbrv;
    }

    public State() {}

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateAbbrv() {
        return stateAbbrv;
    }

    public void setStateAbbrv(String stateAbbrv) {
        this.stateAbbrv = stateAbbrv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return stateId == state.stateId && Objects.equals(stateName, state.stateName) && Objects.equals(stateAbbrv, state.stateAbbrv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateId, stateName, stateAbbrv);
    }

    @Override
    public String toString() {
        return "State{" +
                "stateId=" + stateId +
                ", stateName='" + stateName + '\'' +
                ", stateAbbrv='" + stateAbbrv + '\'' +
                '}';
    }
}

