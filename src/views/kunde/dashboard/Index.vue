<template>
  <div class="flex flex-col items-center justify-start min-h-screen mt-6">
    <!-- Cards Grid -->
    <div class="grid grid-cols-1 sm:grid-cols-1 lg:grid-cols-3 gap-6 w-full px-4">

      <!-- First Card: Booked Events -->
      <div class="bg-white shadow-lg rounded-lg p-6 hover:shadow-xl transition duration-150 ease-in-out">
        <div class="text-center">
          <h2 class="text-xl font-bold text-gray-700">🎫 Booked Events</h2>
          <p class="text-sm text-gray-500 mt-2">
            You have <br> {{ dashboardInfo?.eventDashboard?.count || 0 }} events booked.
          </p>
          <router-link to="/tickets">
            <div class="mt-4">
              <button class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                Show my tickets
              </button>
            </div>
          </router-link>
        </div>
      </div>

      <!-- Second Card: Next Event -->
      <div class="bg-white shadow-lg rounded-lg p-6 hover:shadow-xl transition duration-150 ease-in-out">
        <div class="text-center">
          <h2 class="text-xl font-bold text-gray-700">⏳ My Next Event</h2>
          <div v-if="dashboardInfo?.nextEvent" class="text-sm text-gray-500 mt-2">
            <p class="font-medium text-gray-700">{{ dashboardInfo.nextEvent.title }}</p>
            <p>{{ formatDate(dashboardInfo.nextEvent.dateOfEvent) }}</p>
            <p>{{ dashboardInfo.nextEvent.location }}</p>
          </div>
          <p v-else class="text-sm text-gray-500 mt-2">
            No upcoming events
          </p>
          <router-link to="/events">
            <div class="mt-4">
              <button class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                View all Events
              </button>
            </div>
          </router-link>
        </div>
      </div>

      <!-- Third Card: Vouchers -->
      <div class="bg-white shadow-lg rounded-lg p-6 hover:shadow-xl transition duration-150 ease-in-out">
        <div class="text-center">
          <h2 class="text-xl font-bold text-gray-700">🎁 Vouchers</h2>
          <p class="text-sm text-gray-500 mt-2">
            {{ dashboardInfo?.voucherDashboard?.count || 0 }} Voucher available <br>
            Total Voucher Value: {{ formatCurrency(dashboardInfo?.voucherDashboard?.value || 0) }}€
          </p>
          <router-link to="/vouchers">
            <div class="mt-4">
              <button class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                Use Vouchers
              </button>
            </div>
          </router-link>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useDashboard } from "@/composables/useDashboard";

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