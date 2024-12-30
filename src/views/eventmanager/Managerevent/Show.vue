<template>
    <div class="flex justify-center items-center min-h-screen p-4">
        <!-- Event Karte, auf 50% der Seite begrenzt -->
        <div class="bg-white shadow-lg rounded-lg overflow-hidden max-w-md w-full">
            <div class="bg-blue-500 text-white p-4 rounded-t-lg">
                <!-- Navbar für den Titel -->
                <h2 class="text-2xl font-semibold text-center">{{ event.name || 'Neues Event' }}</h2>
            </div>
            <div class="p-6">
                <!-- Event Details: Datum, Ort, Verfügbare Tickets und Preis -->
                <div class="mt-2">
                    <label for="name">Event Name:</label>
                    <input type="text" id="name" v-model="event.name" class="w-full px-3 py-2 border" placeholder="Event Name" />
                </div>
                <div class="mt-2">
                    <label for="date">Datum:</label>
                    <input type="text" id="date" v-model="event.date" class="w-full px-3 py-2 border" placeholder="Event Date" />
                </div>
                <div class="mt-2">
                    <label for="location">Ort:</label>
                    <input type="text" id="location" v-model="event.location" class="w-full px-3 py-2 border" placeholder="Event Location" />
                </div>
                <div class="mt-2">
                    <label for="price">Preis:</label>
                    <input type="number" id="price" v-model="event.price" class="w-full px-3 py-2 border" placeholder="Event Price" />
                </div>
                <div class="mt-2">
                    <label for="availableTickets">Verfügbare Tickets:</label>
                    <input type="number" id="availableTickets" v-model="event.availableTickets" class="w-full px-3 py-2 border" placeholder="Available Tickets" />
                </div>

                <!-- Buttons für Ticketkauf und Gutscheinverwendung -->
                <div class="flex justify-between mt-6">
                    <button class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 focus:outline-none"
                            @click="saveEvent">
                        <i class="fa fa-save mr-2"></i> Save Event
                    </button>

                    <button class="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600 focus:outline-none"
                            @click="cancelChanges">
                        <i class="fa fa-times mr-2"></i> Cancel
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                event: {
                    name: "",
                    date: "",
                    location: "",
                    price: 0,
                    availableTickets: 0,
                },
            };
        },
        methods: {
            saveEvent() {
                // Save the event (or handle the event creation)
                console.log("Event saved", this.event);
                // You can add logic to save this event to your data
            },
            cancelChanges() {
                // Navigate back to the event list without saving
                this.$router.push('/Kunde/event_index');
            }
        },
        created() {
            // Check if it's a new event or an existing event
            const eventId = this.$route.params.id;
            if (eventId) {
                // Load existing event details based on eventId (if it's provided)
                // For example, fetch event data from an API
                // axios.get(`/api/events/${eventId}`).then(response => {
                //   this.event = response.data;
                // });
            } else {
                // It's a new event, so initialize with empty data
                this.event = {
                    name: "",
                    date: "",
                    location: "",
                    price: 0,
                    availableTickets: 0,
                };
            }
        },
    };
</script>

<style scoped>
    /* Optional Styling für das Event-Kartenlayout */
    .bg-white {
        background-color: white;
    }

    .shadow-lg {
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }

    .rounded-lg {
        border-radius: 8px;
    }

    .overflow-hidden {
        overflow: hidden;
    }

    .text-gray-600 {
        color: #4a4a4a;
    }
</style>
