<template>
  <!-- Navbar -->


<nav class="border-gray-200 bg-gray-50 dark:bg-gray-800 dark:border-gray-700">
  <div class="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
    <a href="#" class="flex items-center space-x-3 rtl:space-x-reverse">
        <span class="self-center text-xl font-semibold whitespace-nowrap dark:text-white">{{ currentPage }}</span>
    </a>
    <!-- User -->
      <ul class="flex-col md:flex-row list-none items-center hidden md:flex">
        <notification-dropdown />
        <user-dropdown />
      </ul>
  </div>
  <hr class="mb-4 border-b-1 border-blueGray-200" />
</nav>
  <!-- End Navbar -->
</template>

<script>
import UserDropdown from "../Dropdowns/UserDropdowns/EventMangerDropdown/EventMangerDropDown.vue";
import NotificationDropdown from "../Dropdowns/NotificationDropdown/EventManagerNotification/EventMangerNotificationDropdown.vue";

export default {
  components: {
    UserDropdown,
    NotificationDropdown
  },
  computed: {
    // Berechnet den aktuellen Seitentitel basierend auf der Route
    currentPage() {
      if (this.$route.path === '/event_manager/dashboard') {
        return 'Dashboard';
      } else if (this.$route.path === '/event_manager/profile') {
        return 'Profile';
      } else if (this.$route.path === '/event_manager/kunden') {
        return 'Customers';
      }
       else if (this.$route.path === '/event_manager/create_vourcher') {
        return 'Add Vourcher';
      }
       else if (this.$route.path === '/event_manager/events') {
        return 'Events';
      }
      else {
        return 'Unknown Page';
      }
    }
  }
};
</script>
