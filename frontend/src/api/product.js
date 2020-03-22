import axios from "./axios";

export const  productApi = {
  getAll: (page = 0) => {
    return axios.get(`/product?size=5&page=${page}`);
  },
  get: (id) => {
    if (id) {
      return fetch(`${URL}/product/${id}`);
    }
    return false;
  }
}
