import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import PostDeck from "./PostDeck";
// import AuthContext from "../context/AuthContext.js";

import { findAllPosts } from "../services/postsAPI.js";

export default function Explore() {
  const navigate = useNavigate();
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    findAllPosts()
      .then(setPosts)
      .catch((error) => {
        console.error(error);
        navigate("/error", { state: { error } });
      });
  }, [posts]);

  return (
    <div className="background">
      {posts.length == 0 ? (
        <div className="alert alert-warning py-4">No post found.</div>
      ) : (
        <PostDeck posts={posts} />
      )}
    </div>
  );
}
