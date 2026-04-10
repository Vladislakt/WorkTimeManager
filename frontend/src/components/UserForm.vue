<template>
  <div>
    <div>
      <h2>{{ isEditing ? 'Редактирование пользователя' : 'Создание пользователя' }}</h2>

      <form @submit.prevent="saveUser">
        <div>
          <label>Имя:</label>
          <input type="text" v-model="formData.name" required autofocus />
        </div>

        <div>
          <label>Email:</label>
          <input type="email" v-model="formData.email" required />
        </div>

        <div>
          <label>Дата рождения:</label>
          <input type="date" v-model="formData.birth" required />
        </div>

        <div>
          <button type="submit">Сохранить</button>
          <button type="button" @click="goBack">Отмена</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { usersApi } from '../api/users'

export default {
  name: 'UserForm',
  data() {
    return {
      formData: {
        id: null,
        name: '',
        email: '',
        birth: ''
      },
      isEditing: false
    }
  },
  mounted() {
    const userId = this.$route.params.id
    if (userId) {
      this.isEditing = true
      this.loadUser(userId)
    }
  },
  methods: {
    async loadUser(id) {
      try {
        const response = await usersApi.getAll()
        const user = response.data.find(u => u.id == id)
        if (user) {
          this.formData = { ...user }
        }
      } catch (error) {
        console.error('Ошибка загрузки:', error)
      }
    },
    async saveUser() {
      try {
        if (this.isEditing) {
          await usersApi.update(
            this.formData.id,
            this.formData.email,
            this.formData.name
          )
          alert('Пользователь обновлен')
        } else {
          await usersApi.create({
            name: this.formData.name,
            email: this.formData.email,
            birth: this.formData.birth
          })
          alert('Пользователь создан')
        }
        this.$router.push('/')
      } catch (error) {
        console.error('Ошибка сохранения:', error)
        alert('Ошибка при сохранении')
      }
    },
    goBack() {
      this.$router.push('/')
    }
  }
}
</script>
