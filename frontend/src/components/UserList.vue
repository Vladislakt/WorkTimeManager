<template>
  <div>
    <h1>Пользователи</h1>

    <router-link to="/create">Добавить пользователя</router-link>

    <table v-if="users.length > 0">
      <thead>
        <tr>
          <th>ID</th>
          <th>Имя</th>
          <th>Email</th>
          <th>Дата рождения</th>
          <th>Возраст</th>
          <th>Действия</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.id }}</td>
          <td>{{ user.name }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.birth }}</td>
          <td>{{ user.age }}</td>
          <td>
            <router-link :to="`/edit/${user.id}`">Редактировать</router-link>
            <button @click="confirmDelete(user)">Удалить</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-else>
      <p>Нет пользователей</p>
    </div>
  </div>
</template>

<script>
import { usersApi } from '../api/users'

export default {
  name: 'UserList',
  data() {
    return {
      users: []
    }
  },
  mounted() {
    this.loadUsers()
  },
  methods: {
    async loadUsers() {
      try {
        const response = await usersApi.getAll()
        this.users = response.data
      } catch (error) {
        console.error('Ошибка загрузки:', error)
      }
    },
    async deleteUser(id) {
      try {
        await usersApi.delete(id)
        await this.loadUsers()
        alert('Пользователь удален')
      } catch (error) {
        console.error('Ошибка удаления:', error)
        alert('Ошибка при удалении')
      }
    },
    confirmDelete(user) {
      if (confirm(`Удалить пользователя ${user.name}?`)) {
        this.deleteUser(user.id)
      }
    }
  }
}
</script>
