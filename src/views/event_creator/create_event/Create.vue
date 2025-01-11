# EventForm.vue
<template>
  <div class="flex items-center justify-center ">
    <div class="w-full px-4">
      <div class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-blueGray-100 border-0">
        <div class="rounded-t bg-white mb-0 px-6 py-6">

          <div class="text-center">
            <h6 class="text-blueGray-700 text-xl font-bold">Create Event</h6>
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
                      required
                      class="border-0 px-3 py-3 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  />
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
            </div>

            <div class="mt-6 text-right">
              <button
                  type="submit"
                  class="bg-emerald-500 text-white active:bg-emerald-600 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none ease-linear transition-all duration-150"
              >
                Create Event
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/utils/axios-auth";

export default {
  name: 'EventForm',
  data() {
    return {
      formData: {
        title: '',
        location: '',
        dateOfEvent: '',
        totalTickets: null,
        thresholdValue: 0,
        basePrice: null,
        managerIds: []
      }
    }
  },
  methods: {
    handleSubmit() {
      const dateTime = new Date(this.formData.dateOfEvent)
      const isoDateTime = dateTime.toISOString().slice(0, 19)

      const eventData = {
        ...this.formData,
        dateOfEvent: isoDateTime,
      }

      api.post('events/create', eventData)
          .then(response => {
            this.resetForm()
            this.$emit('event-created', response.data)
          })
          .catch(error => {
            console.error('Error creating event:', error)
          })
    },
    resetForm() {
      this.formData = {
        title: '',
        location: '',
        dateOfEvent: '',
        totalTickets: null,
        thresholdValue: 0,
        basePrice: null,
        managerIds: []
      }
    }
  }
}
</script>