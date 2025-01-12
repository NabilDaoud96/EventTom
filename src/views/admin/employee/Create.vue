<template>
  <div class="min-h-screen bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full mx-auto space-y-8">
      <!-- Header -->
      <div>
        <h2 class="mt-6 text-center text-3xl font-bold tracking-tight text-gray-900">
          Create New User
        </h2>
      </div>

      <!-- Form -->
      <form class="mt-8 space-y-6" @submit.prevent="handleSubmit">
        <div class="rounded-md shadow-sm space-y-4">
          <!-- Email -->
          <div>
            <label for="email" class="block text-sm font-medium text-gray-700">
              Email Address
            </label>
            <input
                id="email"
                v-model="formData.email"
                type="email"
                required
                class="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2 text-gray-900 placeholder-gray-500 focus:border-indigo-500 focus:outline-none focus:ring-indigo-500 sm:text-sm"
                :class="{ 'border-red-500': errors.email }"
            />
            <p v-if="errors.email" class="mt-1 text-sm text-red-600">{{ errors.email }}</p>
          </div>

          <!-- Password -->
          <div>
            <label for="password" class="block text-sm font-medium text-gray-700">
              Password
            </label>
            <input
                id="password"
                v-model="formData.password"
                type="password"
                required
                class="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2 text-gray-900 placeholder-gray-500 focus:border-indigo-500 focus:outline-none focus:ring-indigo-500 sm:text-sm"
                :class="{ 'border-red-500': errors.password }"
            />
            <p v-if="errors.password" class="mt-1 text-sm text-red-600">{{ errors.password }}</p>
          </div>

          <!-- First Name -->
          <div>
            <label for="firstName" class="block text-sm font-medium text-gray-700">
              First Name
            </label>
            <input
                id="firstName"
                v-model="formData.firstName"
                type="text"
                required
                class="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2 text-gray-900 placeholder-gray-500 focus:border-indigo-500 focus:outline-none focus:ring-indigo-500 sm:text-sm"
                :class="{ 'border-red-500': errors.firstName }"
            />
            <p v-if="errors.firstName" class="mt-1 text-sm text-red-600">{{ errors.firstName }}</p>
          </div>

          <!-- Last Name -->
          <div>
            <label for="lastName" class="block text-sm font-medium text-gray-700">
              Last Name
            </label>
            <input
                id="lastName"
                v-model="formData.lastName"
                type="text"
                required
                class="mt-1 block w-full rounded-md border border-gray-300 px-3 py-2 text-gray-900 placeholder-gray-500 focus:border-indigo-500 focus:outline-none focus:ring-indigo-500 sm:text-sm"
            />
            <p v-if="errors.lastName" class="mt-1 text-sm text-red-600">{{ errors.lastName }}</p>
          </div>

          <!-- Roles -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Roles
            </label>
            <div class="space-y-2">
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
            <p v-if="errors.roles" class="mt-1 text-sm text-red-600">{{ errors.roles }}</p>
          </div>
        </div>

        <!-- Submit Button -->
        <div>
          <button
              type="submit"
              class="group relative flex w-full justify-center rounded-md border border-transparent bg-indigo-600 py-2 px-4 text-sm font-medium text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
          >
            Create User
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import {defineComponent, onMounted, ref} from 'vue'
import {useRegistration} from "@/composables/useRegistration"
import {useRole} from "@/composables/useRole"

export default defineComponent({
  name: 'CreateUser',
  setup() {
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

    // Fetch roles on component mount
    onMounted(async () => {
      try {
        availableRoles.value = await getRoles()
      } catch (error) {
        console.error('Error fetching roles:', error)
      }
    })

    const validateForm = () => {
      const newErrors = {}

      // Email validation
      if (!formData.value.email) {
        newErrors.email = 'Email is required'
      } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.value.email)) {
        newErrors.email = 'Invalid email format'
      }

      // Password validation
      if (!formData.value.password) {
        newErrors.password = 'Password is required'
      } else if (formData.value.password.length < 6) {
        newErrors.password = 'Password must be at least 6 characters long'
      }

      // First name validation
      if (!formData.value.firstName) {
        newErrors.firstName = 'First name is required'
      } else if (formData.value.firstName.length > 50) {
        newErrors.firstName = 'First name cannot exceed 50 characters'
      }

      // Last name validation
      if (!formData.value.lastName) {
        newErrors.lastName = 'Last name is required'
      } else if (formData.value.lastName.length > 50) {
        newErrors.lastName = 'Last name cannot exceed 50 characters'
      }

      // Roles validation
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
            roles: formData.value.roles // roles should just be the enum values
          }
          await registerEmployee(requestData)
        } catch (error) {
          console.error('Error creating user:', error)
        }
      }
    }

    // Update the template to use the role's displayName
    const formatRoleLabel = (role) => {
      return role.name;
    }

    return {
      formData,
      errors,
      availableRoles,
      handleSubmit,
      formatRoleLabel,
      loading,
      error
    }
  }
})
</script>