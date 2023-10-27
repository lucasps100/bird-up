const URL = `https://www.googleapis.com/customsearch/v1?key=${process.env.REACT_APP_GOOGLE_KEY}&cx=${process.env.REACT_APP_GOOGLE_CX}&searchType=image&q=`;

export async function findBirdImage(birdName) {
  const response = await fetch(`${URL}${birdName.replace(" ", "+")}+bird`);
  if (response.status === 200) {
    const j = await response.json();
    return j;
  } else if (response.status === 429) {
    console.log("Past usage limit.");
    return { items: [{ link: "question-mark.jpg" }] };
  } else {
    return Promise.reject("Unexpected error.");
  }
}
