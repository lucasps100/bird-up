export default function PostDeck({posts}) {
    return ( 
        // render image from BLOB
        <div className="row justify-content-center">
            <div className="col">
                {posts.map(post => (
                <div class="card mb-3" key={post.postId}>
                    <img class="card-img-top" src="..." alt="Card image cap" />
                <div class="card-body">
                  <h5 class="card-title">postUsername</h5>
                  <p class="card-text">{post.postText}</p>
                  <p class="card-text">date | time | location <small class="text-muted"></small></p>
                </div>
              </div>
            ))}
            </div>

        </div>
        

    );
}