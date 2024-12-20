<template>
  <div class="container mx-auto px-4 h-full">
    <div class="flex content-center items-center justify-center h-full">
      <div class="w-full lg:w-6/12 px-4">
        <div class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-blueGray-200 border-0">
          <div class="rounded-t mb-0 px-6 py-6">
            <div class="text-center mb-3">
              <h6 class="text-blueGray-500 text-sm font-bold">Sign up with</h6>
            </div>
            <div class="btn-wrapper text-center">
               <button
                class="bg-white active:bg-blueGray-50 text-blueGray-700 font-normal px-4 py-2 rounded outline-none focus:outline-none mr-2 mb-1 uppercase shadow hover:shadow-md inline-flex items-center font-bold text-xs ease-linear transition-all duration-150"
                type="button"
              >
                <img alt="..." class="w-5 mr-1" :src="github" />
                Github
              </button>  
              <button
                class="bg-white active:bg-blueGray-50 text-blueGray-700 font-normal px-4 py-2 rounded outline-none focus:outline-none mr-1 mb-1 uppercase shadow hover:shadow-md inline-flex items-center font-bold text-xs ease-linear transition-all duration-150"
                type="button"
              >
                <img alt="..." class="w-5 mr-1" :src="google" />
                Google
              </button>
            </div>
            <hr class="mt-6 border-b-1 border-blueGray-300" />
          </div>
          <div class="flex-auto px-4 lg:px-10 py-10 pt-0">
            <div class="text-blueGray-400 text-center mb-3 font-bold">
              <small>Or sign up with credentials</small>
            </div>
            <form v-on:submit.prevent="handleSubmit">
              <div v-if="error" class="text-red-500 text-center mb-3">
                {{ error }}
              </div>

              <div class="relative w-full mb-3">
                <label
                  class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                  for="firstname"
                >
                  First Name
                </label>
                <input
                  id="firstname"
                  v-model="formData.firstname"
                  type="text"
                  class="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  placeholder="First Name"
                  required
                />
              </div>

              <div class="relative w-full mb-3">
                <label
                  class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                  for="lasname"
                >
                  Last Name
                </label>
                <input
                  id="lastname"
                  v-model="formData.lastname"
                  type="text"
                  class="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  placeholder="Last Name"
                  required
                />
              </div>

              <div class="relative w-full mb-3">
                <label
                  class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                  for="email"
                >
                  Email
                </label>
                <input
                  id="email"
                  v-model="formData.email"
                  type="email"
                  class="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  placeholder="Email"
                  required
                />
              </div>

              <div class="relative w-full mb-3">
                <label
                  class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                  for="password"
                >
                  Password
                </label>
                <input
                  id="password"
                  v-model="formData.password"
                  type="password"
                  class="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  placeholder="Password"
                  required
                />
              </div>

              <div class="relative w-full mb-3">
                <label
                    class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                    for="role"
                >
                    Select Role
                </label>
                <div class="relative">
                    <select
                        id="role"
                        v-model="formData.role"
                        class="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                        required
                        >
                        <option value="" disabled selected>Select Role</option>
                        <option value="Administrator">Administrator</option>
                        <option value="Eventcreator">Eventcreator</option>
                        <option value="Customer">Customer</option>
                    </select>
                </div>
                </div>

              <div class="text-center mt-6">
                <button
                  :disabled="loading"
                  class="bg-blueGray-800 text-white active:bg-blueGray-600 text-sm font-bold uppercase px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 w-full ease-linear transition-all duration-150"
                  type="submit"
                >
                  {{ loading ? 'Creating Account...' : 'Create Account' }}
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
import github from "@/assets/img/github.svg";
import google from "@/assets/img/google.svg";

export default {
  name: 'RegisterForm',
  data() {
    return {
      formData: {
        email: '',
        firstName: '',
        lastName: '',
        password: '',
        role: '',  
      },
      loading: false,
      error: '',
      github,
      google
    }
  },
  methods: {
    async handleSubmit() {
      try {
        this.loading = true;
        this.error = '';
        
        const response = await fetch('http://localhost:8080/api/registration/customer', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(this.formData),
        });

        if (!response.ok) {
          throw new Error('Registration failed');
        }

        // Optionally redirect to login page
        this.$router.push('/login');
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    }
  }
}
</script>
