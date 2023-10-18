package shepherd.birdup;

import shepherd.birdup.models.*;

public class TestHelper {
    public TestHelper () {}

    public Location createLocation(int locationId) {
        State state = new State(1, "Alabama", "AL");
        return new Location(locationId, "Birmingham", state, 11111);
    }

    public Like createLike(int postId) {
        Profile liker = new Profile();
        liker.setAppUserId(1);
        liker.setFirstName("Jane");
        liker.setLastName("Doe");
        liker.setBio("I like birds.");
        liker.setUsername("jane@doe.com");

        return new Like(liker, postId);
    }



}
