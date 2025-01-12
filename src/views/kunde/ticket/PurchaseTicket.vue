<template>
  <!-- Previous sections remain the same until voucher section -->
  <div class="min-h-screen bg-gray-50 py-8">
    <div class="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8">
      <!-- Loading State -->
      <div v-if="loading" class="flex justify-center items-center py-12">
        <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-900"></div>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="bg-red-50 border border-red-200 rounded-md p-4 mt-4">
        <div class="flex">
          <div class="ml-3">
            <h3 class="text-sm font-medium text-red-800">Error loading event</h3>
            <div class="mt-2 text-sm text-red-700">{{ error }}</div>
          </div>
        </div>
      </div>

      <!-- Main Content -->
      <div v-else class="bg-white shadow rounded-lg">
        <!-- Header -->
        <div class="px-6 py-5 border-b border-gray-200">
          <h1 class="text-2xl font-semibold text-gray-900">Purchase Ticket</h1>
        </div>

        <!-- Event Details Section -->
        <div class="px-6 py-5 space-y-4">
          <div class="grid grid-cols-1 gap-4 sm:grid-cols-2">
            <div class="space-y-2">
              <label class="block text-sm font-medium text-gray-700">Event Name</label>
              <p class="text-lg text-gray-900">{{ event?.title }}</p>
            </div>
            <div class="space-y-2">
              <label class="block text-sm font-medium text-gray-700">Date</label>
              <p class="text-lg text-gray-900">{{ event?.dateOfEvent || 'TBD' }}</p>
            </div>
            <div class="space-y-2">
              <label class="block text-sm font-medium text-gray-700">Preis</label>
              <p class="text-lg text-gray-900">{{ event?.basePrice }} $</p>
            </div>
          </div>

          <!-- Customer Details -->
          <div class="mt-6">
            <h3 class="text-lg font-medium text-gray-900">Customer Details</h3>
            <div v-if="customerLoading" class="mt-2 text-sm text-gray-500">
              Loading customer information...
            </div>
            <div v-else-if="customerError" class="mt-2 text-sm text-red-600">
              {{ customerError }}
            </div>
            <div v-else-if="customer" class="mt-4 grid grid-cols-1 gap-4 sm:grid-cols-2">
              <div class="space-y-2">
                <label class="block text-sm font-medium text-gray-700">Customer Number</label>
                <p class="text-lg text-gray-900">{{ customer.customerNumber }}</p>
              </div>
              <div class="space-y-2">
                <label class="block text-sm font-medium text-gray-700">Full Name</label>
                <p class="text-lg text-gray-900">{{ customer.user.firstName }} {{ customer.user.lastName }}</p>
              </div>
              <div class="space-y-2">
                <label class="block text-sm font-medium text-gray-700">Email</label>
                <p class="text-lg text-gray-900">{{ customer.user.email }}</p>
              </div>
            </div>
          </div>

          <!-- Voucher Section -->
          <div class="mt-6">
            <h3 class="text-lg font-medium text-gray-900">Vouchers</h3>

            <!-- Available Vouchers Selection -->
            <div class="mt-4" v-if="vouchers && vouchers.length > 0">
              <label class="block text-sm font-medium text-gray-700 mb-2">Select Available Vouchers</label>
              <div class="space-y-2">
                <div v-for="voucher in unusedVouchers" :key="voucher.voucherCode"
                     class="flex items-center p-3 border rounded-md hover:bg-gray-50">
                  <input
                      type="checkbox"
                      :value="voucher.voucherCode"
                      v-model="formData.voucherCodes"
                      class="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                  />
                  <label class="ml-3 block text-sm text-gray-700">
                      {{ voucher.voucherCode	 }}
                    <span v-if="voucher.amount" class="text-gray-500 ml-2">({{ voucher.amount }}$)</span>
                  </label>
                </div>
              </div>
            </div>

            <!-- Manual Voucher Entry -->
            <div class="mt-4">
              <label class="block text-sm font-medium text-gray-700">Enter Voucher Code Manually</label>
              <div class="mt-2 flex rounded-md shadow-sm">
                <input
                    v-model="newVoucherCode"
                    type="text"
                    class="flex-1 min-w-0 block w-full px-3 py-2 rounded-md border border-gray-300 focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                    placeholder="Enter voucher code"
                />
                <button
                    @click="addVoucherCode"
                    type="button"
                    class="ml-3 inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                  Apply
                </button>
              </div>
              <p v-if="voucherError" class="mt-2 text-sm text-red-600">{{ voucherError }}</p>
            </div>
            <!-- Selected Vouchers List -->

            <div v-if="formData.voucherCodes.length > 0" class="mt-4">
              <label class="block text-sm font-medium text-gray-700">Selected Vouchers</label>
              <div class="mt-2 space-y-2">
                <div v-for="code in formData.voucherCodes" :key="code"
                     class="flex justify-between items-center p-2 bg-gray-50 rounded-md">
                  <div class="flex items-center space-x-2">
                    <span class="text-sm text-gray-700">{{ code }}</span>
                    <span class="text-sm text-green-600">({{ getVoucherAmount(code) }} $)</span>
                  </div>
                  <button
                      @click="removeVoucherCode(code)"
                      class="text-sm text-red-600 hover:text-red-800"
                  >
                    Remove
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div class="space-y-2">
            <label for="ticketQuantity" class="block text-sm font-medium text-gray-700">Number of Tickets</label>
            <div class="mt-1 flex rounded-md shadow-sm">
              <input
                  id="ticketQuantity"
                  type="number"
                  min="1"
                  v-model.number="formData.amount"
                  class="flex-1 min-w-0 block w-full px-3 py-2 rounded-md border border-gray-300 focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                  required
              />
            </div>
            <!-- Updated price display -->
            <div class="mt-2 space-y-1">
              <p class="text-sm text-gray-500">Subtotal: {{ calculateSubtotal }} $</p>
              <p v-if="totalVoucherAmount > 0" class="text-sm text-green-600">Voucher Discount: -{{ totalVoucherAmount }} $</p>
              <p class="text-sm font-medium text-gray-900">Final Total: {{ calculateTotal }} $</p>
            </div>
          </div>
          <!-- Purchase Actions -->
          <div class="mt-8 flex justify-end space-x-4">
            <button
                type="button"
                :disabled="purchaseLoading"
                @click="$router.push('/events')"
                class="px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
              Cancel
            </button>
            <button
                type="submit"
                :disabled="purchaseLoading"
                @click="handlePurchase"
                class="px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50"
            >
              <span v-if="purchaseLoading">Processing...</span>
              <span v-else>Purchase Ticket</span>
            </button>
          </div>

          <!-- Purchase Error Message -->
          <div v-if="purchaseError" class="mt-4">
            <div class="bg-red-50 border border-red-200 rounded-md p-4">
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
import { useRoute } from 'vue-router'
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
          path: '/tickets',
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