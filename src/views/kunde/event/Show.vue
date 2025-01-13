<template>
  <div class="flex justify-center items-center min-h-screen p-4">
    <div v-if="isLoading" class="text-center">
      <p>Loading event data...</p>
    </div>
    <div v-else-if="error" class="text-center text-red-500">
      <p>{{ error }}</p>
    </div>
    <div v-else class="bg-white shadow-lg rounded-lg overflow-hidden max-w-md w-full">
      <div class="bg-blue-500 text-white p-4 rounded-t-lg">
        <h2 class="text-2xl font-semibold text-center">{{ event.title }}</h2>
      </div>
      <div class="p-6">
        <p class="mt-2 text-center text-gray-600">Date: {{ formatDate(event.dateOfEvent) }}</p>
        <p class="mt-2 text-center text-gray-600">Location: {{ event.location }}</p>
        <p class="mt-2 text-center text-gray-600">Available Tickets: {{ event.availableTickets }}</p>
        <p class="mt-4 text-center text-xl font-bold">Price: €{{ event.price }}</p>
        <router-link v-if="event.availableTickets > 0"
                     :to="{ name: 'PurchaseTicket', params: { id: event.id } }" class="flex justify-between mt-6">
          <button 
            class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 focus:outline-none"

          >
            <i class="fa fa-shopping-cart mr-2"></i> Buy Ticket
          </button>
          <button 
            v-if="event.hasVoucher" 
            class="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600 focus:outline-none"
            @click="useVoucher"
          >
            <i class="fa fa-gift mr-2"></i> Use Voucher
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>

import {useEvent} from "@/composables/useEvent";

export default {
  data() {
    return {
      event: null, // Startet leer, wird durch API gefüllt
      isLoading: true, // Ladesymbol oder Status
      error: null, // Fehleranzeige
    };
  },
  methods: {
    async fetchEvent() {
      try {
        const {error, loading, getEvent} = useEvent()
        const eventId = this.$route.params.id;
        this.event = await getEvent(eventId);
        this.isLoading = false;
      } catch (err) {
        this.error = "Failed to load event data.";
        this.isLoading = false;
      }
    },

    useVoucher() {
      if (this.event.hasVoucher) {
        const discount = 10;
        this.event.price -= discount;
        alert(`Voucher applied! New price: €${this.event.price}`);
        this.event.hasVoucher = false;
      }
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
    this.fetchEvent(); // Abrufen der Event-Daten beim Laden der Komponente
  }
};
</script>

<style scoped>
/* Styling für das Layout der Event-Karte */
.bg-white {
  background-color: white;
}
.shadow-lg {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
.rounded-lg {
  border-radius: 8px;
}
.text-gray-600 {
  color: #4a4a4a;
}
.bg-blue-500 {
  background-color: #3b82f6;
}
.text-white {
  color: white;
}
</style>
