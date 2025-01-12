<template>
  <div class="p-6">
    <h2 class="text-2xl font-semibold mb-4">Notifications</h2>

    <!-- Table -->
    <table class="min-w-full table-auto border-collapse border border-gray-200">
      <thead>
      <tr class="bg-gray-100">
        <th class="px-4 py-2 border">Message</th>
        <th class="px-4 py-2 border">Date</th>
        <th class="px-4 py-2 border">Status</th>
        <th class="px-4 py-2 border">
          <select
              v-model="sortConfig.direction"
              class="bg-transparent text-sm focus:outline-none"
              @change="handleSort"
          >
            <option value="asc">Read ↑</option>
            <option value="desc">Read ↓</option>
          </select>
        </th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="notification in notifications" :key="notification.id"
          :class="{'bg-gray-50': notification.read}">
        <td class="px-4 py-2 border">{{ notification.message }}</td>
        <td class="px-4 py-2 border">{{ formatDate(notification.date) }}</td>
        <td class="px-4 py-2 border">{{ notification.type }}</td>
        <td class="px-4 py-2 border">
            <span class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium"
                  :class="notification.read ? 'bg-green-100 text-green-800' : 'bg-yellow-100 text-yellow-800'">
              {{ notification.read ? 'Read' : 'Unread' }}
            </span>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- Pagination Controls -->
    <BasePagination
        :current-page="currentPage"
        :total-pages="totalPages"
        @page-change="loadPage"
    />


    <!-- Loading State -->
    <div v-if="loading" class="flex justify-center items-center mt-4">
      <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"></div>
    </div>

    <!-- Error State -->
    <div v-if="error" class="mt-4 p-4 bg-red-100 text-red-700 rounded">
      {{ error }}
    </div>
  </div>
</template>

<script>
import { useNotifications } from "@/composables/useNotifications";
import BasePagination from '@/components/BasePagination.vue'; // Adjust path as needed

export default {
  name: 'NotificationsTable',
  components: {
    BasePagination
  },
  setup() {
    const { loading, error, getUserNotifications } = useNotifications();
    return {
      loading,
      error,
      getUserNotifications
    };
  },
  data() {
    return {
      notifications: [],
      totalPages: 0,
      currentPage: 0,
      sortConfig: {
        sortBy: 'read',
        direction: 'asc'
      }
    };
  },
  methods: {
    formatDate(date) {
      return new Date(date).toLocaleDateString();
    },
    async loadPage(page) {
      // Add validation to prevent invalid page loads
      // if (page < 0 || page >= this.totalPages) return;

      const response = await this.getUserNotifications({
        page,
        size: 10,
        sortBy: this.sortConfig.sortBy,
        direction: this.sortConfig.direction
      });

      if (response) {
        this.notifications = response.content;
        this.totalPages = response.totalPages;
        this.currentPage = response.number;
      }
    },
    async handleSort() {
      await this.loadPage(0);
    }
  },
  mounted() {
    this.loadPage(0);
  }
};
</script>