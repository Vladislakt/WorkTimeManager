import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

export const usersApi = {
  getAll() {
    return api.get('/users')
  },

  create(user) {
    return api.post('/users', user)
  },

  delete(id) {
    return api.delete(`/users/${id}`)
  },

  update(id, email, name) {
    return api.put(`/users/${id}`, null, {
      params: { email, name }
    })
  }
}
