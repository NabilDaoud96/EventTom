<template>
    <div class="p-6">
        <div class="flex justify-between items-center mb-4">
            <h2 class="text-2xl font-semibold">All Events</h2>
            <!-- Green Plus Button to Add New Event -->
            <router-link to="/Kunde/event_show"
                         class="bg-green-500 text-white px-4 py-2 rounded-full hover:bg-green-600 focus:outline-none flex items-center">
                <i class="fa fa-plus"></i> Add Event
            </router-link>
        </div>

        <!-- Table -->
        <table class="min-w-full table-auto border-collapse border border-gray-200">
            <thead>
                <tr class="bg-gray-100">
                    <th class="px-4 py-2 border text-left">Event Name</th>
                    <th class="px-4 py-2 border text-left">Datum</th>
                    <th class="px-4 py-2 border text-left">Ort</th>
                    <th class="px-4 py-2 border text-left">Preis</th>
                    <th class="px-4 py-2 border text-left">Verfügbare Tickets</th>
                    <th class="px-4 py-2 border text-left">Aktionen</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="event in paginatedEvents" :key="event.id">
                    <td class="px-4 py-2 border">{{ event.name }}</td>
                    <td class="px-4 py-2 border">{{ event.date }}</td>
                    <td class="px-4 py-2 border">{{ event.location }}</td>
                    <td class="px-4 py-2 border">{{ event.price }}€</td>
                    <td class="px-4 py-2 border">
                        {{ event.availableTickets === 0 ? "Ausverkauft" : event.availableTickets }}
                    </td>
                    <td class="px-6 py-2 border text-center flex space-x-2 justify-center">
                        <!-- Show Event Link -->
                        <router-link :to="{ name: 'EventShow', params: { id: event.id } }"
                                     class="px-1 py-1 bg-blue-500 text-white text-sm rounded hover:bg-blue-600 focus:outline-none flex items-center">
                            <i class="fa fa-eye"></i>
                        </router-link>
                    </td>
                </tr>
            </tbody>
        </table>

        <!-- Pagination Controls -->
        <div class="pagination-container flex justify-center items-center mt-4 space-x-2">
            <!-- Previous Arrow -->
            <button class="px-2 py-1 bg-gray-300 rounded hover:bg-gray-400 disabled:opacity-50"
                    @click="prevPage"
                    :disabled="currentPage === 1">
                &larr;
            </button>

            <!-- Page Numbers -->
            <button v-for="page in totalPages"
                    :key="page"
                    @click="goToPage(page)"
                    class="px-3 py-1 rounded border"
                    :class="{
          'bg-blue-500 text-white': currentPage === page,
          'bg-gray-300 hover:bg-gray-400': currentPage !== page,
        }">
                {{ page }}
            </button>

            <!-- Next Arrow -->
            <button class="px-2 py-1 bg-gray-300 rounded hover:bg-gray-400 disabled:opacity-50"
                    @click="nextPage"
                    :disabled="currentPage === totalPages">
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
            // Update Event Details
            updateEvent(updatedEvent) {
                const index = this.events.findIndex(event => event.id === updatedEvent.id);
                if (index !== -1) {
                    this.events[index] = updatedEvent;
                }
            },
            // Delete Event
            deleteEvent(eventId) {
                this.events = this.events.filter(event => event.id !== eventId);
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
                        const availableTickets = Math.floor(Math.random() * 20); // Random number of tickets
                        return {
                            id: item.id,
                            name: item.title.split(" ").slice(0, 5).join(" "),
                            date: eventDate.toLocaleDateString('en-GB').replace(/\//g, '-'),
                            location: cities[Math.floor(Math.random() * cities.length)], // Random city from the list
                            price: (Math.random() * 100).toFixed(2),
                            availableTickets,
                        };
                    });
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

    .bg-green-500 {
        background-color: #48bb78;
    }

    .bg-green-600 {
        background-color: #38a169;
    }
</style>
