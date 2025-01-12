<template>
  <div class="p-6">
    <h2 class="text-2xl font-semibold mb-4">All Events</h2>

    <!-- Table -->
    <table class="min-w-full table-auto border-collapse border border-gray-200">
      <thead>
        <tr class="bg-gray-100">
          <th class="px-4 py-2 border text-left">Event Name</th>
          <th class="px-4 py-2 border text-left">Datum</th>
          <th class="px-4 py-2 border text-left">Ort</th>
          <th class="px-4 py-2 border text-left">Preis</th>
          <th class="px-4 py-2 border text-left">Verfügbare Tickets</th>
          <th class="px-4 py-2 border text-left">Aktion</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="event in events" :key="event.id">
          <td class="px-4 py-2 border">{{ event.title }}</td>
          <td class="px-4 py-2 border">{{ formatDate(event.dateOfEvent) }}</td>
          <td class="px-4 py-2 border">{{ event.location }}</td>
          <td class="px-4 py-2 border">{{ event.basePrice }}€</td>
          <td class="px-4 py-2 border">
            {{ event.availableTickets === 0 ? "Ausverkauft" : event.availableTickets }}
          </td>
          <td class="px-6 py-2 border text-center">
            <!-- Show Event Link -->
            <router-link
              :to="{ name: 'EventShow', params: { id: event.id } }"
              class="px-1 py-1 bg-blue-500 text-white text-sm rounded hover:bg-blue-600 focus:outline-none flex justify-center items-center"
            >
              <i class="fa fa-eye"></i>
            </router-link>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Pagination Controls -->
    <BasePagination
        :current-page="currentPage - 1"
    :total-pages="totalPages"
    @page-change="handlePageChange"
    />
  </div>
</template>

<script>
import api from "@/utils/axios-auth";
import BasePagination from '@/components/BasePagination.vue';  // Adjust path as needed

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
    };
  },
  methods: {
    async fetchEvents() {
      try {
        const response = await api.get("/events", {
          params: {
            page: this.currentPage - 1,  // Backend expects 0-based index
            size: this.rowsPerPage,
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
      this.currentPage = page + 1;  // Convert from 0-based to 1-based
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

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}
th,
td {
  text-align: center;
  padding: 12px;
}
th {
  background-color: #f0f0f0;
}
</style>