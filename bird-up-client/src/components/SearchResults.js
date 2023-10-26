import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import PostDeck from "./PostDeck";

import { findPostsBySpecies } from "../services/postsAPI.js";

export default function SearchResults() {
  const navigate = useNavigate();
  const { query } = useParams();

  const [posts, setPosts] = useState([]);

  useEffect(() => {
    console.log(query);
    findPostsBySpecies(query)
      .then(setPosts)
      .catch((error) => {
        console.error(error);
        navigate("/error", { state: { error } });
      });
  }, [query]);
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
