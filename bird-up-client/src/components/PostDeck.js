import { useState } from "react";
import BirdModal from "./BirdModal";

export default function PostDeck({posts}) {

    const [selectedBird, setSelectedBird] = useState(null);
    const [modalOpen, setModalOpen] = useState(false);


    const onBirdClick = (birdName) => {
        setModalOpen(true);
        setSelectedBird(birdName);
      };
    
      const closeModal = () => {
        setModalOpen(false);
        setSelectedBird(null);
      };


    return ( 
        // render image from BLOB
        <div className="row justify-content-center">
            <div className="col-4">
                {posts.map(post => (
                <div class="card mb-3" key={post.postId}>
                <div class="card-body">
                  <button class="card-title" onClick={() => onBirdClick(post.species.speciesShortName)}>Sighting: {post.species.speciesShortName} </button>
                  <p class="card-text">{post.postText}</p>
                  <p class="card-text">{post.posterProfile.username} | {post.createdAt.split("T")[0]}  <small class="text-muted"></small></p>
                </div>
              </div>
            ))}
            </div>

            <div className="col-3">
            {modalOpen && <BirdModal birdName={selectedBird} onClose={closeModal} />}
            </div>


        </div>
        

    );
}