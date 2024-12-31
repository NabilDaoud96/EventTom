<template>
  <div>
    <event-manager-sidebar />
    <div class="relative md:ml-64 bg-blueGray-100">
      <event-manager-navbar />
      <div class="mt-4 px-4 md:px-10 mx-auto w-full -m-24">
        <router-view />
        <event-manager-footer />
      </div>
    </div>
  </div>
</template>
<script>

import EventManagerNavbar from "../components/Navbars/EventManagerNavbar.vue";
import EventManagerSidebar from "../components/Sidebars/EventMangerSidebar.vue";
import EventManagerFooter from "../components/Footers/EventManagerFooter.vue";

export default {
  name: "kunde-layout",
  components: {
    EventManagerNavbar,
    EventManagerSidebar,
    EventManagerFooter,
  },
};
</script>
