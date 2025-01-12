<template>
  <div class="flex items-center justify-center">
    <div class="w-full px-4">
      <div class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-blueGray-100 border-0">
        <div class="rounded-t bg-white mb-0 px-6 py-6">
          <div class="text-center">
            <h6 class="text-blueGray-700 text-xl font-bold">Update Event</h6>
          </div>
        </div>

        <div class="flex-auto px-4 lg:px-10 py-10 pt-0">
          <form @submit.prevent="handleSubmit">
            <h6 class="text-blueGray-400 text-sm mt-3 mb-6 font-bold uppercase">
              Event Information
            </h6>
            <div class="flex flex-wrap">
              <div class="w-full lg:w-6/12 px-4">
                <div class="relative w-full mb-3">
                  <label
                      class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                      for="title"
                  >
                    Title
                  </label>
                  <input
                      id="title"
                      type="text"
                      v-model="formData.title"
                      maxlength="100"
                      required
                      class="border-0 px-3 py-3 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  />
                </div>
              </div>
              <div class="w-full lg:w-6/12 px-4">
                <div class="relative w-full mb-3">
                  <label
                      class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                      for="location"
                  >
                    Location
                  </label>
                  <input
                      id="location"
                      type="text"
                      v-model="formData.location"
                      required
                      class="border-0 px-3 py-3 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  />
                </div>
              </div>
              <div class="w-full lg:w-6/12 px-4">
                <div class="relative w-full mb-3">
                  <label
                      class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                      for="date"
                  >
                    Date & Time
                  </label>
                  <input
                      id="date"
                      type="datetime-local"
                      v-model="formData.dateOfEvent"
                      :min="minDateTime"
                      :max="maxDateTime"
                      required
                      class="border-0 px-3 py-3 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150 cursor-pointer"
                  />
                  <p v-if="dateError" class="text-red-500 text-xs mt-1">
                    {{ dateError }}
                  </p>
                </div>
              </div>
              <div class="w-full lg:w-6/12 px-4">
                <div class="relative w-full mb-3">
                  <label
                      class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                      for="tickets"
                  >
                    Total Tickets
                  </label>
                  <input
                      id="tickets"
                      type="number"
                      v-model.number="formData.totalTickets"
                      min="1"
                      max="100000"
                      required
                      class="border-0 px-3 py-3 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  />
                </div>
              </div>
              <div class="w-full lg:w-6/12 px-4">
                <div class="relative w-full mb-3">
                  <label
                      class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                      for="threshold"
                  >
                    Threshold Value
                  </label>
                  <input
                      id="threshold"
                      type="number"
                      v-model.number="formData.thresholdValue"
                      min="0"
                      required
                      class="border-0 px-3 py-3 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  />
                </div>
              </div>
              <div class="w-full lg:w-6/12 px-4">
                <div class="relative w-full mb-3">
                  <label
                      class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                      for="price"
                  >
                    Base Price
                  </label>
                  <input
                      id="price"
                      type="number"
                      v-model.number="formData.basePrice"
                      min="0.01"
                      step="0.01"
                      required
                      class="border-0 px-3 py-3 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  />
                </div>
              </div>
              <div class="w-full lg:w-6/12 px-4">
                <div class="relative w-full mb-3">
                  <label
                      class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                      for="managers"
                  >
                    Event Managers
                  </label>
                  <select
                      id="managers"
                      v-model="formData.managerIds"
                      multiple
                      required
                      class="border-0 px-3 py-3 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150 h-32"
                  >
                    <option v-for="manager in eventManagers" :key="manager.id" :value="manager.id">
                      {{ manager.firstName }} {{ manager.lastName }} ({{ manager.employeeNumber }})
                    </option>
                  </select>
                  <p class="text-xs text-blueGray-400 mt-1">Hold Ctrl/Cmd to select multiple managers</p>
                </div>
              </div>
            </div>

            <div class="mt-6 text-right">
              <button
                  type="submit"
                  class="bg-emerald-500 text-white active:bg-emerald-600 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none mr-2 ease-linear transition-all duration-150"
              >
                Update Event
              </button>
              <button
                  type="button"
                  @click="$router.back()"
                  class="bg-blueGray-500 text-white active:bg-blueGray-600 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none ease-linear transition-all duration-150"
              >
                Cancel
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useManager } from "@/composables/useManager";
import { useEvent } from "@/composables/useEvent";
import { onMounted, ref, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';

export default {
  name: 'UpdateEventForm',
  setup() {
    const route = useRoute();
    const router = useRouter();
    const eventManagers = ref([]);
    const dateError = ref('');
    const { error: managerError, loading: managerLoading, getEventManagers } = useManager();
    const { error: eventError, loading: eventLoading, getEvent, updateEvent } = useEvent();

    const formData = ref({
      id: '',
      title: '',
      location: '',
      dateOfEvent: '',
      totalTickets: null,
      thresholdValue: 0,
      basePrice: null,
      managerIds: []
    });

    // Get current date and format it for datetime-local min attribute
    const minDateTime = computed(() => {
      const now = new Date();
      now.setFullYear(now.getFullYear());
      now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
      return now.toISOString().slice(0, 16);
    });

    // Set max date to 3 years from now
    const maxDateTime = computed(() => {
      const maxDate = new Date();
      maxDate.setFullYear(maxDate.getFullYear() + 3);
      maxDate.setMinutes(maxDate.getMinutes() - maxDate.getTimezoneOffset());
      return maxDate.toISOString().slice(0, 16);
    });

    const fetchEventManagers = async () => {
      try {
        eventManagers.value = await getEventManagers();
      } catch (err) {
        console.error('Error fetching event managers:', err);
      }
    };

    const fetchEvent = async () => {
      try {
        const eventData = await getEvent(route.params.id);
        if (eventData) {
          // Format the date to work with datetime-local input
          const dateObj = new Date(eventData.dateOfEvent);
          dateObj.setMinutes(dateObj.getMinutes() - dateObj.getTimezoneOffset());
          const formattedDate = dateObj.toISOString().slice(0, 16);

          // Round the base price to 2 decimal places
          const roundedBasePrice = eventData.basePrice ? Number(eventData.basePrice.toFixed(2)) : null;

          formData.value = {
            ...eventData,
            dateOfEvent: formattedDate,
            basePrice: roundedBasePrice
          };
        }
      } catch (err) {
        console.error('Error fetching event:', err);
      }
    };

    const handleSubmit = async () => {
      if (dateError.value) {
        return;
      }

      try {
        const dateTime = new Date(formData.value.dateOfEvent);
        const isoDateTime = dateTime.toISOString().slice(0, 19);

        const eventData = {
          ...formData.value,
          dateOfEvent: isoDateTime,
          basePrice: Number(formData.value.basePrice.toFixed(2))
        };

        await updateEvent(formData.value.id, eventData);
      } catch (err) {
        console.error('Error updating event:', err);
      }
    };

    onMounted(() => {
      fetchEventManagers();
      fetchEvent();
    });

    return {
      formData,
      eventManagers,
      managerError,
      eventError,
      managerLoading,
      eventLoading,
      minDateTime,
      maxDateTime,
      dateError,
      handleSubmit
    };
  }
}
</script>