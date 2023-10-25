const CX = "70ec685dde49b4e2d";
const KEY = "AIzaSyDoBYXegCeEFBpIFNg6eBiqhs0BARKHNnE";

const URL = `https://www.googleapis.com/customsearch/v1?key=${KEY}&cx=${CX}&searchType=image&q=`;
 

export async function findBirdImage(birdName) {
    const response = await fetch(`${URL}${birdName.replace(" ", "+")}+bird`);
    if (response.status === 200) {
        const j =  await response.json();
        return j;
    } else if (response.status === 429) {
        console.log("Past usage limit.")
        return {"items": [{"link": "question-mark.jpg"}]}
    } 
    else {
        return Promise.reject("Unexpected error.")
    }
}