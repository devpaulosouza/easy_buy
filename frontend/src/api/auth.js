import axios from "./axios";

export const  authApi = {
  post: (username, password) => {
    return axios.post(`/auth`, { username, password });
  },
  get: (id) => {
    if (id) {
      return fetch(`${URL}/product/${id}`);
    }
    return false;
  }
}
