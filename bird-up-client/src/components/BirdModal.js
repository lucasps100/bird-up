import { useEffect, useState } from "react";
import { findBirdImage } from "../services/googleImageSearchAPI.js";
import { getBirdDescription } from "../services/openAiAPI.js";

export default function BirdModal({ birdName, onClose }) {
  const [isLoading, setIsLoading] = useState(false);
  const [birdImage, setBirdImage] = useState();
  const [birdDescription, setBirdDescription] = useState(
    "Description Loading..."
  );

  useEffect(() => {
    console.log("Received Bird Name:", birdName);
    if (!birdName) return;

    const fetchData = async () => {
      setIsLoading(true);
      try {
        findBirdImage(birdName).then((r) => setBirdImage(r.items[0].link));
        console.log(birdImage);
        getBirdDescription(birdName).then(setBirdDescription);
        console.log(birdDescription);
      } catch (error) {
        console.error("Failed to fetch bird image and description.", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, [birdName]);

  return (
    <div className="mx-3 p-2 d-flex flex-wrap" id="bird-modal">
      {isLoading ? (
        <p>Loading...</p>
      ) : (
        birdImage && (
          <div className="col bird-modal">
            <h1>{birdName}</h1>
            <img
              src={birdImage}
              id="bird-img"
              alt={birdName}
              height="300"
              className="mb-3"
            ></img>
            <p id="birdDescription">{birdDescription}</p>
            <div className="d-flex justify-content-center">
              <button className="btn btn-info btn-lg my-3" onClick={onClose}>
                Close
              </button>
            </div>
          </div>
        )
      )}
    </div>
  );
}
