<template>
  <div>
    <a
        class="text-blueGray-500 block"
        href="#pablo"
        ref="btnDropdownRef"
        @click="toggleDropdown($event)"
    >
      <div class="items-center flex">
        <span
            class="w-12 h-12 text-sm text-white bg-blueGray-200 inline-flex items-center justify-center rounded-full"
        >
          <img
              alt="..."
              class="w-full rounded-full align-middle border-none shadow-lg"
              :src="image"
          />
        </span>
      </div>
    </a>
    <div
        ref="popoverDropdownRef"
        class="bg-white text-base z-50 float-left py-2 list-none text-left rounded shadow-lg min-w-48"
        :class="{
        hidden: !dropdownPopoverShow,
        block: dropdownPopoverShow,
      }"
    >
      <router-link
          class="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-blueGray-700"
          to="/kunde/profile"
      >
        Profile
      </router-link>
      <router-link
          class="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-blueGray-700"
          to="/kunde/events"
      >
        Buy Ticket
      </router-link>
      <router-link
          class="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-blueGray-700"
          to="/kunde/tickets"
      >
        Purchased Tickets
      </router-link>
      <div class="h-0 my-2 border border-solid border-blueGray-100" />
      <a
          href="#"
          @click.prevent="handleLogout"
          class="text-sm py-2 px-4 font-normal block w-full whitespace-nowrap bg-transparent text-blueGray-700"
      >
        Logout
      </a>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { createPopper } from "@popperjs/core"
import { useAuth } from '@/composables/useAuth'
import { useRouter } from 'vue-router'

const router = useRouter()
const { logout } = useAuth()

const dropdownPopoverShow = ref(false)
const btnDropdownRef = ref(null)
const popoverDropdownRef = ref(null)

// eslint-disable-next-line no-unused-vars
const toggleDropdown = (event) => {
  event.preventDefault()
  dropdownPopoverShow.value = !dropdownPopoverShow.value

  if (dropdownPopoverShow.value) {
    createPopper(btnDropdownRef.value, popoverDropdownRef.value, {
      placement: "bottom-start",
    })
  }
}

// eslint-disable-next-line no-unused-vars
const handleLogout = async () => {
  try {
    await logout()
    dropdownPopoverShow.value = false
    await router.push('/auth/login')
  } catch (error) {
    console.error('Logout failed:', error)
  }
}
</script>