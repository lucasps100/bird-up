const url = 'http://localhost:8080/api/birdup/post';

export async function findAllPosts() {
    const response = await fetch(`${url}/0/0/0`);
    if (response.status === 200) {
        return response.json();
      } else {
        return Promise.reject("Unexpected error, oops.");
      }
}

export async function savePost(post, speciesId) {
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
      body: JSON.stringify(post),
    };
  
    if (post.postId > 0) {
      init.method = "PUT";
      const response = await fetch(`${url}/${post.postId}/${speciesId}`, init);
      if (response.status === 400) {
        const result = await response.json();
        return { errors: result.messages };
      } else if (response.status === 404) {
        return Promise.reject(
          `Post id: ${post.postId} could not be found.`
        );
      } else if (response.status !== 204) {
        return Promise.reject("Unexpected error, oops.");
      }
    } else {
      init.method = "POST";
      const response = await fetch(url + '/' + speciesId, init);
      if (response.status === 201) {
        return response.json();
      } else if (response.status === 400) {
        const result = await response.json();
        return { errors: result.messages };
      } else {
        return Promise.reject("Unexpected error, oops.");
      }
    }
  }
  