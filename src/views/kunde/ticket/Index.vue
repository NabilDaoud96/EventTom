<template>
  <div class="p-6">
    <h2 class="text-2xl font-semibold mb-4">Purchased Tickets</h2>

    <!-- Table -->
    <table class="min-w-full table-auto border-collapse border border-gray-200">
      <thead>
        <tr class="bg-gray-100">
          <th class="px-4 py-2 border">Event Name</th>
          <th class="px-4 py-2 border">Datum</th>
          <th class="px-4 py-2 border">Ort</th>
          <th class="px-4 py-2 border">Final Preis</th>
          <th class="px-4 py-2 border">Days Left</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="ticket in paginatedTickets" :key="ticket.id">
          <td class="px-4 py-2 border">{{ ticket.eventTitle }}</td>
          <td class="px-4 py-2 border">{{ ticket.date}}</td>
          <td class="px-4 py-2 border">{{ ticket.location }}</td>
          <td class="px-4 py-2 border">{{ ticket.finalPrice }}</td>
          <td class="px-4 py-2 border">{{ ticket.statusUsed }}</td>  <!-- noch berechnen -->
        </tr>
      </tbody>
    </table>

    <!-- Pagination Controls -->
    <div class="pagination-container flex justify-center items-center mt-4 space-x-2">
      <!-- Previous Arrow -->
      <button
        class="px-2 py-1 bg-gray-300 rounded hover:bg-gray-400 disabled:opacity-50"
        @click="prevPage"
        :disabled="currentPage === 1"
      >
        &larr;
      </button>

      <!-- Page Numbers -->
      <button
        v-for="page in totalPages"
        :key="page"
        @click="goToPage(page)"
        class="px-3 py-1 rounded border"
        :class="{
          'bg-blue-500 text-white': currentPage === page,
          'bg-gray-300 hover:bg-gray-400': currentPage !== page,
        }"
      >
        {{ page }}
      </button>

      <!-- Next Arrow -->
      <button
        class="px-2 py-1 bg-gray-300 rounded hover:bg-gray-400 disabled:opacity-50"
        @click="nextPage"
        :disabled="currentPage === totalPages"
      >
        &rarr;
      </button>
    </div>  
  </div>
</template>

<script>
import { useTickets } from '@/composables/useTickets'

const {error, loading, getUserTickets} = useTickets()

export default {
  data() {
    return {
      tickets: [], // All events
      currentPage: 1, // Current page
      rowsPerPage: 10, // Anzahl der Events pro Seite
      totalElements: 0, // Gesamtanzahl der Events
      totalPages: 0, // Gesamtanzahl der Seiten
    };
  },
  computed: {
    paginatedTickets() {
      const start = (this.currentPage - 1) * this.rowsPerPage;
      const end = start + this.rowsPerPage;
      return this.tickets.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.tickets.length / this.rowsPerPage);
    },
  },
  methods: {

    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },

    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },

    goToPage(page) {
      this.currentPage = page;
    },

    async fetchTickets() {
      try {
        const response = await getUserTickets();
        this.tickets = response.content
        this.totalElements = response.data.totalElements;
        this.totalPages = response.data.totalPages;

      } catch (err) {
        console.log(err);
      }
    }
  },
  created() {
    this.fetchTickets();
  }
}

</script>


<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}
th,
td {
  text-align: center;
  padding: 8px;
}
th {
  background-color: #f0f0f0;
}
.pagination-container {
  background-color: #f0f0f0; /* Hellgraue Hintergrundfarbe */
  padding: 10px; /* Etwas Innenabstand */
  border-radius: 8px; /* Optional: Runde Ecken */
}
</style>

