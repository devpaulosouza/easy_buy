import axios from 'axios';
import { URL } from '../util/consts';

const axiosInstance = axios.create({
  baseURL: URL,
});

export default axiosInstance;
