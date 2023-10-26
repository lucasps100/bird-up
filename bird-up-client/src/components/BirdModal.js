import { useEffect, useState } from "react";
import { findBirdImage } from "../services/googleImageSearchAPI.js";
import { getBirdDescription } from "../services/openAiAPI.js";

export default function BirdModal({ birdName, onClose }) {
  const [isLoading, setIsLoading] = useState(false);
  const [birdImage, setBirdImage] = useState();
  const [birdDescription, setBirdDescription] = useState(null);

  useEffect(() => {
    console.log("Received Bird Name:", birdName);
    if (!birdName) return;

    const fetchData = async () => {
      setIsLoading(true);
      try {
        findBirdImage(birdName).then((r) => setBirdImage(r.items[0].link));
        console.log(birdImage);
        // getBirdDescription(birdName).then(setBirdDescription);
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
    <div className="Inner-Squares mx-3 p-2 d-flex flex-wrap">
      {isLoading ? (
        <p>Loading...</p>
      ) : (
        birdImage && (
          <div>
            <h1>{birdName}</h1>
            <img src={birdImage} alt={birdName} height="300"></img>
            {/* <p id ="birdDescription">{birdDescription}</p> */}
            <button onClick={onClose}>Close</button>
          </div>
        )
      )}
    </div>
  );
}
