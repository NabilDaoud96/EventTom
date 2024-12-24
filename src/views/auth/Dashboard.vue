<template>
    <div class="container mx-auto px-4 h-full">
        <div class="flex content-center items-center justify-center h-full">
            <div class="w-full lg:w-8/12 px-4">
                <div class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-blueGray-200 border-0">
                    <div class="rounded-t mb-0 px-6 py-6">
                        <div class="text-center mb-3">
                            <h6 class="text-blueGray-500 text-sm font-bold">My Dashboard</h6>
                        </div>
                    </div>

                    <!-- Ticket List Section -->
                    <div class="flex-auto px-4 lg:px-10 py-10 pt-0">
                        <div class="text-blueGray-400 text-center mb-3 font-bold">
                            <small>Purchased Tickets</small>
                        </div>

                        <!-- Ticket List Table -->
                        <div class="overflow-x-auto bg-white rounded-lg shadow-md mb-6">
                            <table class="min-w-full table-auto">
                                <thead>
                                    <tr>
                                        <th class="px-6 py-3 text-xs font-bold text-blueGray-500 text-left align-middle border-b border-blueGray-200 bg-blueGray-50 uppercase">Ticket ID</th>
                                        <th class="px-6 py-3 text-xs font-bold text-blueGray-500 text-left align-middle border-b border-blueGray-200 bg-blueGray-50 uppercase">Event</th>
                                        <th class="px-6 py-3 text-xs font-bold text-blueGray-500 text-left align-middle border-b border-blueGray-200 bg-blueGray-50 uppercase">Purchase Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <!-- Example Ticket Rows -->
                                    <tr v-for="ticket in tickets" :key="ticket.id">
                                        <td class="px-6 py-4 text-sm text-blueGray-600">{{ ticket.id }}</td>
                                        <td class="px-6 py-4 text-sm text-blueGray-600">{{ ticket.event }}</td>
                                        <td class="px-6 py-4 text-sm text-blueGray-600">{{ ticket.purchaseDate }}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <!-- Buttons Section -->
                        <div class="flex justify-between mt-6">
                            <router-link to="/redeem-voucher">
                                <button class="bg-green-500 text-white font-bold px-6 py-3 rounded shadow hover:shadow-md transition-all duration-150">
                                    Redeem Voucher
                                </button>
                            </router-link>

                            <router-link to="/buy-tickets">
                                <button class="bg-blueGray-800 text-white font-bold px-6 py-3 rounded shadow hover:shadow-md transition-all duration-150">
                                    Buy Tickets
                                </button>
                            </router-link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>export default {
  name: 'Dashboard',
  data() {
    return {
      // Beispiel Tickets
      tickets: [
        { id: '12345', event: 'Konzert XYZ', purchaseDate: '2023-12-15' },
        { id: '67890', event: 'Theater ABC', purchaseDate: '2023-12-10' },
      ],
    };
  },
};</script>

<style scoped>
    /* Scoped styling für Dashboard */
</style>
