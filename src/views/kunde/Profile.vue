<template>
  <div class="flex items-center justify-center min-h-screen">
    <div class="w-full max-w-lg px-4">
      <div class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-blueGray-100 border-0">
      
      <div class="rounded-t bg-white mb-0 px-6 py-6">
        <div class="flex justify-center -mt-10 mb-3">
          <div class="w-20 h-20 bg-blueGray-200 inline-flex items-center justify-center rounded-full shadow-lg">
            <img
              alt="Profile Picture"
              class="w-full h-full rounded-full align-middle"
              :src="profilePicture"
            />
          </div>
        </div>

          <div class="text-center">
            <h6 class="text-blueGray-700 text-xl font-bold">My Profile</h6>
          </div>
        </div>
        <div class="flex-auto px-4 lg:px-10 py-10 pt-0">
          <form>
            <h6 class="text-blueGray-400 text-sm mt-3 mb-6 font-bold uppercase">
              User Information
            </h6>
            <div class="flex flex-wrap">
              <div class="w-full lg:w-6/12 px-4">
                <div class="relative w-full mb-3">
                  <label
                    class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                    for="lastname"
                  >
                    Last Name
                  </label>
                  <input
                    id="lastname"
                    type="text"
                    v-model="profile.lastName"
                    class="border-0 px-3 py-3 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  />
                </div>
              </div>
              <div class="w-full lg:w-6/12 px-4">
                <div class="relative w-full mb-3">
                  <label
                    class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                    for="firstname"
                  >
                    First Name
                  </label>
                  <input
                    id="firstname"
                    type="text"
                    v-model="profile.firstName"
                    class="border-0 px-3 py-3 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  />
                </div>
              </div>
              <div class="w-full lg:w-6/12 px-4">
                <div class="relative w-full mb-3">
                  <label
                    class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                    for="email"
                  >
                    Email Address
                  </label>
                  <input
                    id="email"
                    type="email"
                    v-model="profile.email"
                    class="border-0 px-3 py-3 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  />
                </div>
              </div>
              <div class="w-full lg:w-6/12 px-4">
                <div class="relative w-full mb-3">
                  <label
                    class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                    for="password"
                  >
                    Password
                  </label>
                  <input
                    id="password"
                    type="text"
                    v-model="profile.password"
                    class="border-0 px-3 py-3 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  />
                </div>
              </div>
            </div>

            <div class="mt-6 mr-4 text-right">
              <button
                class="bg-emerald-500 text-white active:bg-emerald-600 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none ease-linear transition-all duration-150"
                type="button"
              >
                Save
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import image from "@/assets/img/team-1-800x800.jpg"; // Default Profile Image

export default {
  data() {
    return {
      profile: {
        firstName: 'Lucky',
        lastName: 'Jesse',
        email: 'jesse@example.com',
        password: '*********',
      },
      profilePicture: image, // Default profile image
    };
  },
};
</script>

<style>
.min-h-screen {
  min-height: 100vh;
}

.w-16 {
  width: 4rem; /* Avatar width */
}

.h-16 {
  height: 4rem; /* Avatar height */
  }

</style>
