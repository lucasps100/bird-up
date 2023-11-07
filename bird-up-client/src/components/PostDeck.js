import { useContext, useState } from "react";
import { Link } from "react-router-dom";
import BirdModal from "./BirdModal";
import AuthContext from "../context/AuthContext";
import { createLike, deleteLike } from "../services/likeAPI";
import { confirmAlert } from "react-confirm-alert";
import "react-confirm-alert/src/react-confirm-alert.css";
import { deletePost } from "../services/postsAPI";
import Success from "./Success";

export default function PostDeck({ posts }) {
  const [selectedBird, setSelectedBird] = useState(null);
  const [modalOpen, setModalOpen] = useState(false);
  const [msg, setMsg] = useState();
  const { user } = useContext(AuthContext);
  const delay = (ms) => new Promise((res) => setTimeout(res, ms));

  async function msgWithTimeout(msg) {
    setMsg(msg);
    await delay(10000);
    setMsg("");
  }

  const createDeleteUI = (post) => {
    const deleteDialog = ({ onClose }) => {
      const handleClickedNo = () => {
        onClose();
      };
      const handleClickedYes = () => {
        deletePost(post.postId);
        msgWithTimeout(`Post ${post.postId} deleted.`);
        onClose();
      };

      return (
        <div className="add-dialog">
          <h3>Delete Sighting</h3>
          <p>Sighting: {post.species.speciesShortName}</p>
          <p>Text: {post.postText}</p>
          <p>
            Created At: {post.createdAt.split("T")[0].split("-")[1]}/
            {post.createdAt.split("T")[0].split("-")[2]}/
            {post.createdAt.split("T")[0].split("-")[0]}
          </p>
          <div className="add-dialog-buttons">
            <button onClick={handleClickedNo}>No</button>
            <button onClick={handleClickedYes}>Yes, delete sighting.</button>
          </div>
        </div>
      );
    };
    return deleteDialog;
  };

  const onBirdClick = (birdName) => {
    setModalOpen(true);
    setSelectedBird(birdName);
  };

  const closeModal = () => {
    setModalOpen(false);
    setSelectedBird(null);
  };

  const likePost = (postId) => {
    if (user) {
      createLike(postId);
    }
  };

  const unlikePost = (postId) => {
    if (user) {
      deleteLike(postId);
    }
  };

  return (
    <div className="row justify-content-center">
      <Success msg={msg} />
      <div className="col-5">
        {posts.map((post) => (
          <div className="card bg-dark text-white mb-3" key={post.postId}>
            <div className="card-header d-flex justify-content-between">
              <Link to={`/profile/${post.posterProfile.username}`}>
                {post.posterProfile.username}
              </Link>
              {user && user.username == post.posterProfile.username && (
                <>
                  <button
                    className="btn btn-danger"
                    onClick={() =>
                      confirmAlert({ customUI: createDeleteUI(post) })
                    }
                  >
                    Delete post
                  </button>
                  <Link
                    className="btn btn-secondary"
                    to={`/edit/${post.postId}`}
                  >
                    Edit
                  </Link>
                </>
              )}
            </div>
            <div className="card-body">
              <div className="card-title">
                <strong>
                  Sighting:
                  <button
                    className="card-title btn btn-link"
                    onClick={() => onBirdClick(post.species.speciesShortName)}
                  >
                    {post.species.speciesShortName}{" "}
                  </button>
                </strong>
              </div>
              <p className="card-text">{post.postText}</p>
              <p className="card-text">
                {" "}
                {post.createdAt.split("T")[0].split("-")[1]}/
                {post.createdAt.split("T")[0].split("-")[2]}/
                {post.createdAt.split("T")[0].split("-")[0]}{" "}
                <small class="text-muted"></small>
              </p>
              <div className="d-flex flex-row justify-content-evenly">
                <p>
                  <strong>{post.likes.length}</strong> Likes
                </p>
                {user &&
                  (post.likes
                    .flatMap((l) => l.likerAccount.username)
                    .includes(user.username) ? (
                    <button
                      className="btn btn-primary btn-sm"
                      onClick={() => unlikePost(post.postId)}
                    >
                      Unlike
                    </button>
                  ) : (
                    <button
                      className="btn btn-light btn-sm px-3"
                      onClick={() => likePost(post.postId)}
                    >
                      Like
                    </button>
                  ))}

                {/* <p>
                  <strong>{post.comments.length}</strong> Comments
                </p> 
                 {user && (
                  <button className="btn btn-secondary btn-sm">
                    Add Comment
                  </button>
                )}
              </div>
              <div className="comments col">
                {post.comments.map((comment) => {
                  <p className="card-text">
                    <small>{comment.commenterProfile.username}</small>
                    {comment.commentText}
                  </p>;
                })} */}
              </div>
            </div>
            {/* <CommentForm /> */}
          </div>
        ))}
      </div>

      <div className="col-3">
        {modalOpen && (
          <BirdModal birdName={selectedBird} onClose={closeModal} />
        )}
      </div>
    </div>
  );
}
