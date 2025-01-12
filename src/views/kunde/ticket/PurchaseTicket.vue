<template>
  <div class="min-h-screen bg-gray-100 py-12">
    <div class="container mx-auto px-4 sm:px-6 lg:px-8 max-w-7xl">
      <!-- Loading State -->
      <div v-if="loading" class="flex justify-center items-center py-16">
        <div class="animate-spin rounded-full h-12 w-12 border-b-4 border-indigo-600"></div>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="bg-red-50 border-l-4 border-red-400 shadow-lg rounded-lg p-6 mt-4">
        <div class="flex items-center">
          <div class="flex-shrink-0">
            <svg class="h-5 w-5 text-red-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
              <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"/>
            </svg>
          </div>
          <div class="ml-3">
            <h3 class="text-sm font-medium text-red-800">Error loading event</h3>
            <div class="mt-2 text-sm text-red-700">{{ error }}</div>
          </div>
        </div>
      </div>

      <!-- Main Content -->
      <div v-else class="bg-white shadow-xl rounded-md overflow-hidden">


        <div class="px-6 py-8 space-y-8">
          <div class="bg-white rounded-xl shadow-lg p-6 border border-gray-100">
            <h3 class="text-xl font-semibold text-gray-900 mb-6">Event Details</h3>
            <div class="grid grid-cols-1 gap-6 sm:grid-cols-3">
              <div class="space-y-2">
                <label class="block text-sm font-medium text-gray-500">Event Name</label>
                <p class="text-lg font-medium text-gray-900">{{ event?.title }}</p>
              </div>
              <div class="space-y-2">
                <label class="block text-sm font-medium text-gray-500">Date</label>
                <p class="text-lg font-medium text-gray-900">{{ event?.dateOfEvent || 'TBD' }}</p>
              </div>
              <div class="space-y-2">
                <label class="block text-sm font-medium text-gray-500">Price</label>
                <p class="text-lg font-medium text-indigo-600">{{ event?.basePrice }} $</p>
              </div>
            </div>
          </div>

          <!-- Customer Details -->
          <div class="bg-white rounded-xl shadow-lg p-6 border border-gray-100">
            <h3 class="text-xl font-semibold text-gray-900 mb-6">Customer Details</h3>
            <div v-if="customerLoading" class="animate-pulse flex space-x-4">
              <div class="flex-1 space-y-4 py-1">
                <div class="h-4 bg-gray-200 rounded w-3/4"></div>
                <div class="space-y-2">
                  <div class="h-4 bg-gray-200 rounded"></div>
                  <div class="h-4 bg-gray-200 rounded w-5/6"></div>
                </div>
              </div>
            </div>
            <div v-else-if="customerError" class="text-sm text-red-600">
              {{ customerError }}
            </div>
            <div v-else-if="customer" class="grid grid-cols-1 gap-6 sm:grid-cols-3">
              <div class="space-y-2">
                <label class="block text-sm font-medium text-gray-500">Customer Number</label>
                <p class="text-lg font-medium text-gray-900">{{ customer.customerNumber }}</p>
              </div>
              <div class="space-y-2">
                <label class="block text-sm font-medium text-gray-500">Full Name</label>
                <p class="text-lg font-medium text-gray-900">{{ customer.user.firstName }} {{ customer.user.lastName }}</p>
              </div>
              <div class="space-y-2">
                <label class="block text-sm font-medium text-gray-500">Email</label>
                <p class="text-lg font-medium text-gray-900">{{ customer.user.email }}</p>
              </div>
            </div>
          </div>

          <!-- Voucher Section -->
          <div class="bg-white rounded-xl shadow-lg p-6 border border-gray-100">
            <h3 class="text-xl font-semibold text-gray-900 mb-6">Vouchers</h3>

            <!-- Available Vouchers -->
            <div class="mt-6" v-if="vouchers && vouchers.length > 0">
              <label class="block text-sm font-medium text-gray-500 mb-4">Available Vouchers</label>
              <div class="space-y-3">
                <div v-for="voucher in unusedVouchers" :key="voucher.voucherCode"
                     class="flex items-center p-4 border rounded-lg hover:bg-gray-50 transition-colors duration-150 shadow-sm">
                  <input
                      type="checkbox"
                      :value="voucher.voucherCode"
                      v-model="formData.voucherCodes"
                      class="h-5 w-5 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                  />
                  <label class="ml-4 flex items-center justify-between flex-grow">
                    <span class="text-sm font-medium text-gray-900">{{ voucher.voucherCode }}</span>
                    <span class="text-sm font-semibold text-indigo-600">{{ voucher.amount }}$</span>
                  </label>
                </div>
              </div>
            </div>

            <!-- Manual Voucher Entry -->
            <div class="mt-6">
              <label class="block text-sm font-medium text-gray-500 mb-2">Add Voucher Code</label>
              <div class="flex rounded-md shadow-sm">
                <input
                    v-model="newVoucherCode"
                    type="text"
                    class="flex-1 min-w-0 block w-full px-4 py-3 rounded-l-md border border-gray-300 focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                    placeholder="Enter voucher code"
                />
                <button
                    @click="addVoucherCode"
                    type="button"
                    class="inline-flex items-center px-6 py-3 border border-transparent text-sm font-medium rounded-r-md text-white bg-indigo-500 hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition-colors duration-150"
                >
                  Apply
                </button>
              </div>
              <p v-if="voucherError" class="mt-2 text-sm text-red-600">{{ voucherError }}</p>
            </div>

            <!-- Selected Vouchers -->
            <div v-if="formData.voucherCodes.length > 0" class="mt-6">
              <label class="block text-sm font-medium text-gray-500 mb-3">Selected Vouchers</label>
              <div class="space-y-2">
                <div v-for="code in formData.voucherCodes" :key="code"
                     class="flex justify-between items-center p-3 bg-indigo-50 rounded-lg border border-indigo-100">
                  <div class="flex items-center space-x-3">
                    <span class="text-sm font-medium text-indigo-900">{{ code }}</span>
                    <span class="text-sm font-semibold text-indigo-600">{{ getVoucherAmount(code) }}$</span>
                  </div>
                  <button
                      @click="removeVoucherCode(code)"
                      class="text-sm font-medium text-indigo-600 hover:text-indigo-800 transition-colors duration-150"
                  >
                    Remove
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- Quantity and Total -->
          <div class="bg-white rounded-xl shadow-lg p-6 border border-gray-100">
            <div class="grid grid-cols-1 gap-6 sm:grid-cols-2">
              <div class="space-y-3">
                <label for="ticketQuantity" class="block text-sm font-medium text-gray-500">Number of Tickets</label>
                <input
                    id="ticketQuantity"
                    type="number"
                    min="1"
                    v-model.number="formData.amount"
                    class="block w-full px-4 py-3 rounded-md border border-gray-300 focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                    required
                />
              </div>
              <div class="space-y-3 bg-gray-50 p-4 rounded-lg">
                <p class="text-sm text-gray-500">Subtotal: <span class="float-right font-medium text-gray-900">{{ calculateSubtotal }}$</span></p>
                <p v-if="totalVoucherAmount > 0" class="text-sm text-indigo-600">
                  Voucher Discount: <span class="float-right font-medium">-{{ totalVoucherAmount }}$</span>
                </p>
                <div class="pt-2 border-t border-gray-200">
                  <p class="text-lg font-semibold text-gray-900">
                    Final Total: <span class="float-right">{{ calculateTotal }}$</span>
                  </p>
                </div>
              </div>
            </div>
          </div>

          <!-- Actions -->
          <div class="flex justify-end space-x-4 pt-6">
            <button
                type="button"
                :disabled="purchaseLoading"
                @click="router().push('/events')"
                class="px-6 py-3 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition-colors duration-150"
            >
              Cancel
            </button>
            <button
                type="submit"
                :disabled="purchaseLoading"
                @click="handlePurchase"
                class="px-6 py-3 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-500 hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition-colors duration-150 disabled:opacity-50"
            >
              <span v-if="purchaseLoading">Processing...</span>
              <span v-else>Purchase Tickets</span>
            </button>
          </div>

          <!-- Purchase Error -->
          <div v-if="purchaseError" class="mt-6">
            <div class="bg-red-50 border-l-4 border-red-400 p-4 rounded-lg shadow-md">
              <div class="flex">
                <div class="ml-3">
                  <h3 class="text-sm font-medium text-red-800">Purchase failed</h3>
                  <div class="mt-2 text-sm text-red-700">{{ purchaseError }}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, ref, computed } from 'vue'
import { useEvent } from '@/composables/useEvent'
import { useUser } from '@/composables/useUser'
import { useVoucher } from '@/composables/useVoucher'
import {createRouter as $router, useRoute} from 'vue-router'
import {useTickets} from "@/composables/useTickets";
import router from "@/router";

export default {
  name: 'PurchaseTicket',

  setup() {
    const route = useRoute()
    const event = ref(null)
    const customer = ref(null)
    const vouchers = ref([])
    const newVoucherCode = ref('')
    const purchaseLoading = ref(false)
    const purchaseError = ref('')

    const {error, loading, getEvent} = useEvent()
    const {customerError, customerLoading, getCurrentCustomer} = useUser()
    const {voucherError, voucherLoading, getUserVoucherAll, validateVoucher} = useVoucher()
    const {  purchaseTicket } = useTickets()

    const eventId = route.params.id;
    const formData = ref({
      amount: 0,
      voucherCodes: [],
    })

    const calculateSubtotal = computed(() => {
      if (!event.value?.basePrice || !formData.value.amount) return 0
      return event.value.basePrice * formData.value.amount
    })

    const totalVoucherAmount = computed(() => {
      return formData.value.voucherCodes.reduce((total, code) => {
        const voucher = vouchers.value.find(v => v.voucherCode === code)
        return total + (voucher?.amount || 0)
      }, 0)
    })

    const calculateTotal = computed(() => {
      const subtotal = calculateSubtotal.value
      const discount = totalVoucherAmount.value
      return Math.max(0, subtotal - discount)
    })

    const unusedVouchers = computed(() => {
      return vouchers.value.filter(voucher => !voucher.used)
    })

    const addVoucherCode = async () => {
      if (!newVoucherCode.value) return;

      try {
        const validatedVoucher = await validateVoucher(newVoucherCode.value);

        if (validatedVoucher && !formData.value.voucherCodes.includes(newVoucherCode.value)) {
          formData.value.voucherCodes.push(newVoucherCode.value);
          const existingVoucherIndex = vouchers.value.findIndex(v => v.voucherCode === newVoucherCode.value);
          if (existingVoucherIndex >= 0) {
            vouchers.value[existingVoucherIndex].amount = validatedVoucher.amount;
          } else {
            vouchers.value.push({
              voucherCode: newVoucherCode.value,
              amount: validatedVoucher.amount,
              used: false
            });
          }
          newVoucherCode.value = ''; // Clear the input
          voucherError.value = ''; // Clear any previous errors
        }
      } catch (err) {
        voucherError.value = err.response?.data?.error || 'Invalid voucher code';
      }
    }

    const removeVoucherCode = (code) => {
      formData.value.voucherCodes = formData.value.voucherCodes.filter(c => c !== code)
    }

    const getVoucherAmount = (code) => {
      const voucher = vouchers.value.find(v => v.voucherCode === code);
      return voucher?.amount || 0;
    }
    const handlePurchase = async () => {
      purchaseLoading.value = true;
      purchaseError.value = '';

      try {
        if (!formData.value.amount || formData.value.amount < 1) {
          throw new Error('Please select at least one ticket');
        }

        const purchaseData = {
          eventId: eventId,
          amount: formData.value.amount,
          voucherCodes: [...formData.value.voucherCodes] // Convert Proxy to plain array
                };
        console.log(purchaseData)
        const response = await purchaseTicket(purchaseData);

        router.push({
          path: '/notifications',
          query: {
            purchase: 'success',
            reference: response.reference
          }
        });
      } catch (err) {
        purchaseError.value = err.response?.data?.error || 'Failed to complete purchase. Please try again.';
      } finally {
        purchaseLoading.value = false;
      }
    };
    onMounted(async () => {
      if (route.params.id) {
        try {
          event.value = await getEvent(eventId)
        } catch (err) {
          console.error('Error fetching event:', err)
        }
        try {
          vouchers.value = await getUserVoucherAll()
        } catch (err) {
          console.error('Error fetching vouchers:', err)
        }
        try {
          customer.value = await getCurrentCustomer()
        } catch (err) {
          console.error('Error fetching Customer:', err)
        }
      }
    })

    return {
      error,
      loading,
      customerError,
      customerLoading,
      voucherError,
      voucherLoading,
      event,
      customer,
      vouchers,
      formData,
      newVoucherCode,
      unusedVouchers,
      addVoucherCode,
      removeVoucherCode,
      calculateTotal,
      calculateSubtotal,
      totalVoucherAmount,
      handlePurchase,
      purchaseLoading,
      purchaseError,
      getVoucherAmount
    }
  },

  methods: {
    router() {
      return router
    },
    $router,
    resetForm() {
      this.formData = {
        amount: 0,
        voucherCodes: [],
      }
      this.error = ''
    }
  }
}
</script>