const url = "http://localhost:8080/api/birdup/profile";

export async function createProfile(profile) {
  const jwtToken = localStorage.getItem("jwt_token");
  if (!jwtToken) {
    return Promise.reject("Unauthorized.");
  }

  const init = {
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
      Authorization: "Bearer " + jwtToken,
    },
    body: JSON.stringify(profile),
  };

  init.method = "POST";
  const response = await fetch(url, init);
  if (response.status === 201) {
    return response.json();
  } else if (response.status < 500) {
    const result = await response.json();
    return { errors: result.messages };
  } else {
    return Promise.reject("Unexpected error.");
  }
}

export async function editProfile(profile) {
  const jwtToken = localStorage.getItem("jwt_token");
  if (!jwtToken) {
    return Promise.reject("Unauthorized.");
  }

  const init = {
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
      Authorization: "Bearer " + jwtToken,
    },
    body: JSON.stringify(profile),
  };
  init.method = "PUT";
  console.log(profile);
  const response = await fetch(url, init);
  if (response.status === 400) {
    const result = await response.json();
    return { errors: result.messages };
  } else if (response.status === 404) {
    return Promise.reject(`Profile could not be found.`);
  } else if (response.status !== 204) {
    return Promise.reject("Unexpected error.");
  }
}

export async function getProfileByUsername(username) {
  const response = await fetch(`${url}/${username}`);
  if (response.status === 200) {
    const j = await response.json();
    console.log(j);
    return j;
  } else {
    return Promise.reject("Unexpected error.");
  }
}
