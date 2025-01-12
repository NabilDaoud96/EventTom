<template>
  <div class="p-6">
    <!-- Navbar mit Titel -->
    <div class="bg-blue-500 text-white text-center py-4 mb-6">
      <h2 class="text-3xl font-semibold">All Events</h2>
    </div>

    <!-- Karten-Gruppe -->
    <div class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 xl:grid-cols-4 gap-6">
      <!-- Jede Event-Karte -->
      <div v-for="event in events" :key="event.id" class="bg-white shadow-lg rounded-lg overflow-hidden">
        <div class="p-6">
          <!-- Event-Titel -->
          <h3 class="text-2xl font-semibold text-center">{{ event.title }}</h3>
          <!-- Event Details: Datum, Ort und Preis -->
          <p class="mt-2 text-center text-gray-600">Date: {{ formatDate(event.dateOfEvent) }}</p>
          <p class="mt-2 text-center text-gray-600">Location: {{ event.location }}</p>
          <p class="mt-4 text-center text-xl font-bold">Price: €{{ event.basePrice }}</p>

          <!-- Buttons für Ticketkauf und Gutscheinverwendung -->
          <div class="flex justify-between mt-6">
            <!-- Button für "Buy Ticket" -->
            <button
              class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 focus:outline-none flex items-center"
              @click="buyTicket(event.id)"
            >
              <i class="fa fa-shopping-cart mr-2 text-sm"></i>
              Buy Ticket
            </button>

          </div>
        </div>
      </div>
    </div>

    <!-- Pagination Controls -->
    <div class="pagination-container flex justify-center items-center mt-8 space-x-2">
      <button
        class="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400 disabled:opacity-50"
        @click="prevPage"
        :disabled="currentPage === 1"
      >
        &larr; Previous
      </button>

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

      <button
        class="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400 disabled:opacity-50"
        @click="nextPage"
        :disabled="currentPage === totalPages"
      >
        Next &rarr;
      </button>
    </div>
  </div>
</template>

<script>
import api from "@/utils/axios-auth";

export default {
  data() {
    return {
      events: [], // Events von der API
      currentPage: 1, // Aktuelle Seite
      rowsPerPage: 8, // Anzahl der Karten pro Seite
      totalElements: 0, // Gesamtanzahl der Events
      totalPages: 0, // Gesamtanzahl der Seiten
    };
  },
  methods: {
    // Events von der API laden
    fetchEvents() {
      api
        .get("/events", {
          params: {
            page: this.currentPage - 1, // Backend beginnt mit 0
            size: this.rowsPerPage,
          },
        })
        .then((response) => {
          this.events = response.data.content; // Events für die aktuelle Seite
          this.totalElements = response.data.totalElements; // Gesamtanzahl
          this.totalPages = response.data.totalPages; // Gesamtanzahl der Seiten
        })
        .catch((error) => {
          console.error("Error fetching events:", error.response?.data?.error);
          alert("Failed to load events. Please try again.");
        });
    },
    // Zur vorherigen Seite wechseln
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.fetchEvents();
      }
    },
    // Zur nächsten Seite wechseln
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.fetchEvents();
      }
    },
    // Zu einer bestimmten Seite wechseln
    goToPage(page) {
      if (page >= 1 && page <= this.totalPages) {
        this.currentPage = page;
        this.fetchEvents();
      }
    },
    // Ticketkauf über API
    buyTicket(eventId) {

    },
    // Gutschein verwenden
    useVoucher(eventId) {
      api
        .post("/vouchers", {
          eventId: eventId,
        })
        .then(() => {
          alert(`Voucher successfully applied for Event ID: ${eventId}`);
        })
        .catch((error) => {
          console.error("Error using voucher:", error.response?.data?.error);
          alert(`Failed to apply voucher for Event ID: ${eventId}`);
        });
    },
    // Datum formatieren
    formatDate(date) {
      const eventDate = new Date(date);
      const day = String(eventDate.getDate()).padStart(2, "0");
      const month = String(eventDate.getMonth() + 1).padStart(2, "0");
      const year = eventDate.getFullYear();
      return `${day}.${month}.${year}`;
    },
  },
  created() {
    // Daten beim Laden der Komponente abrufen
    this.fetchEvents();
  },
};
</script>

<style scoped>
.pagination-container {
  background-color: #f0f0f0; /* Hellgraue Hintergrundfarbe */
  padding: 10px; /* Etwas Innenabstand */
  border-radius: 8px; /* Runde Ecken */
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
