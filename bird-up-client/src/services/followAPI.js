const url = "http://localhost:8080/api/birdup/follower";

export async function createFollow(id) {
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
    body: JSON.stringify({}),
  };
  init.method = "POST";
  const response = await fetch(`${url}/${id}`, init);
  if (response.status === 201) {
    return response.json();
  } else if (response.status === 400) {
    const result = await response.json();
    return { errors: result.messages };
  } else {
    return Promise.reject("Unexpected error.");
  }
}

export async function deleteFollow(id) {
  const jwtToken = localStorage.getItem("jwt_token");
  if (!jwtToken) {
    return Promise.reject("Unauthorized.");
  }
  const init = {
    headers: {
      Authorization: "Bearer " + jwtToken,
    },
  };
  init.method = "DELETE";
  const response = await fetch(`${url}/${id}`, init);
  if (response.status === 204) {
    return;
  } else if (response.status === 404) {
    const result = await response.json();
    return { errors: result.messages };
  } else {
    return Promise.reject("Unexpected error.");
  }
}

export async function findFollowByFolloweeId(followeeId) {
  const jwtToken = localStorage.getItem("jwt_token");
  if (!jwtToken) {
    return Promise.reject("Unauthorized.");
  }
  const init = {
    headers: {
      Accept: "application/json",
      Authorization: "Bearer " + jwtToken,
    },
  };
  init.method = "GET";
  const response = await fetch(`${url}/${followeeId}`, init);
  if (response.status === 200) {
    return response.json();
  } else if (response.status === 404) {
    return null;
  } else {
    return Promise.reject("Unexpected error.");
  }
}
