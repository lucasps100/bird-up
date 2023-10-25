import { findSpeciesById } from "../services/speciesAPI";

export default function PostDeck({posts}) {

    return ( 
        // render image from BLOB
        <div className="row justify-content-center">
            <div className="col">
                {posts.map(post => (
                <div class="card mb-3" key={post.postId}>
                <div class="card-body">
                  <h5 class="card-title">Sighting: {post.species.speciesShortName} </h5>
                  <p class="card-text">{post.postText}</p>
                  <p class="card-text">{post.posterProfile.username} | {post.createdAt.split("T")[0]}  <small class="text-muted"></small></p>
                </div>
              </div>
            ))}
            </div>

        </div>
        

    );
}