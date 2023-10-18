package shepherd.birdup;

import shepherd.birdup.models.Location;
import shepherd.birdup.models.State;

public class TestHelper {
    public TestHelper () {}
    public Location createLocation(int locationId) {
        State state = new State(1, "Alabama", "AL");
        return new Location(locationId, "Birmingham", state, 11111);
    }


}
