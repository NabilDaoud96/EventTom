<template>
  <div class="min-h-screen bg-gray-100">
    <div class="container mx-auto px-4 sm:px-6 lg:px-8 max-w-7xl">
      <!-- Loading State -->
      <div v-if="loading" class="flex justify-center items-center py-16">
        <div class="animate-spin rounded-full h-12 w-12 border-b-4 border-indigo-600"></div>
      </div>

      <!-- Error State -->
      <div v-if="error" class="bg-red-50 border-l-4 border-red-400 shadow-lg rounded-lg p-6 mt-4">
        <div class="flex items-center">
          <div class="flex-shrink-0">
            <svg class="h-5 w-5 text-red-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
              <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"/>
            </svg>
          </div>
          <div class="ml-3">
            <h3 class="text-sm font-medium text-red-800">Error creating employee</h3>
            <div class="mt-2 text-sm text-red-700">{{ error }}</div>
          </div>
        </div>
      </div>

      <!-- Main Content -->
      <div class="bg-white shadow-xl rounded-md overflow-hidden">
        <div class="px-6 py-8 space-y-8">
          <!-- Employee Creation Form -->
          <div class="bg-white rounded-xl shadow-lg p-6 border border-gray-100">
            <h3 class="text-xl font-semibold text-gray-900 mb-6">Create Employee</h3>

            <!-- Form Fields -->
            <form @submit.prevent="handleSubmit" class="space-y-6">
              <div class="grid grid-cols-1 gap-6 sm:grid-cols-2">
                <!-- Email -->
                <div class="space-y-3">
                  <label for="email" class="block text-sm font-medium text-gray-500">
                    Email Address
                  </label>
                  <input
                      id="email"
                      v-model="formData.email"
                      type="email"
                      required
                      class="block w-full px-4 py-3 rounded-md border border-gray-300 focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                      :class="{ 'border-red-500': errors.email }"
                  />
                  <p v-if="errors.email" class="text-sm text-red-600">{{ errors.email }}</p>
                </div>

                <!-- Password -->
                <div class="space-y-3">
                  <label for="password" class="block text-sm font-medium text-gray-500">
                    Password
                  </label>
                  <input
                      id="password"
                      v-model="formData.password"
                      type="password"
                      required
                      class="block w-full px-4 py-3 rounded-md border border-gray-300 focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                      :class="{ 'border-red-500': errors.password }"
                  />
                  <p v-if="errors.password" class="text-sm text-red-600">{{ errors.password }}</p>
                </div>
              </div>

              <div class="grid grid-cols-1 gap-6 sm:grid-cols-2">
                <!-- First Name -->
                <div class="space-y-3">
                  <label for="firstName" class="block text-sm font-medium text-gray-500">
                    First Name
                  </label>
                  <input
                      id="firstName"
                      v-model="formData.firstName"
                      type="text"
                      required
                      class="block w-full px-4 py-3 rounded-md border border-gray-300 focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                      :class="{ 'border-red-500': errors.firstName }"
                  />
                  <p v-if="errors.firstName" class="text-sm text-red-600">{{ errors.firstName }}</p>
                </div>

                <!-- Last Name -->
                <div class="space-y-3">
                  <label for="lastName" class="block text-sm font-medium text-gray-500">
                    Last Name
                  </label>
                  <input
                      id="lastName"
                      v-model="formData.lastName"
                      type="text"
                      required
                      class="block w-full px-4 py-3 rounded-md border border-gray-300 focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                      :class="{ 'border-red-500': errors.lastName }"
                  />
                  <p v-if="errors.lastName" class="text-sm text-red-600">{{ errors.lastName }}</p>
                </div>
              </div>


              <div class="space-y-3 mt-1">
                <label class="block text-sm font-medium text-gray-500">
                  Roles
                </label>
                <div class="grid grid-cols-2 gap-4 mt-1">
                  <div v-for="role in availableRoles" :key="role.name" class="flex items-center">
                    <input
                        :id="role.name"
                        type="checkbox"
                        v-model="formData.roles"
                        :value="role.name"
                        class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
                    />
                    <label :for="role.name" class="ml-2 block text-sm text-gray-900">
                      {{ role.name }}
                    </label>
                  </div>
                </div>
                <p v-if="errors.roles" class="text-sm text-red-600">{{ errors.roles }}</p>
              </div>

              <!-- Actions -->
              <div class="flex justify-end space-x-4 pt-6">
                <button
                    type="button"
                    @click="resetForm"
                    class="px-6 py-3 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition-colors duration-150"
                >
                  Reset
                </button>
                <button
                    type="submit"
                    :disabled="loading"
                    class="px-6 py-3 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-500 hover:bg-indigo-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition-colors duration-150 disabled:opacity-50"
                >
                  <span v-if="loading">Creating...</span>
                  <span v-else>Create Employee</span>
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent, onMounted, ref } from 'vue'
import { useRegistration } from "@/composables/useRegistration"
import { useRole } from "@/composables/useRole"
import { useRouter } from 'vue-router'

export default defineComponent({
  name: 'CreateEmployee',
  setup() {
    const router = useRouter()
    const formData = ref({
      email: '',
      password: '',
      firstName: '',
      lastName: '',
      roles: []
    })
    const { error, loading, registerEmployee } = useRegistration()
    const { getRoles } = useRole()
    const errors = ref({})
    const availableRoles = ref([])

    onMounted(async () => {
      try {
        availableRoles.value = await getRoles()
      } catch (error) {
        console.error('Error fetching roles:', error)
      }
    })

    const validateForm = () => {
      const newErrors = {}

      if (!formData.value.email) {
        newErrors.email = 'Email is required'
      } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.value.email)) {
        newErrors.email = 'Invalid email format'
      }

      if (!formData.value.password) {
        newErrors.password = 'Password is required'
      } else if (formData.value.password.length < 6) {
        newErrors.password = 'Password must be at least 6 characters long'
      }

      if (!formData.value.firstName) {
        newErrors.firstName = 'First name is required'
      } else if (formData.value.firstName.length > 50) {
        newErrors.firstName = 'First name cannot exceed 50 characters'
      }

      if (!formData.value.lastName) {
        newErrors.lastName = 'Last name is required'
      } else if (formData.value.lastName.length > 50) {
        newErrors.lastName = 'Last name cannot exceed 50 characters'
      }

      if (!formData.value.roles.length) {
        newErrors.roles = 'At least one role must be assigned'
      }

      errors.value = newErrors
      return Object.keys(newErrors).length === 0
    }

    const handleSubmit = async () => {
      if (validateForm()) {
        try {
          const requestData = {
            ...formData.value,
            roles: formData.value.roles
          }
          await registerEmployee(requestData)
          router.push({
            path: '/employees',
            query: {
              created: 'success'
            }
          })
        } catch (error) {
          console.error('Error creating employee:', error)
        }
      }
    }

    const resetForm = () => {
      formData.value = {
        email: '',
        password: '',
        firstName: '',
        lastName: '',
        roles: []
      }
      errors.value = {}
    }

    return {
      formData,
      errors,
      availableRoles,
      handleSubmit,
      resetForm,
      loading,
      error
    }
  }
})
</script>