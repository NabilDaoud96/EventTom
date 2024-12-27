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
          <th class="px-4 py-2 border text-left">Aktion</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="event in paginatedEvents" :key="event.id">
          <td class="px-4 py-2 border">{{ event.name }}</td>
          <td class="px-4 py-2 border">{{ event.date }}</td>
          <td class="px-4 py-2 border">{{ event.location }}</td>
          <td class="px-4 py-2 border">{{ event.price }}€</td>
          <td class="px-4 py-2 border text-center">
            <!-- Action buttons in the same line -->
            <div class="flex justify-center space-x-4">
                <button 
                @click="buyTicket(event)" 
                class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 focus:outline-none"
                >
                <i class="fa fa-shopping-cart"></i> <!-- Shopping cart icon -->
                </button>
                <button 
                @click="useVoucher(event)" 
                class="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600 focus:outline-none"
                >
                <i class="fa fa-gift"></i> <!-- Gift icon for voucher -->
                </button>
            </div>
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
      events: [], // All events
      currentPage: 1, // Current page
      rowsPerPage: 10, // Rows per page
      sortBy: 'date', // Default sorting by date
      sortOrder: 'asc', // Default sorting order is ascending
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
    // Sort Table Based on Column and Order
    sortTable(column) {
      // Toggle the sorting order if the same column is clicked again
      if (this.sortBy === column) {
        this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
      } else {
        this.sortBy = column;
        this.sortOrder = 'asc'; // Default to ascending when changing columns
      }

      this.events.sort((a, b) => {
        let valueA, valueB;
        if (column === 'date') {
          valueA = new Date(a.date);
          valueB = new Date(b.date);
        } else if (column === 'price') {
          valueA = parseFloat(a.price);
          valueB = parseFloat(b.price);
        } else {
          valueA = a[column].toLowerCase();
          valueB = b[column].toLowerCase();
        }

        if (this.sortOrder === 'asc') {
          return valueA < valueB ? -1 : valueA > valueB ? 1 : 0;
        } else {
          return valueA > valueB ? -1 : valueA < valueB ? 1 : 0;
        }
      });
    },
    // Placeholder method for buying a ticket
    buyTicket(event) {
      alert(`Buying ticket for event: ${event.name}`);
    },
    // Placeholder method for using a voucher
    useVoucher(event) {
      alert(`Using voucher for event: ${event.name}`);
    },
  },
  created() {
    // Fetch Events from the API
    axios
      .get("https://jsonplaceholder.typicode.com/posts")
      .then((response) => {
        const cities = ["Berlin", "Hamburg", "München", "Köln", "Frankfurt", "Stuttgart", "Düsseldorf", "Leipzig", "Dortmund", "Essen"];
        const today = new Date();
        // Map the response data to match the event structure
        this.events = response.data.map((item) => {
          const eventDate = new Date();
          eventDate.setDate(today.getDate() + Math.floor(Math.random() * 30)); // Random days ahead
          return {
            id: item.id,
            name: item.title.split(" ").slice(0, 5).join(" "),
            date : eventDate.toLocaleDateString('en-GB').replace(/\//g, '-'),
            location: cities[Math.floor(Math.random() * cities.length)], // Random city from the list
            price: (Math.random() * 100).toFixed(2),
          };
        });
        // Sort events by default column (date) in ascending order
        this.sortTable('date');
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
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
  background-color: #f0f0f0; /* Hellgraue Hintergrundfarbe */
  padding: 10px; /* Etwas Innenabstand */
  border-radius: 8px; /* Optional: Runde Ecken */
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
