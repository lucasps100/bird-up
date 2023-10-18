package shepherd.birdup;

import shepherd.birdup.models.*;

import java.time.LocalDateTime;

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

    public Comment createComment(int commentId, int postId) {
        Profile commenter = new Profile();
        commenter.setAppUserId(1);
        commenter.setFirstName("Jane");
        commenter.setLastName("Doe");
        commenter.setBio("I like birds.");
        commenter.setUsername("jane@doe.com");

        return new Comment(commentId, "comment", postId, commenter, LocalDateTime.of(2023, 10, 1, 9, 9, 9));
    }



}
