import { createRouter, createWebHistory } from 'vue-router'
import UserList from '../components/UserList.vue'
import UserForm from '../components/UserForm.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: UserList
  },
  {
    path: '/create',
    name: 'create',
    component: UserForm
  },
  {
    path: '/edit/:id',
    name: 'edit',
    component: UserForm
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
