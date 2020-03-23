import axios from "./axios";

export const  authApi = {
  post: (username, password) => {
    return axios.post(`/auth`, { username, password });
  },
  register: (name, username, password) => {
    return axios.post('/auth/user', { name, username, password });
  }
}
