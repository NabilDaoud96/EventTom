// src/utils/axios-auth.js
import axios from 'axios';
import store from '@/store';

const api = axios.create({
    baseURL: 'http://localhost:8080/api/auth/',
    withCredentials: true
});

// Flag to prevent multiple refresh token requests
let isRefreshing = false;
// Store pending requests
let failedQueue = [];

const processQueue = (error) => {
    failedQueue.forEach(prom => {
        if (error) {
            prom.reject(error);
        } else {
            prom.resolve();
        }
    });
    failedQueue = [];
};

// Response interceptor
api.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        // If error is not 401 or request has already been retried, reject
        if (error.response?.status !== 401 || originalRequest._retry) {
            return Promise.reject(error);
        }

        if (isRefreshing) {
            // If token refresh is in progress, queue the request
            return new Promise((resolve, reject) => {
                failedQueue.push({ resolve, reject });
            })
                .then(() => api(originalRequest))
                .catch((err) => Promise.reject(err));
        }

        originalRequest._retry = true;
        isRefreshing = true;

        try {
            await store.dispatch('auth/refreshToken');
            processQueue(null);
            return api(originalRequest);
        } catch (refreshError) {
            processQueue(refreshError, null);
            await store.dispatch('auth/logout');
            return Promise.reject(refreshError);
        } finally {
            isRefreshing = false;
        }
    }
);

export default api;