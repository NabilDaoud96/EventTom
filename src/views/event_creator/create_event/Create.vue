<template>
    <div class="flex items-center justify-center min-h-screen">
      <div class="w-full max-w-lg px-4">
        <div class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-blueGray-100 border-0">
        
        <div class="rounded-t bg-white mb-0 px-6 py-6">
          <div class="flex justify-center -mt-10 mb-3">
            <div class="w-20 h-20 bg-blueGray-200 inline-flex items-center justify-center rounded-full shadow-lg">
              <img
                alt="Event Picture"
                class="w-full h-full rounded-full align-middle"
                :src="eventPicture"
              />
            </div>
          </div>
  
            <div class="text-center">
              <h6 class="text-blueGray-700 text-xl font-bold">Create Event</h6>
            </div>
          </div>
          <div class="flex-auto px-4 lg:px-10 py-10 pt-0">
            <form>
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
                      v-model="event.title"
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
                      v-model="event.location"
                      class="border-0 px-3 py-3 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                    />
                  </div>
                </div>
                <div class="w-full lg:w-6/12 px-4">
                  <div class="relative w-full mb-3">
                    <label
                      class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                      for="Date"
                    >
                      Date
                    </label>
                    <input
                      id="Date"
                      type="Date"
                      v-model="event.Date"
                      class="border-0 px-3 py-3 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                    />
                  </div>
                </div>
                <div class="w-full lg:w-6/12 px-4">
                  <div class="relative w-full mb-3">
                    <label
                      class="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                      for="password"
                    >
                      Password
                    </label>
                    <input
                      id="password"
                      type="text"
                      v-model="event.password"
                      class="border-0 px-3 py-3 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                    />
                  </div>
                </div>
              </div>
  
              <div class="mt-6 mr-4 text-right">
                <button
                  class="bg-emerald-500 text-white active:bg-emerald-600 font-bold uppercase text-xs px-4 py-2 rounded shadow hover:shadow-md outline-none focus:outline-none ease-linear transition-all duration-150"
                  type="button"
                >
                  Save
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import image from "@/assets/img/events-1.jpg"; // Default event Image
  
  export default {
    data() {
      return {
        event: {
          title: 'Circus',
          location: 'Berlin',
          Date: '5/1/2025',
        },
        eventPicture: image, // Default event image
      };
    },
  };
  </script>
  
  <style>
  .min-h-screen {
    min-height: 100vh;
  }
  
  .w-16 {
    width: 4rem; /* Avatar width */
  }
  
  .h-16 {
    height: 4rem; /* Avatar height */
    }
  
  </style>
  