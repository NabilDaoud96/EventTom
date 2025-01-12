# template section
<template>
  <div class="flex flex-col items-center justify-start min-h-screen mt-6">
    <!-- Cards Grid -->
    <div class="grid grid-cols-1 sm:grid-cols-1 lg:grid-cols-3 gap-6 w-full px-4">
      <!-- Booked Events Card -->
      <div class="relative flex flex-col min-w-0 break-words bg-white rounded mb-6 xl:mb-0 shadow-lg">
        <div class="flex-auto p-4">
          <div class="flex flex-wrap">
            <div class="relative w-full pr-4 max-w-full flex-grow flex-1">
              <h5 class="text-blueGray-400 uppercase font-bold text-xs">
                Booked Events
              </h5>
              <span class="font-semibold text-xl text-blueGray-700">
                {{ dashboardInfo?.eventDashboard?.count || 0 }} Events
              </span>
            </div>
            <router-link to="/tickets" class="relative w-auto pl-4 flex-initial">
              <div class="text-white p-3 text-center inline-flex items-center justify-center w-12 h-12 shadow-lg rounded-full bg-emerald-500">
                <i class="fas fa-ticket-alt"></i>
              </div>
            </router-link>
          </div>
          <p class="text-sm text-blueGray-400 mt-4">
            <router-link to="/tickets" class="text-emerald-500 hover:text-emerald-600">
              View your tickets →
            </router-link>
          </p>
        </div>
      </div>

      <!-- Next Event Card -->
      <div class="relative flex flex-col min-w-0 break-words bg-white rounded mb-6 xl:mb-0 shadow-lg">
        <div class="flex-auto p-4">
          <div class="flex flex-wrap">
            <div class="relative w-full pr-4 max-w-full flex-grow flex-1">
              <h5 class="text-blueGray-400 uppercase font-bold text-xs">
                Next Event
              </h5>
              <span class="font-semibold text-xl text-blueGray-700">
                {{ dashboardInfo?.nextEvent?.title || 'No upcoming events' }}
              </span>
            </div>
            <router-link to="/events" class="relative w-auto pl-4 flex-initial">
              <div class="text-white p-3 text-center inline-flex items-center justify-center w-12 h-12 shadow-lg rounded-full bg-red-500">
                <i class="fas fa-calendar"></i>
              </div>
            </router-link>
          </div>
          <div v-if="dashboardInfo?.nextEvent" class="text-sm text-blueGray-400 mt-4">
            <p>{{ formatDate(dashboardInfo.nextEvent.dateOfEvent) }}</p>
            <p>{{ dashboardInfo.nextEvent.location }}</p>
            <router-link to="/events" class="text-red-500 hover:text-red-600">
              View all events →
            </router-link>
          </div>
          <p v-else class="text-sm text-blueGray-400 mt-4">
            <router-link to="/events" class="text-red-500 hover:text-red-600">
              Browse available events →
            </router-link>
          </p>
        </div>
      </div>

      <!-- Vouchers Card -->
      <div class="relative flex flex-col min-w-0 break-words bg-white rounded mb-6 xl:mb-0 shadow-lg">
        <div class="flex-auto p-4">
          <div class="flex flex-wrap">
            <div class="relative w-full pr-4 max-w-full flex-grow flex-1">
              <h5 class="text-blueGray-400 uppercase font-bold text-xs">
                Available Vouchers
              </h5>
              <span class="font-semibold text-xl text-blueGray-700">
                {{ formatCurrency(dashboardInfo?.voucherDashboard?.value || 0) }}€
              </span>
            </div>
            <router-link to="/vouchers" class="relative w-auto pl-4 flex-initial">
              <div class="text-white p-3 text-center inline-flex items-center justify-center w-12 h-12 shadow-lg rounded-full bg-blue-500">
                <i class="fas fa-gift"></i>
              </div>
            </router-link>
          </div>
          <p class="text-sm text-blueGray-400 mt-4">
            {{ dashboardInfo?.voucherDashboard?.count || 0 }} vouchers available
            <router-link to="/vouchers" class="block text-blue-500 hover:text-blue-600 mt-1">
              View your vouchers →
            </router-link>
          </p>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useDashboard } from "@/composables/useDashboard"

const { error, loading, getDashboardInformation } = useDashboard()
const dashboardInfo = ref(null)

// Format currency function
const formatCurrency = (value) => {
  return Number(value).toFixed(2)
}

// Format date function
const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return new Intl.DateTimeFormat('en-GB', {
    day: 'numeric',
    month: 'short',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  }).format(date)
}

onMounted(async () => {
  try {
    dashboardInfo.value = await getDashboardInformation()
  } catch (err) {
    console.error('Error fetching dashboard information:', err)
  }
})
</script>