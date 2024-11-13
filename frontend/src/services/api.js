import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export const fetchData = async () => {
    try {
        const response = await axios.get(`${SERVER_URL}/data`);
        return response.data;
    } catch (error) {
        console.error('Error fetching data:', error);
        throw error;
    }
};

export const loginUser = async (username, password) => {
    try {
        const response = await axios.post(`${SERVER_URL}/login`, {
            username,
            password
        });
        return response.data;
    } catch (error) {
        console.error('Login failed:', error);
        throw error;
    }
};