// DynamicLayout.vue
<template>
  <div>
    <component :is="currentSidebar" />
    <div class="relative md:ml-64 bg-blueGray-100">
      <component :is="currentNavbar" />
      <div class="mt-4 px-4 md:px-10 mx-auto w-full -m-24">
        <router-view></router-view>
        <component :is="currentFooter" />
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent, computed } from 'vue'
import { useStore } from 'vuex'

import KundeNavbar from "@/components/Navbars/KundeNavbar.vue"
import KundeSidebar from "@/components/Sidebars/KundeSidebar.vue"
import FooterKunde from "@/components/Footers/FooterKunde.vue"

import EventCreatorNavbar from "@/components/Navbars/EventCreatorNavbar.vue"
import EventCreatorSidebar from "@/components/Sidebars/EventCreatorSidebar.vue"
import FooterEventCreator from "@/components/Footers/FooterEventCreator.vue"

export default defineComponent({
  name: "DynamicLayout",
  components: {
    KundeNavbar,
    KundeSidebar,
    FooterKunde,
    EventCreatorNavbar,
    EventCreatorSidebar,
    FooterEventCreator,
  },
  setup() {
    const store = useStore()

    const userRoles = computed(() => store.getters['auth/userRoles'])

    const currentSidebar = computed(() =>
        userRoles.value.includes('ROLE_EVENT_CREATOR') ? 'EventCreatorSidebar' : 'KundeSidebar'
    )

    const currentNavbar = computed(() =>
        userRoles.value.includes('ROLE_EVENT_CREATOR') ? 'EventCreatorNavbar' : 'KundeNavbar'
    )

    const currentFooter = computed(() =>
        userRoles.value.includes('ROLE_EVENT_CREATOR') ? 'FooterEventCreator' : 'FooterKunde'
    )

    return {
      currentSidebar,
      currentNavbar,
      currentFooter
    }
  }
})
</script>