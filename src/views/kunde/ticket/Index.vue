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
      <tr v-for="ticket in tickets" :key="ticket.id">
        <td class="px-4 py-2 border">{{ ticket.eventTitle }}</td>
        <td class="px-4 py-2 border">{{ ticket.date }}</td>
        <td class="px-4 py-2 border">{{ ticket.location }}</td>
        <td class="px-4 py-2 border">{{ ticket.finalPrice }}</td>
        <td class="px-4 py-2 border">{{ calculateDaysLeft(ticket.date) }}</td>
      </tr>
      </tbody>
    </table>

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
import { useTickets } from '@/composables/useTickets';
import BasePagination from '@/components/BasePagination.vue';

export default {
  name: 'TicketsTable',
  components: {
    BasePagination
  },
  setup() {
    const { error, loading, getUserTickets } = useTickets();
    return {
      error,
      loading,
      getUserTickets,

    };
  },
  data() {
    return {
      tickets: [],
      totalPages: 0,
      currentPage: 0,
      sortConfig: {
        sortBy: 'event.dateOfEvent',
        direction: 'asc'
      }
    };
  },
  methods: {
    async loadPage(page) {
      const response = await this.getUserTickets({
        page,
        size: 10,
        sortBy: this.sortConfig.sortBy,
        direction: this.sortConfig.direction
      });

      if (response) {
        this.tickets = response.content;
        this.totalPages = response.totalPages;
        this.currentPage = response.number;
      }
    },
    calculateDaysLeft(eventDate) {
      const today = new Date();
      const event = new Date(eventDate);

      // Reset the time part to compare full days only
      today.setHours(0, 0, 0, 0);
      event.setHours(0, 0, 0, 0);

      const diffTime = event.getTime() - today.getTime();
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

      if (diffDays < 0) {
        return 'Event passed';
      } else if (diffDays === 0) {
        return 'Today!';
      } else {
        return `${diffDays} days`;
      }
    },
  },
  mounted() {
    this.loadPage(0);
  }
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
  padding: 8px;
}
th {
  background-color: #f0f0f0;
}
</style>