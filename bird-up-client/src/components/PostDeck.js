import { useContext, useState } from "react";
import { Link } from "react-router-dom";
import BirdModal from "./BirdModal";
import AuthContext from "../context/AuthContext";
import { createLike, deleteLike } from "../services/likeAPI";

export default function PostDeck({ posts }) {
  const [selectedBird, setSelectedBird] = useState(null);
  const [modalOpen, setModalOpen] = useState(false);

  const { user } = useContext(AuthContext);

  const onBirdClick = (birdName) => {
    setModalOpen(true);
    setSelectedBird(birdName);
  };

  const closeModal = () => {
    setModalOpen(false);
    setSelectedBird(null);
  };

  const likePost = (postId) => {
    createLike(postId);
  };

  const unlikePost = (postId) => {
    deleteLike(postId);
  };

  return (
    <div className="row justify-content-center">
      <div className="col-4">
        {posts.map((post) => (
          <div className="card bg-dark text-white mb-3" key={post.postId}>
            <div className="card-header d-flex justify-content-between">
              <Link to={`/profile/${post.posterProfile.username}`}>
                {post.posterProfile.username}
              </Link>
              {user && user.username == post.posterProfile.username && (
                <div className="btn-group">
                  <button className="btn btn-secondary px-3">Edit Post</button>
                  <button className="btn btn-danger">Delete post</button>
                </div>
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

                <p>
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
                })}
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
