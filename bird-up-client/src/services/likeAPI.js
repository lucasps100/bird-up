const url = "http://localhost:8080/api/birdup/like";

export async function createLike(id) {
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
    body: JSON.stringify({ postId: id, userId: 0 }),
  };
  init.method = "POST";
  const response = await fetch(url, init);
  if (response.status === 201) {
    return response.json();
  } else if (response.status === 400) {
    const result = await response.json();
    return { errors: result.messages };
  } else {
    return Promise.reject("Unexpected error, oops.");
  }
}

export async function deleteLike(id) {
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
    return Promise.reject("Unexpected error, oops.");
  }
}
