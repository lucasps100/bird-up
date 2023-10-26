import { getProfileByUsername } from "../services/profileAPI";
import { useContext, useEffect, useState } from "react";
import AuthContext from "../context/AuthContext";
import { useNavigate, useParams } from "react-router";
import PostDeck from "./PostDeck";

export default function Profile() {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();
  const [profile, setProfile] = useState();
  const { username } = useParams();

  useEffect(() => {
    if (username) {
      getProfileByUsername(username)
        .then(setProfile)
        .then(console.log(profile));
    }
  }, [username]);

  return (
    <div className="profile container">
      {profile && (
        <>
          <div className="row">
            <div className="col">
              <h3>
                {profile.firstName} {profile.lastName}
              </h3>
              <p>Username: {profile.username}</p>
              <p>Followers: {profile.followers.length}</p>
              <p>Following: {profile.followees.length}</p>
            </div>
          </div>
          <PostDeck posts={profile.posts} />
        </>
      )}
    </div>
  );
}
