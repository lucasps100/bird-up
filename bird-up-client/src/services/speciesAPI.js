const url = "http://localhost:8080/api/birdup/species"

export async function findAllSpecies() {
    const response = await fetch(url);
    if (response.status === 200) {
        return response.json();
    } else {
        return Promise.reject("Unexpected error.")
    }
}

export async function findSpeciesById(id) {
    const response = await fetch(`${url}/${id}`);
  if (response.status === 200) {
    return response.json();
  } else if (response.status === 404) {
    return Promise.reject(`Species game id: ${id} could not be found.`);
  } else {
    return Promise.reject("Unexpected error, oops.");
  }
}