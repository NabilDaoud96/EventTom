<template>
  <div class="flex flex-col items-center justify-start min-h-screen mt-6">
    <!-- Cards Grid -->
    <div class="grid grid-cols-1 sm:grid-cols-1 lg:grid-cols-3 gap-6 w-full px-4">
      
      <!-- First Card: Booked Events -->
      <div class="bg-white shadow-lg rounded-lg p-6 hover:shadow-xl transition duration-150 ease-in-out">
        <div class="text-center">
          <h2 class="text-xl font-bold text-gray-700">🎫 Booked Events</h2>
          <p class="text-sm text-gray-500 mt-2">You have <br> {{ events.length }} events booked.</p>
          <router-link
              to="/kunde/tickets"
            >
            <div class="mt-4">
            <button class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
              Show my tickets
            </button>
          </div>
          </router-link>
        </div>
      </div>

      <!-- Second Card: Next Event -->
      <div class="bg-white shadow-lg rounded-lg p-6 hover:shadow-xl transition duration-150 ease-in-out">
        <div class="text-center">
          <h2 class="text-xl font-bold text-gray-700">⏳ Next Event</h2>
          <p class="text-sm text-gray-500 mt-2">
            Next event: {{ nextEvent.name }} in {{ nextEvent.location }} on {{ nextEvent.date }}.
          </p>
          <router-link
              to="/kunde/Events"
            >
            <div class="mt-4">
                <button class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                View all Events
                </button>
            </div>
          </router-link>    
        </div>
      </div>

      <!-- Third Card: Vouchers (Only Show if Available) -->
      <div v-if="vouchers.length > 0" class="bg-white shadow-lg rounded-lg p-6 hover:shadow-xl transition duration-150 ease-in-out">
        <div class="text-center">
          <h2 class="text-xl font-bold text-gray-700">🎁 Vouchers</h2>
          <p class="text-sm text-gray-500 mt-2">2 Voucher available <br>Total Voucher Value: {{ totalVoucherValue }}€</p>
          <router-link
              to="/kunde/buy_ticket"
            >
            <div class="mt-4">
                <button class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                Use Vouchers
                </button>
          </div>
          </router-link>
        </div>
      </div>
      
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      events: [
        { id: 1, name: "Concert", location: "Berlin", date: "2024-01-15" },
        { id: 2, name: "Play", location: "Munich", date: "2024-02-20" },
        { id: 3, name: "Theater", location: "Hamburg", date: "2024-03-10" },
      ], // Example list of events
      vouchers: [{ value: 10 }, { value: 10 }], // Example vouchers
    };
  },
  computed: {
    nextEvent() {
      return this.events[0]; // Just the first event for now; you can implement logic to get the next one dynamically
    },
    totalVoucherValue() {
      return this.vouchers.reduce((sum, voucher) => sum + voucher.value, 0);
    },
  },
};
</script>

<style scoped>
/* Additional Custom Styles for Cards */
</style>
