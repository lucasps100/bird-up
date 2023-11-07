import { getProfileByUsername, deleteProfile } from "../services/profileAPI";
import { useContext, useEffect, useState } from "react";
import AuthContext from "../context/AuthContext";
import { useNavigate, useParams } from "react-router";
import PostDeck from "./PostDeck";
import { deleteFollow, createFollow } from "../services/followAPI";
import "react-confirm-alert/src/react-confirm-alert.css";
import { confirmAlert } from "react-confirm-alert";

export default function Profile() {
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();
  const [profile, setProfile] = useState();
  const { username } = useParams();
  const [clicked, setClicked] = useState(0);
  const delay = (ms) => new Promise((res) => setTimeout(res, ms));

  useEffect(
    () => {
      if (username) {
        getProfileByUsername(username)
          .then(setProfile)
          .then(() => console.log("Profile loaded: " + profile));
      }
    },
    profile ? [clicked, profile.posts] : [clicked]
  );

  async function unfollow(followeeId) {
    if (user) {
      deleteFollow(followeeId);
      await delay(100);
      setClicked(clicked + 1);
    }
  }

  async function follow(followeeId) {
    if (user) {
      createFollow(followeeId);
      await delay(100);
      setClicked(clicked + 1);
    }
  }

  const deleteDialog = ({ onClose }) => {
    const handleClickedNo = () => {
      onClose();
    };
    const handleClickedYes = () => {
      deleteProfile();
      logout();
      navigate("/");

      // msgWithTimeout(`Profile deleted.`);
      onClose();
    };

    return (
      <div className="add-dialog">
        <h3>Delete Profile</h3>
        <div className="add-dialog-buttons">
          <button onClick={handleClickedNo}>No</button>
          <button onClick={handleClickedYes}>Yes, delete my profile.</button>
        </div>
      </div>
    );
  };

  return (
    <div className="profile background">
      {profile && (
        <>
          <div className="row">
            <div className="col alert bg-success">
              <h3>
                {profile.firstName} {profile.lastName}
              </h3>
              <p>About Me: {profile.bio ? profile.bio : profile.bio}</p>
              <p>Username: {profile.username}</p>

              <p>
                Followers: {profile.followers ? profile.followers.length : 0}
              </p>
              <p>
                Following: {profile.followees ? profile.followees.length : 0}
              </p>
              {user.username != profile.username ? (
                user &&
                (profile.followers
                  .map((f) => f.username)
                  .includes(user.username) ? (
                  <button onClick={() => unfollow(profile.appUserId)}>
                    Unfollow
                  </button>
                ) : (
                  <button onClick={() => follow(profile.appUserId)}>
                    Follow
                  </button>
                ))
              ) : (
                <>
                  <button
                    className="btn btn-secondary"
                    onClick={() => navigate("/update")}
                  >
                    Edit Profile
                  </button>
                  <button
                    className="btn btn-danger"
                    onClick={() => confirmAlert({ customUI: deleteDialog })}
                  >
                    Delete Profile
                  </button>
                </>
              )}
            </div>
          </div>
          <PostDeck posts={profile.posts} />
        </>
      )}
    </div>
  );
}
