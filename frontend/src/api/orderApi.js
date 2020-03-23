import axios from "./axios";
import { getToken } from "../util/web";

export const orderApi = {
  post: (order) => {
    return axios.post(`/order`, order, {
        headers: {
            gambi_web_token: getToken()
        }
    });
  },
  getAll: (page) => {
    return axios.get(`/order?page=${page}&size=10`)
  },
  get: (id) => {
    if (id) {
      return fetch(`${URL}/order/${id}`);
    }
    return false;
  }
}
