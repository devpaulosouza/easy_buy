import axios from "./axios";
import { getToken } from "../util/web";

export const  productApi = {
  getAll: (page = 0) => {
    return axios.get(`/product?size=5&page=${page}&sort=createdAt,desc`);
  },
  get: (id) => {
    if (id) {
      return fetch(`${URL}/product/${id}`);
    }
    return false;
  },
  post: (product) =>  {
    return axios.post('/product', product, {
      headers: {
          gambi_web_token: getToken()
      }
  }) 
  }
}
