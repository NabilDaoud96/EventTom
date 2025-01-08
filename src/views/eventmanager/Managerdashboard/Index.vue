<template>
    <div class="flex flex-col items-center justify-start min-h-screen mt-6">
        <!-- Cards Grid -->
        <div class="grid grid-cols-1 sm:grid-cols-1 lg:grid-cols-3 gap-6 w-full px-4">

            <!-- First Card: Create Events -->
            <div class="bg-white shadow-lg rounded-lg p-6 hover:shadow-xl transition duration-150 ease-in-out">
                <div class="text-center">
                    <h2 class="text-xl font-bold text-gray-700">📅 Create Events</h2>
                    <p class="text-sm text-gray-500 mt-2">Manage and create your events easily.</p>
                    <router-link to="/event_manager/event_show">
                        <div class="mt-4">
                            <button class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                                Go to Event Management
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
                    <router-link to="/kunde/Events">
                        <div class="mt-4">
                            <button class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                                View All Events
                            </button>
                        </div>
                    </router-link>
                </div>
            </div>

            <!-- Third Card: Create Voucher -->
            <div class="bg-white shadow-lg rounded-lg p-6 hover:shadow-xl transition duration-150 ease-in-out">
                <div class="text-center">
                    <h2 class="text-xl font-bold text-gray-700">🎁 Create Voucher</h2>
                    <p class="text-sm text-gray-500 mt-2">Generate new vouchers for customers.</p>
                    <router-link to="/event_manager/create_vourcher">
                        <div class="mt-4">
                            <button class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                                Create Voucher
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
            };
        },
        computed: {
            nextEvent() {
                // Determine the next event dynamically by sorting and filtering
                const today = new Date();
                const futureEvents = this.events.filter(event => new Date(event.date) > today);
                return futureEvents.sort((a, b) => new Date(a.date) - new Date(b.date))[0] || {
                    name: "No upcoming events",
                    location: "-",
                    date: "N/A",
                };
            },
        },
    };
</script>

<style scoped>
    /* Add custom styles for consistent card look */
</style>
