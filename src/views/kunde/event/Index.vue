<template>
  <!-- Removed the max-w-7xl to allow full width -->
  <div class="p-6 w-full">
    <div class="mb-6">
      <h2 class="text-2xl font-bold text-gray-800">All Events</h2>
    </div>

    <div class="bg-white shadow-lg overflow-hidden w-full">
      <div class="overflow-x-auto w-full">
        <table class="w-full divide-y divide-gray-200">
          <thead>
          <tr>
            <th class="px-6 py-3 bg-gray-50 text-left text-x font-bold text-gray-500 uppercase tracking-wider">
              Event Name
            </th>
            <th class="px-6 py-3 bg-gray-50 text-left text-x font-bold text-gray-500 uppercase tracking-wider">
              Datum
            </th>
            <th class="px-6 py-3 bg-gray-50 text-left text-x font-medium text-gray-500 uppercase tracking-wider">
              Ort
            </th>
            <th class="px-6 py-3 bg-gray-50 text-left text-x font-bold text-gray-500 uppercase tracking-wider">
              Preis
            </th>
            <th class="px-6 py-3 bg-gray-50 text-left text-x font-bold text-gray-500 uppercase tracking-wider">
              Verfügbare Tickets
            </th>
            <th class="px-6 py-3 bg-gray-50 text-center text-x font-bold text-gray-500 uppercase tracking-wider">
              Details
            </th>
            <th class="px-6 py-3 bg-gray-50 text-center text-x font-bold text-gray-500 uppercase tracking-wider">
              Ticket
            </th>
          </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="event in events" :key="event.id" class="hover:bg-gray-50">
            <td class="px-6 py-4">
              <div class="text-l font-medium text-gray-900">{{ event.title }}</div>
            </td>
            <td class="px-6 py-4">
              <div class="text-l text-gray-500">{{ formatDate(event.dateOfEvent) }}</div>
            </td>
            <td class="px-6 py-4">
              <div class="text-l text-gray-500">{{ event.location }}</div>
            </td>
            <td class="px-6 py-4">
              <div class="text-l font-medium text-gray-900">{{ event.basePrice }}€</div>
            </td>
            <td class="px-6 py-4">
                <span v-if="event.availableTickets > 0"
                      class="px-2 inline-flex text-l leading-5 font-semibold rounded-full bg-green-100 text-green-800">
                  {{ event.availableTickets }}
                </span>
              <span v-else
                    class="px-2 inline-flex text-l leading-5  font-semibold rounded-full bg-red-100 text-red-500">
                  Ausverkauft
                </span>
            </td>
            <td class="px-6 py-4 text-center">
              <router-link
                  :to="{ name: 'EventShow', params: { id: event.id } }"
                  class="text-white bg-blue-500 hover:bg-blue-600 px-4 py-2 rounded text-sm inline-flex items-center justify-center"
              >
                <i class="fas fa-eye mr-2"></i>
              </router-link>
            </td>
            <td class="px-6 py-4 text-center">
              <router-link
                  v-if="event.availableTickets > 0"
                  :to="{ name: 'PurchaseTicket', params: { id: event.id } }"
                  class="text-white bg-green-500 hover:bg-green-600 px-4 py-2 rounded text-sm inline-flex items-center justify-center"
              >
                <i class="fas fa-ticket-alt mr-2"></i> Kaufen
              </router-link>
              <span v-else class="text-red-500 text-sm font-medium">
                  Ausverkauft
                </span>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

      <BasePagination
          :current-page="currentPage - 1"
          :total-pages="totalPages"
          @page-change="handlePageChange"
      />

  </div>
</template>

<script>
import api from "@/utils/axios-auth";
import BasePagination from '@/components/BasePagination.vue';

export default {
  components: {
    BasePagination
  },
  data() {
    return {
      events: [],
      currentPage: 1,
      rowsPerPage: 10,
      totalElements: 0,
      totalPages: 0,
      sortConfig: {
        sortBy: 'dateOfEvent',
        direction: 'asc'
      }
    };
  },
  methods: {
    async fetchEvents() {
      try {
        const response = await api.get("/events", {
          params: {
            page: this.currentPage - 1,
            size: this.rowsPerPage,
            sortBy: this.sortConfig.sortBy
          },
        });
        this.events = response.data.content;
        this.totalElements = response.data.totalElements;
        this.totalPages = response.data.totalPages;
      } catch (error) {
        console.error("Fehler beim Laden der Daten:", error.response?.data?.error);
      }
    },

    handlePageChange(page) {
      this.currentPage = page + 1;
      this.fetchEvents();
    },

    formatDate(date) {
      const eventDate = new Date(date);
      const day = String(eventDate.getDate()).padStart(2, '0');
      const month = String(eventDate.getMonth() + 1).padStart(2, '0');
      const year = eventDate.getFullYear();
      return `${day}.${month}.${year}`;
    },
  },
  created() {
    this.fetchEvents();
  },
};
</script>