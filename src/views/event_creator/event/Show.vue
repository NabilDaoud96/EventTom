<template>
  <div class="p-6 w-full max-w-4xl mx-auto">
    <div v-if="loading" class="flex justify-center items-center">
      <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"></div>
    </div>

    <div v-else-if="error" class="p-4 bg-red-100 border border-red-400 text-red-700 rounded-md">
      {{ error }}
    </div>

    <div v-else-if="event" class="bg-white rounded-lg shadow-lg overflow-hidden">
      <!-- Header Section -->
      <div class="p-6 border-b border-gray-200 bg-gray-50">
        <h1 class="text-3xl font-bold text-gray-900">{{ event.title }}</h1>
      </div>

      <!-- Main Content Section -->
      <div class="p-6 space-y-6">
        <!-- Date and Location Section -->
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div class="space-y-3">
            <div class="flex items-center text-gray-600">
              <i class="fas fa-calendar-alt mr-3 text-xl"></i>
              <div>
                <div class="text-lg">{{ formatDate(event.dateOfEvent) }}</div>
                <div class="text-sm text-gray-500">Datum der Veranstaltung</div>
              </div>
            </div>
          </div>

          <div class="space-y-3">
            <div class="flex items-center text-gray-600">
              <i class="fas fa-map-marker-alt mr-3 text-xl"></i>
              <div>
                <div class="text-lg">{{ event.location }}</div>
                <div class="text-sm text-gray-500">Veranstaltungsort</div>
              </div>
            </div>
          </div>
        </div>

        <!-- Price Information -->
        <div class="bg-gray-50 rounded-lg p-6 space-y-3">
          <div class="flex items-center justify-between">
            <div class="space-y-1">
              <div class="flex items-center font-semibold text-xl">
                <i class="fas fa-tag mr-2"></i>
                <span>{{ formatPrice(event.price) }}</span>
              </div>
              <div class="text-gray-500 text-sm ml-6">
                Grundpreis: {{ formatPrice(event.basePrice) }}
              </div>
            </div>
          </div>
        </div>

        <!-- Ticket Information -->
        <div class="bg-gray-50 rounded-lg p-6">
          <h2 class="text-xl font-semibold mb-4">Ticket Status</h2>

          <div class="space-y-4">
            <!-- Available Tickets -->
            <div class="flex items-center justify-between">
              <span class="text-gray-600">Verfügbare Tickets:</span>
              <span
                  v-if="event.availableTickets > 0"
                  class="text-emerald-500 font-semibold text-lg"
              >
                {{ event.availableTickets }} Tickets
              </span>
              <span
                  v-else
                  class="text-red-500 font-semibold text-lg"
              >
                Ausverkauft
              </span>
            </div>

            <!-- Sold Tickets -->
            <div class="flex items-center justify-between">
              <span class="text-gray-600">Verkaufte Tickets:</span>
              <span class="font-semibold text-lg">{{ event.soldTickets }}</span>
            </div>

            <!-- Threshold Information -->
            <div class="space-y-2">
              <div class="flex justify-between text-gray-600">
                <span>Schwellenwert:</span>
                <span class="font-semibold">{{ event.thresholdValue }}</span>
              </div>

              <div
                  class="flex items-center justify-end space-x-2"
                  :class="getThresholdClass(event)"
              >
                <i
                    class="fas text-lg"
                    :class="getThresholdIcon(event)"
                ></i>
                <span class="font-semibold">{{ getThresholdMessage(event) }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Action Buttons -->
      <div class="p-6 bg-gray-50 border-t border-gray-200 flex justify-end space-x-4">
        <router-link
            :to="{ name: 'UpdateEventForm', params: { id: event.id } }"
            class="text-white bg-blue-500 hover:bg-blue-600 px-6 py-2 rounded text-sm inline-flex items-center"
        >
          <i class="fas fa-edit mr-2"></i>
          Bearbeiten
        </router-link>

        <button
            @click="handleDelete"
            class="text-white bg-red-500 hover:bg-red-600 px-6 py-2 rounded text-sm inline-flex items-center"
        >
          <i class="fas fa-trash mr-2"></i>
          Löschen
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useEvent } from "@/composables/useEvent";
import { formatDate, formatPrice } from "@/utils/formatter";
import websocketService from "@/utils/websocket";

export default {
  name: 'SingleEventView',

  setup() {
    const route = useRoute();
    const router = useRouter();
    const { error, loading, getEventManaged, deleteEvent } = useEvent();
    const event = ref(null);

    const getThresholdClass = (event) => {
      const ratio = event.soldTickets / event.thresholdValue;
      if (ratio >= 1.1) {
        return 'text-emerald-500';
      } else if (ratio <= 0.9) {
        return 'text-red-500';
      }
      return '';
    };

    const getThresholdIcon = (event) => {
      const ratio = event.soldTickets / event.thresholdValue;
      if (ratio >= 1.1) {
        return 'fa-arrow-trend-up';
      } else if (ratio <= 0.9) {
        return 'fa-arrow-trend-down';
      }
      return 'fa-equals';
    };

    const getThresholdMessage = (event) => {
      const ratio = event.soldTickets / event.thresholdValue;
      const percentage = Math.abs((ratio - 1) * 100).toFixed(1);

      if (ratio >= 1.1) {
        return `${percentage}% über Schwellenwert`;
      } else if (ratio <= 0.9) {
        return `${percentage}% unter Schwellenwert`;
      }
      return 'Nahe am Schwellenwert';
    };

    const fetchEvent = async () => {
      try {
        event.value = await getEventManaged(route.params.id);
      } catch (err) {
        console.error("Error loading event:", err);
      }
    };

    const handleDelete = async () => {
      try {
        const confirmed = await confirm('Sind Sie sicher, dass Sie diese Veranstaltung löschen möchten?');

        if (confirmed) {
          await deleteEvent(route.params.id);
          router.push({ name: 'EventList' });
        }
      } catch (error) {
        console.error("Error deleting event:", error);
      }
    };

    const setupWebSocketListener = () => {
      const eventUpdateUnsub = websocketService.on('eventUpdate', (updatedEvent) => {
        if (updatedEvent.id.toString() === route.params.id.toString()) {
          event.value = { ...event.value, ...updatedEvent };
        }
      });

      return eventUpdateUnsub;
    };

    onMounted(async () => {
      await fetchEvent();

      try {
        await websocketService.connect();
        const unsubCallback = setupWebSocketListener();

        onBeforeUnmount(() => {
          unsubCallback();
          websocketService.disconnect();
        });
      } catch (error) {
        console.error('Failed to connect to WebSocket:', error);
      }
    });

    return {
      event,
      error,
      loading,
      formatDate,
      formatPrice,
      getThresholdClass,
      getThresholdIcon,
      getThresholdMessage,
      handleDelete
    };
  }
};
</script>