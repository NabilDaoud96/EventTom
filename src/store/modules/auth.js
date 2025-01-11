// store/modules/auth.js
import axios from 'axios'
import {baseApi} from "@/router/base-api";

const state = {
    token: localStorage.getItem('token') || null,
    user: JSON.parse(localStorage.getItem('user')) || null
}

const getters = {
    isAuthenticated: state => !!state.token,
    userProfile: state => state.user
}

const actions = {
    async login({ commit }, credentials) {
        try {
            const response = await baseApi.post('/auth/login', credentials)
            console.log(response)
            const user = response.data
            localStorage.setItem('user', JSON.stringify(user))


            commit('SET_AUTH_SUCCESS', { user })
            return response
        } catch (error) {
            commit('SET_AUTH_ERROR')
            localStorage.removeItem('token')
            localStorage.removeItem('user')
            throw error
        }
    },

    async logout({ commit }) {
        try {
            await baseApi.post('/auth/signout')

            // Clear local storage
            localStorage.removeItem('user')

            // Remove token from axios headers
            delete axios.defaults.headers.common['Authorization']

            commit('SET_LOGOUT')
        } catch (error) {
            console.error('Logout error:', error)
            throw error
        }
    },
    async checkAuthentication({ commit }) {
        try {
            const response = await baseApi.get('/auth/authenticated')
            return response.data
        } catch (error) {
            commit('SET_AUTH_ERROR')
            return false
        }
    },
    async fetchUser({ commit }) {
        try {
            // Replace with your API endpoint
            const response = await baseApi.get('/auth/user')
            const user = response.data

            localStorage.setItem('user', JSON.stringify(user))
            commit('SET_USER', user)
            return user
        } catch (error) {
            commit('SET_AUTH_ERROR')
            throw error
        }
    }
}

const mutations = {
    SET_AUTH_SUCCESS(state, { token, user }) {
        state.token = token
        state.user = user
    },

    SET_AUTH_ERROR(state) {
        state.token = null
        state.user = null
    },

    SET_LOGOUT(state) {
        state.token = null
        state.user = null
    },

    SET_USER(state, user) {
        state.user = user
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}