<template>
  <nav class="border-gray-200 bg-gray-50 dark:bg-gray-800 dark:border-gray-700">
    <div class="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
      <a href="#" class="flex items-center space-x-3 rtl:space-x-reverse">
        <span class="self-center text-xl font-semibold whitespace-nowrap dark:text-white">{{ currentPage }}</span>
      </a>

      <!-- Authenticated User View -->
      <ul v-if="isAuthenticated" class="flex-col md:flex-row list-none items-center hidden md:flex">
        <notification-dropdown />
        <user-dropdown />
      </ul>

      <!-- Guest View -->
      <ul v-else class="flex-col md:flex-row list-none items-center hidden md:flex space-x-4">
        <li>
          <router-link
              to="/auth/login"
              class="text-gray-700 hover:text-gray-900 dark:text-gray-200 dark:hover:text-white"
          >
            Login
          </router-link>
        </li>
        <li>
          <router-link
              to="/auth/register"
              class="text-gray-700 hover:text-gray-900 dark:text-gray-200 dark:hover:text-white"
          >
            Register
          </router-link>
        </li>
      </ul>
    </div>
    <hr class="mb-4 border-b-1 border-blueGray-200" />
  </nav>
</template>

<script>
import { mapGetters } from 'vuex';
import UserDropdown from "@/components/Dropdowns/UserDropdown.vue";
import NotificationDropdown from "@/components/Dropdowns/NotificationDropdown.vue";

export default {
  components: {
    UserDropdown,
    NotificationDropdown
  },

  computed: {
    ...mapGetters('auth', ['isAuthenticated']),

    currentPage() {
      console.log(this.isAuthenticated)
      if (this.$route.path === '/kunde/dashboard') {
        return 'Dashboard';
      } else if (this.$route.path === '/kunde/tables') {
        return 'Tables';
      } else if (this.$route.path === '/kunde/tickets') {
        return 'Tickets';
      } else if (this.$route.path === '/kunde/profile') {
        return 'Profile';
      } else if (this.$route.path === '/kunde/events') {
        return 'Events';
      } else if (this.$route.name === 'EventShow') {
        return 'Event Details';
      } else if (this.$route.path === '/kunde/buy_ticket') {
        return 'Buy Ticket';
      } else {
        return 'Unknown Page';
      }
    }
  }
};
</script>