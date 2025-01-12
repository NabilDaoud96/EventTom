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
    currentPage() {
      switch (this.$route.path) {
          // Dashboard routes
        case '/dashboard':
          return 'Dashboard';

          // Kunde routes
        case '/tickets':
          return 'My Tickets';
        case '/vouchers':
          return 'My Vouchers';
        case '/events':
          return 'Events';
        case '/profile':
          return 'Profile';
        case '/notifications':
          return 'Notifications';
        case '/purchase-ticket':
          return 'Purchase Ticket';

          // Event Creator routes
        case '/event-creator/dashboard':
          return 'Event Creator Dashboard';
        case '/event-creator/profile':
          return 'Event Creator Profile';
        case '/event-creator/events':
          return 'Manage Events';
        case '/event-creator/create':
          return 'Create New Event';

          // Admin routes
        case '/admin/employee-register':
          return 'Register Employee';

          // Auth routes
        case '/auth/login':
          return 'Login';
        case '/auth/register':
          return 'Register';

          // Dynamic routes with parameters
        default:
          // Handle routes with parameters
          if (this.$route.name === 'EventShow') {
            return 'Event Details';
          }
          if (this.$route.name === 'PurchaseTicket') {
            return 'Purchase Ticket';
          }
          if (this.$route.name === 'UpdateEventForm') {
            return 'Update Event';
          }
          return 'Unknown Page';
      }
    }
  }
};
</script>