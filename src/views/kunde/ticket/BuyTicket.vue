<template>
  <div class="p-6">
    <!-- Navbar mit Titel -->
    <div class="bg-blue-500 text-white text-center py-4 mb-6">
      <h2 class="text-3xl font-semibold">{{ currentEvent ? currentEvent.name : 'All Events' }}</h2>
    </div>

    <!-- Karten-Gruppe -->
    <div class="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 xl:grid-cols-4 gap-6">
      <!-- Jede Event-Karte -->
      <div v-for="event in paginatedEvents" :key="event.id" class="bg-white shadow-lg rounded-lg overflow-hidden">
        <div class="p-6">
          <!-- Event-Titel -->
          <h3 class="text-2xl font-semibold text-center">{{ event.name }}</h3>
          <!-- Event Details: Datum, Ort und Preis -->
          <p class="mt-2 text-center text-gray-600">Date: {{ event.date }}</p>
          <p class="mt-2 text-center text-gray-600">Location: {{ event.location }}</p>
          <p class="mt-4 text-center text-xl font-bold">Price: €{{ event.price }}</p>

          <!-- Buttons für Ticketkauf und Gutscheinverwendung -->
          <div class="flex justify-between mt-6">
            <!-- Button für "Buy Ticket" mit Einkaufswagen-Icon -->
            <button 
                class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 focus:outline-none flex items-center"
                @click="buyTicket(event)"
                >
                <i class="fa fa-shopping-cart mr-2 text-sm"></i> <!-- Einkaufswagen-Icon -->
                Buy Ticket
            </button>

            <!-- Button für "Use Voucher" mit Geschenk-Icon -->
            <button
                v-if="event.hasVoucher"  
                class="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600 focus:outline-none flex items-center"
                @click="useVoucher(event)"
                >
                <i class="fa fa-gift mr-2 text-sm"></i> <!-- Geschenk-Icon für Voucher -->
                Use Voucher
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
export default {
  data() {
    return {
      // 20 Events, alle haben Voucher
      events: Array.from({ length: 20 }, (_, index) => ({
        id: index + 1,
        name: `Event ${index + 1}`,
        date: `0${(index % 12) + 1}/01/2025`,
        location: `City ${index + 1}`,
        price: (Math.random() * 100 + 10).toFixed(2),
        hasVoucher: true,
      })),
      currentPage: 1,
      rowsPerPage: 4, // 4 Events pro Seite
      currentEvent: null, // Das aktuell ausgewählte Event
    };
  },
  computed: {
    // Paginated Events for the Current Page
    paginatedEvents() {
      const start = (this.currentPage - 1) * this.rowsPerPage;
      const end = start + this.rowsPerPage;
      return this.events.slice(start, end);
    },
    // Total Pages Calculation
    totalPages() {
      return Math.ceil(this.events.length / this.rowsPerPage);
    },
  },
  methods: {
    // Move to the Previous Page
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    // Move to the Next Page
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    // Go to Specific Page
    goToPage(page) {
      this.currentPage = page;
    },
    // Ticket kaufen
    buyTicket(event) {
      this.currentEvent = event; // Setze das aktuell gewählte Event
      alert(`Buying ticket for event: ${event.name}`);
    },
    // Gutschein verwenden (direkt Preis subtrahieren)
    useVoucher(event) {
      const discount = 20; // Beispielwert für Gutschein
      event.price = (parseFloat(event.price) - discount).toFixed(2);
      event.hasVoucher = false; // Gutschein nur einmal verwendbar
    },
  },
};
</script>

<style scoped>
.pagination-container {
  background-color: #f0f0f0; /* Hellgraue Hintergrundfarbe */
  padding: 10px; /* Etwas Innenabstand */
  border-radius: 8px; /* Optionale runde Ecken */
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
