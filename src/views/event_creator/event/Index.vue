<template>
  <div class="p-6 w-full">
    <!-- Search Component -->
    <div class="mb-6 bg-white p-4 rounded-lg shadow">
      <div class="max-w-md">
        <p class="text-xl mb-2 font-bold text-gray-800">Search Events</p>
        <SearchInput
            v-model="searchQuery"
            placeholder="Search by title or location"
            @search="handleSearch"
        />
      </div>
    </div>

    <!-- Grid of Cards -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div
          v-for="event in events"
          :key="event.id"
          class="bg-white rounded-lg shadow-lg overflow-hidden hover:shadow-xl transition-shadow duration-300"
      >
        <!-- Card Header -->
        <div class="p-4 border-b border-gray-200 bg-gray-50">
          <h3 class="text-xl font-semibold text-gray-900 truncate">{{ event.title }}</h3>
        </div>

        <!-- Card Content -->
        <div class="p-4 space-y-3">
          <div class="flex items-center text-gray-600">
            <i class="fas fa-calendar-alt mr-2"></i>
            <span>{{ formatDate(event.dateOfEvent) }}</span>
          </div>

          <div class="flex items-center text-gray-600">
            <i class="fas fa-map-marker-alt mr-2"></i>
            <span>{{ event.location }}</span>
          </div>

          <div class="space-y-1">
            <div class="flex items-center font-semibold">
              <i class="fas fa-tag mr-2"></i>
              <span>{{ formatPrice(event.price) }}</span>
            </div>
            <div class="flex items-center text-gray-500 text-sm ml-5">
              <span>Base price: {{ formatPrice(event.basePrice) }}</span>
            </div>
          </div>

          <div class="space-y-2">
            <!-- Available Tickets -->
            <div class="flex items-center">
              <span
                  v-if="event.availableTickets > 0"
                  class="px-3 py-1 rounded-full text-emerald-500 font-semibold"
              >
                {{ event.availableTickets }} tickets available
              </span>
              <span
                  v-else
                  class="rounded-full text-red-500 font-semibold"
              >
                Ausverkauft
              </span>
            </div>

            <!-- Threshold Indicator -->
            <div class="flex items-center">
              <div class="w-full">
                <div class="flex justify-between text-sm mb-1">
                  <span>Sold: {{ event.soldTickets }}</span>
                  <span>Threshold: {{ event.thresholdValue }}</span>
                </div>
                <div
                    class="flex items-center space-x-2"
                    :class="getThresholdClass(event)"
                >
                  <i
                      class="fas mr-1"
                      :class="getThresholdIcon(event)"
                  ></i>
                  <span>{{ getThresholdMessage(event) }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Card Actions -->
        <div class="p-4 bg-gray-50 border-t border-gray-200 flex justify-between">
          <router-link
              :to="{ name: 'UpdateEventForm', params: { id: event.id } }"
              class="text-white bg-blue-500 hover:bg-blue-600 px-4 py-2 rounded text-sm inline-flex items-center"
          >
            <i class="fas fa-edit mr-2"></i>
            Update
          </router-link>

          <button
              @click="handleDelete(event.id)"
              class="text-white bg-red-500 hover:bg-red-600 px-4 py-2 rounded text-sm inline-flex items-center"
          >
            <i class="fas fa-trash mr-2"></i>
            Delete
          </button>
        </div>
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

    <!-- Pagination -->
    <div class="mt-6">
      <BasePagination
          :current-page="currentPage - 1"
          :total-pages="totalPages"
          @page-change="handlePageChange"
      />
    </div>
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
      rowsPerPage: 12, // Changed to match customer view
      totalElements: 0,
      totalPages: 0,
      sortConfig: {
        sortBy: 'dateOfEvent', // Changed to match customer view
        direction: 'asc'
      },
      searchQuery: '',
    };
  },
  methods: {
    formatPrice,
    formatDate,
    getThresholdClass(event) {
      const ratio = event.soldTickets / event.thresholdValue;
      if (ratio >= 1.1) { // More than 10% above threshold
        return 'text-emerald-500';
      } else if (ratio <= 0.9) { // More than 10% below threshold
        return 'text-red-500';
      }
      return ''; // Within 10% of threshold
    },
    getThresholdIcon(event) {
      const ratio = event.soldTickets / event.thresholdValue;
      if (ratio >= 1.1) {
        return 'fa-arrow-trend-up';
      } else if (ratio <= 0.9) {
        return 'fa-arrow-trend-down';
      }
      return 'fa-equals';
    },
    getThresholdMessage(event) {
      const ratio = event.soldTickets / event.thresholdValue;
      const percentage = Math.abs((ratio - 1) * 100).toFixed(1);

      if (ratio >= 1.1) {
        return `${percentage}% above threshold`;
      } else if (ratio <= 0.9) {
        return `${percentage}% below threshold`;
      }
      return 'Near threshold';
    },
    handlePageChange(page) {
      this.currentPage = page + 1;
      this.fetchEvents();
    },
    handleSearch(value) {
      this.searchQuery = value;
      this.currentPage = 1;
      this.fetchEvents();
    },
    async handleDelete(id) {
      try {
        const confirmed = await confirm('Are you sure you want to delete this item?');

        if (confirmed) {
          await this.deleteEvent(id);
          this.events = this.events.filter(event => event.id !== id);

          if (this.events.length === 0 && this.currentPage > 1) {
            this.currentPage--;
            await this.fetchEvents();
          }
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