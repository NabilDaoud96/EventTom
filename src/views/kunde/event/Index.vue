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
        <tr v-for="event in events" :key="event.title">
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
import axios from "axios";

export default {
  data() {
    return {
      events: [], // Events für die aktuelle Seite
      currentPage: 1, // Aktuelle Seite
      rowsPerPage: 10, // Anzahl der Events pro Seite
      totalElements: 0, // Gesamtanzahl der Events
      totalPages: 0, // Gesamtanzahl der Seiten
    };
  },
  methods: {
    // API-Aufruf zum Laden der Events für die aktuelle Seite
    fetchEvents() {
      axios
        .get("http://localhost:8080/api/events", {
          withCredentials: true,
          params: {
            page: this.currentPage - 1, // API-Parameter: Seite (0-basiert)
            size: this.rowsPerPage, // API-Parameter: Anzahl der Einträge pro Seite
          },
        })
        .then((response) => {
          this.events = response.data.content; // Events für die aktuelle Seite
          this.totalElements = response.data.totalElements; // Gesamtanzahl der Events
          this.totalPages = response.data.totalPages; // Gesamtseitenanzahl
        })
        .catch((error) => {
          console.error("Fehler beim Laden der Daten:", error);
        });
    },
    // Zur vorherigen Seite wechseln
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.fetchEvents(); // Neue Events laden
      }
    },
    // Zur nächsten Seite wechseln
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.fetchEvents(); // Neue Events laden
      }
    },
    // Zu einer bestimmten Seite wechseln
    goToPage(page) {
      this.currentPage = page;
      this.fetchEvents(); // Neue Events laden
    },
    // Datum formatieren (DD.MM.YYYY)
    formatDate(date) {
      const eventDate = new Date(date);
      const day = String(eventDate.getDate()).padStart(2, '0');
      const month = String(eventDate.getMonth() + 1).padStart(2, '0');
      const year = eventDate.getFullYear();
      return `${day}.${month}.${year}`;
    },
  },
  created() {
    // Events für die erste Seite laden
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
.pagination-container {
  background-color: #f0f0f0;
  padding: 10px;
  border-radius: 8px;
}

button {
  cursor: pointer;
}

button:hover {
  opacity: 0.9;
}

button:focus {
  outline: none;
}
</style>
