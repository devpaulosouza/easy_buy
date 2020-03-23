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
  getAll: ({page, userId}) => {
    return axios.get(`/order?${userId?'userId&'+userId:''}page=${page}&size=10`, {
        headers: {
            gambi_web_token: getToken()
        }
    });
  },
  get: (id) => {
    if (id) {
      return fetch(`${URL}/order/${id}`, {
        headers: {
            gambi_web_token: getToken()
        }
    });
    }
    return false;
  }
}
