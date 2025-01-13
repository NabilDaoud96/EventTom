<template>
  <div class="p-6 w-full">
    <div class="mb-6 bg-white p-4 rounded-lg shadow">
        <label class="block text-sm font-medium text-gray-700 mb-1">Search Events</label>
        <SearchInput
            v-model="searchQuery"
            placeholder="Search by title or location"
            @search="handleSearch"
        />
    </div>
    <div class="mb-6">
      <h2 class="text-2xl font-bold text-gray-800">All Events</h2>
    </div>

    <div class="bg-white shadow-lg overflow-hidden w-full">
      <div class="overflow-x-auto w-full">
        <table class="w-full divide-y divide-gray-200">
          <thead>
          <tr>
            <th class="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Event Name
            </th>
            <th class="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Datum
            </th>
            <th class="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Ort
            </th>
            <th class="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Preis
            </th>
            <th class="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Verfügbare Tickets
            </th>
            <th class="px-6 py-3 bg-gray-50 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
              Aktion
            </th>
          </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="event in events" :key="event.id" class="hover:bg-gray-50">
            <td class="px-6 py-4">
              <div class="text-sm font-medium text-gray-900">{{ event.title }}</div>
            </td>
            <td class="px-6 py-4">
              <div class="text-sm text-gray-500">{{ formatDate(event.dateOfEvent) }}</div>
            </td>
            <td class="px-6 py-4">
              <div class="text-sm text-gray-500">{{ event.location }}</div>
            </td>
            <td class="px-6 py-4">
              <div class="text-sm font-medium text-gray-900">{{ formatPrice(event.price) }}</div>
            </td>
            <td class="px-6 py-4">
                <span v-if="event.availableTickets > 0" class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full">
                  {{ event.availableTickets }} available
                </span>
              <span v-else class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-red-100 text-red-500">
                  Ausverkauft
                </span>
            </td>
            <td class="px-6 py-4 text-center">
              <router-link
                  :to="{ name: 'UpdateEventForm', params: { id: event.id } }"
                  class="text-white bg-blue-500 hover:bg-blue-600 px-4 py-2 rounded text-sm inline-flex items-center justify-center"
              >
                <i class="fas fa-eye mr-2"></i>
                Update
              </router-link>
            </td>
            <td class="px-6 py-4 text-center">
              <button
                  @click="handleDelete(event.id)"
                  class="text-white cursor-pointer bg-red-400 hover:bg-red-600 px-4 py-2 rounded text-sm inline-flex items-center justify-center"
              >
                <i class="fas fa-trash mr-2"></i>
                Delete
              </button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Loading State -->
    <div v-if="loading" class="flex justify-center items-center mt-4">
      <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"></div>
    </div>

    <!-- Error State -->
    <div v-if="error" class="mt-4 p-4 bg-red-100 border border-red-400 text-red-700 rounded-md">
      {{ error }}
    </div>

    <BasePagination
        :current-page="currentPage - 1"
        :total-pages="totalPages"
        @page-change="handlePageChange"
    />
  </div>
</template>

<script>
import BasePagination from '@/components/BasePagination.vue';
import { useEvent } from "@/composables/useEvent";
import { formatDate, formatPrice } from "../../../utils/formatter";
import SearchInput from "@/components/SearchComponent.vue";

export default {
  components: {
    SearchInput,
    BasePagination
  },
  setup() {
    const { error, loading, getEventsByManager, deleteEvent } = useEvent();
    return {
      error,
      loading,
      getEventsByManager,
      deleteEvent
    };
  },
  data() {
    return {
      events: [],
      currentPage: 1,
      rowsPerPage: 10,
      totalElements: 0,
      totalPages: 0,
      sortConfig: {
        sortBy: 'id',
        direction: 'asc'
      },
      searchQuery: '',
    };
  },
  methods: {
    formatPrice,
    formatDate,
    handlePageChange(page) {
      this.currentPage = page + 1;
      this.fetchEvents();
    },
    // Updated search handler
    handleSearch(value) {
      this.searchQuery = value;
      this.currentPage = 1; // Reset to first page when searching
      this.fetchEvents();
    },

    async handleDelete(id) {
      try {
        const confirmed = await confirm('Are you sure you want to delete this item?');

        if (confirmed) {
          await this.deleteEvent(id);
          // Remove the deleted event from the local events array
          this.events = this.events.filter(event => event.id !== id);

          // If the current page becomes empty, go to the previous page
          if (this.events.length === 0 && this.currentPage > 1) {
            this.currentPage--;
            await this.fetchEvents();
          }
          // Update total elements and pages
          this.totalElements--;
          this.totalPages = Math.ceil(this.totalElements / this.rowsPerPage);
        }
      } catch (error) {
        console.error("Error deleting event:", error);
      }
    },
    async fetchEvents() {
      try {
        const response = await this.getEventsByManager({
          page: this.currentPage - 1,
          size: this.rowsPerPage,
          sortBy: this.sortConfig.sortBy,
          direction: this.sortConfig.direction,
          search: this.searchQuery
        });

        this.events = response.content;
        this.totalElements = response.totalElements;
        this.totalPages = response.totalPages;
      } catch (err) {
        console.error("Error loading events:", err);
      }
    }
  },
  created() {
    this.fetchEvents();
  }
};
</script>