# template section
<template>
  <div class="flex flex-col items-center justify-start min-h-screen mt-6">
    <!-- Cards Grid -->
    <div class="grid grid-cols-1 sm:grid-cols-1 lg:grid-cols-3 gap-6 w-full px-4">
      <!-- Create Event Card -->
      <router-link to="/event-creator/create"
                   class="relative flex flex-col min-w-0 break-words bg-white rounded mb-6 xl:mb-0 shadow-lg hover:shadow-xl transition-shadow duration-300">
        <div class="flex-auto p-4">
          <div class="flex flex-wrap">
            <div class="relative w-full pr-4 max-w-full flex-grow flex-1">
              <h5 class="text-blueGray-400 uppercase font-bold text-xs">
                Create New Event
              </h5>
              <span class="font-semibold text-xl text-blueGray-700">
                Start Planning
              </span>
            </div>
            <div class="relative w-auto pl-4 flex-initial">
              <div class="text-white p-3 text-center inline-flex items-center justify-center w-12 h-12 shadow-lg rounded-full bg-blue-500">
                <i class="fas fa-plus"></i>
              </div>
            </div>
          </div>
          <p class="text-sm text-blueGray-400 mt-4">
            <span class="whitespace-nowrap">Click to create a new event</span>
          </p>
        </div>
      </router-link>

      <!-- My Events Card -->
      <router-link to="/event-creator/events"
                   class="relative flex flex-col min-w-0 break-words bg-white rounded mb-6 xl:mb-0 shadow-lg hover:shadow-xl transition-shadow duration-300">
        <div class="flex-auto p-4">
          <div class="flex flex-wrap">
            <div class="relative w-full pr-4 max-w-full flex-grow flex-1">
              <h5 class="text-blueGray-400 uppercase font-bold text-xs">
                My Events
              </h5>
              <span class="font-semibold text-xl text-blueGray-700">
                View All Events
              </span>
            </div>
            <div class="relative w-auto pl-4 flex-initial">
              <div class="text-white p-3 text-center inline-flex items-center justify-center w-12 h-12 shadow-lg rounded-full bg-purple-500">
                <i class="fas fa-list"></i>
              </div>
            </div>
          </div>
          <p class="text-sm text-blueGray-400 mt-4">
            <span class="whitespace-nowrap">Manage your existing events</span>
          </p>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const dashboardInfo = ref(null)

// Currency formatter
const formatCurrency = (value) => {
  return new Intl.NumberFormat('de-DE', {
    style: 'currency',
    currency: 'EUR'
  }).format(value)
}

// You can add your API call here
onMounted(async () => {
  try {
    // Example API call:
    // const response = await axios.get('/api/dashboard-info')
    // dashboardInfo.value = response.data
  } catch (error) {
    console.error('Error fetching dashboard info:', error)
  }
})
</script>