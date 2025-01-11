<template>
  <div class="flex flex-col items-center justify-start min-h-screen mt-6">
    <!-- Cards Grid -->
    <div class="grid grid-cols-1 sm:grid-cols-1 lg:grid-cols-3 gap-6 w-full px-4">
      
      <!-- First Card: Booked Events -->
      <div class="bg-white shadow-lg rounded-lg p-6 hover:shadow-xl transition duration-150 ease-in-out">
        <div class="text-center">
          <h2 class="text-xl font-bold text-gray-700">🎫 Booked Events</h2>
          <p class="text-sm text-gray-500 mt-2">You have <br> {{ events.length }} events booked.</p>
          <router-link
              to="/kunde/tickets"
            >
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
          <h2 class="text-xl font-bold text-gray-700">⏳ Next Event</h2>
          <p class="text-sm text-gray-500 mt-2">
            Next event: {{ nextEvent.name }} in {{ nextEvent.location }} on {{ nextEvent.date }}.
          </p>
          <router-link
              to="/kunde/Events"
            >
            <div class="mt-4">
                <button class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                View all Events
                </button>
            </div>
          </router-link>    
        </div>
      </div>

      <!-- Third Card: Vouchers (Only Show if Available) -->
      <div v-if="vouchers.length > 0" class="bg-white shadow-lg rounded-lg p-6 hover:shadow-xl transition duration-150 ease-in-out">
        <div class="text-center">
          <h2 class="text-xl font-bold text-gray-700">🎁 Vouchers</h2>
          <p class="text-sm text-gray-500 mt-2">2 Voucher available <br>Total Voucher Value: {{ totalVoucherValue }}€</p>
          <router-link
              to="/kunde/buy_ticket"
            >
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
/* eslint-disable no-unused-vars */

import {computed, onMounted, ref} from 'vue'
  import { useStore } from 'vuex'
  import {useAuth} from "@/composables/useAuth";

  const { checkAuth } = useAuth()
  const store = useStore()

  const events = ref([
  { id: 1, name: "Concert", location: "Berlin", date: "2024-01-15" },
  { id: 2, name: "Play", location: "Munich", date: "2024-02-20" },
  { id: 3, name: "Theater", location: "Hamburg", date: "2024-03-10" },
  ])
  const vouchers = ref([{ value: 10 }, { value: 10 }])

  const nextEvent = computed(() => events.value[0])
  const totalVoucherValue = computed(() =>
  vouchers.value.reduce((sum, voucher) => sum + voucher.value, 0)
  )

  // Vuex store access
  const isAuthenticated = computed(() => store.getters['auth/isAuthenticated'])
  const userProfile = computed(() => store.getters['auth/userProfile'])

  const logUserInfo = () => {
  console.log('User authenticated:', store.getters['auth/isAuthenticated'])
  console.log('User profile:', store.getters['auth/userProfile'])
}

onMounted(async () => {
  const isAuth = await checkAuth()

})
</script>