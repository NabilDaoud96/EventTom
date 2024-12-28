<template>
  <div class="container mx-auto px-4 h-full">
    <div class="flex content-center items-center justify-center h-full">
      <div class="w-full lg:w-6/12 px-4">
        <div class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-blueGray-200 border-0">
          <div class="rounded-t mb-0 px-6 py-6">
            <div class="text-center mb-3">
              <h6 class="text-blueGray-500 text-sm font-bold">Buy Tickets</h6>
            </div>
          </div>
          <div class="flex-auto px-4 lg:px-10 py-10 pt-0">
            <div class="text-blueGray-400 text-center mb-3 font-bold">
              <small>Choose an Event to Buy Tickets</small>
            </div>
            <!-- Example button for each event -->
            <div class="text-center">
              <button
                class="bg-blueGray-800 text-white font-bold px-6 py-3 rounded shadow hover:shadow-md transition-all duration-150"
                type="button"
              >
                Buy Ticket for Event ABC
              </button>
            </div>
            <div class="text-center mt-4">
              <button
                class="bg-blueGray-800 text-white font-bold px-6 py-3 rounded shadow hover:shadow-md transition-all duration-150"
                type="button"
              >
                Buy Ticket for Event XYZ
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BuyTickets',
};
</script>

<style scoped>
/* Styles for BuyTickets page */
</style>
