<template>
    <div class="flex flex-col items-center justify-center min-h-screen bg-blueGray-50">
        <div class="w-full max-w-6xl px-4 grid grid-cols-2 gap-6">
            <!-- Anstehende Veranstaltungen -->
            <div class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow rounded-lg bg-white border border-gray-200">
                <div class="rounded-t bg-gray-100 mb-0 px-6 py-4 text-center">
                    <h6 class="text-blueGray-700 text-xl font-bold">Anstehende Veranstaltungen</h6>
                </div>
                <div class="flex-auto px-4 lg:px-10 py-6">
                    <div v-if="paginatedUpcomingEvents.length > 0">
                        <table class="table-auto w-full text-left border-collapse">
                            <thead>
                                <tr>
                                    <th class="px-4 py-2 border-b">Event-ID</th>
                                    <th class="px-4 py-2 border-b">Event</th>
                                    <th class="px-4 py-2 border-b">Datum</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="event in paginatedUpcomingEvents"
                                    :key="event.id"
                                    class="border-b">
                                    <td class="px-4 py-2">{{ event.id }}</td>
                                    <td class="px-4 py-2">{{ event.name }}</td>
                                    <td class="px-4 py-2">{{ event.eventDate }}</td>
                                </tr>
                            </tbody>
                        </table>

                        <!-- Pagination Controls -->
                        <div class="flex items-center justify-center mt-4">
                            <button class="bg-gray-300 text-blueGray-700 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none mx-2"
                                    :disabled="currentUpcomingPage === 1"
                                    @click="prevUpcomingPage">
                                Vorherige Seite
                            </button>
                            <span class="text-blueGray-700 font-bold mx-2">
                                Seite {{ currentUpcomingPage }} von {{ totalUpcomingPages }}
                            </span>
                            <button class="bg-gray-300 text-blueGray-700 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none mx-2"
                                    :disabled="currentUpcomingPage === totalUpcomingPages"
                                    @click="nextUpcomingPage">
                                N�chste Seite
                            </button>
                        </div>
                    </div>
                    <div v-else class="text-center text-blueGray-600 text-lg">
                        Keine Veranstaltungen verf�gbar.
                    </div>
                </div>
            </div>

            <!-- Eigene Tickets -->
            <div class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow rounded-lg bg-white border border-gray-200">
                <div class="rounded-t bg-gray-100 mb-0 px-6 py-4 text-center">
                    <h6 class="text-blueGray-700 text-xl font-bold">Eigene Tickets</h6>
                </div>
                <div class="flex-auto px-4 lg:px-10 py-6">
                    <div v-if="paginatedOwnTickets.length > 0">
                        <table class="table-auto w-full text-left border-collapse">
                            <thead>
                                <tr>
                                    <th class="px-4 py-2 border-b">Ticket-ID</th>
                                    <th class="px-4 py-2 border-b">Event</th>
                                    <th class="px-4 py-2 border-b">Datum</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="ticket in paginatedOwnTickets"
                                    :key="ticket.id"
                                    class="border-b">
                                    <td class="px-4 py-2">{{ ticket.id }}</td>
                                    <td class="px-4 py-2">{{ ticket.name }}</td>
                                    <td class="px-4 py-2">{{ ticket.eventDate }}</td>
                                </tr>
                            </tbody>
                        </table>

                        <!-- Pagination Controls -->
                        <div class="flex items-center justify-center mt-4">
                            <button class="bg-gray-300 text-blueGray-700 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none mx-2"
                                    :disabled="currentTicketsPage === 1"
                                    @click="prevTicketsPage">
                                Vorherige Seite
                            </button>
                            <span class="text-blueGray-700 font-bold mx-2">
                                Seite {{ currentTicketsPage }} von {{ totalTicketsPages }}
                            </span>
                            <button class="bg-gray-300 text-blueGray-700 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none mx-2"
                                    :disabled="currentTicketsPage === totalTicketsPages"
                                    @click="nextTicketsPage">
                                Nächste Seite
                            </button>
                        </div>
                    </div>
                    <div v-else class="text-center text-blueGray-600 text-lg">
                        Keine Tickets verfügbar.
                    </div>
                </div>
            </div>
        </div>

        <!-- Buttons -->
        <div class="flex justify-between w-full max-w-6xl mt-6 px-4">
            <router-link to="/kunde/redeem-voucher"
                         class="bg-green-500 text-white active:bg-green-600 font-bold uppercase text-xs px-6 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none ease-linear transition-all duration-150">
                Gutschein einlösen
            </router-link>
            <router-link to="/kunde/events"
                         class="bg-blue-500 text-white active:bg-blue-600 font-bold uppercase text-xs px-6 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none ease-linear transition-all duration-150">
                Tickets kaufen
            </router-link>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                upcomingEvents: [
                    { id: 1, name: "Concert A", eventDate: "2024-01-15" },
                    { id: 2, name: "Festival B", eventDate: "2024-02-10" },
                    { id: 3, name: "Play C", eventDate: "2024-01-20" },
                    { id: 4, name: "Workshop D", eventDate: "2024-03-05" },
                    { id: 5, name: "Exhibition E", eventDate: "2024-01-25" },
                    { id: 6, name: "Conference F", eventDate: "2024-02-01" },
                    { id: 7, name: "Event G", eventDate: "2024-03-10" },
                    { id: 8, name: "Show H", eventDate: "2024-01-30" },
                ],
                ownTickets: [
                    { id: 1, name: "Ticket A", eventDate: "2024-01-15" },
                    { id: 2, name: "Ticket B", eventDate: "2024-02-10" },
                    { id: 3, name: "Ticket C", eventDate: "2024-01-20" },
                    { id: 4, name: "Ticket D", eventDate: "2024-03-05" },
                    { id: 5, name: "Ticket E", eventDate: "2024-01-25" },
                ],
                currentUpcomingPage: 1,
                currentTicketsPage: 1,
                itemsPerPage: 5,
            };
        },
        computed: {
            sortedUpcomingEvents() {
                return [...this.upcomingEvents].sort(
                    (a, b) => new Date(a.eventDate) - new Date(b.eventDate)
                );
            },
            sortedOwnTickets() {
                return [...this.ownTickets].sort(
                    (a, b) => new Date(a.eventDate) - new Date(b.eventDate)
                );
            },
            paginatedUpcomingEvents() {
                const start = (this.currentUpcomingPage - 1) * this.itemsPerPage;
                const end = start + this.itemsPerPage;
                return this.sortedUpcomingEvents.slice(start, end);
            },
            paginatedOwnTickets() {
                const start = (this.currentTicketsPage - 1) * this.itemsPerPage;
                const end = start + this.itemsPerPage;
                return this.sortedOwnTickets.slice(start, end);
            },
            totalUpcomingPages() {
                return Math.ceil(this.upcomingEvents.length / this.itemsPerPage);
            },
            totalTicketsPages() {
                return Math.ceil(this.ownTickets.length / this.itemsPerPage);
            },
        },
        methods: {
            prevUpcomingPage() {
                if (this.currentUpcomingPage > 1) {
                    this.currentUpcomingPage--;
                }
            },
            nextUpcomingPage() {
                if (this.currentUpcomingPage < this.totalUpcomingPages) {
                    this.currentUpcomingPage++;
                }
            },
            prevTicketsPage() {
                if (this.currentTicketsPage > 1) {
                    this.currentTicketsPage--;
                }
            },
            nextTicketsPage() {
                if (this.currentTicketsPage < this.totalTicketsPages) {
                    this.currentTicketsPage++;
                }
            },
        },
    };
</script>

<style>
    .min-h-screen {
        min-height: 100vh;
    }

    .table-auto {
        width: 100%;
        border-collapse: collapse;
    }

        .table-auto th,
        .table-auto td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #e2e8f0;
        }

        .table-auto th {
            background-color: #f7fafc;
        }
</style>
