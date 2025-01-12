<template>
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
                            <p class="text-lg text-gray-900">{{ event?.name || 'Loading...' }}</p>
                        </div>
                        <div class="space-y-2">
                            <label class="block text-sm font-medium text-gray-700">Date</label>
                            <p class="text-lg text-gray-900">{{ event?.date || 'TBD' }}</p>
                        </div>
                    </div>

                    <!-- Voucher Section -->
                    <div class="mt-6">
                        <label class="block text-sm font-medium text-gray-700">Voucher Code</label>
                        <div class="mt-2 flex rounded-md shadow-sm">
                            <input
                                type="text"
                                class="flex-1 min-w-0 block w-full px-3 py-2 rounded-md border border-gray-300 focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                                placeholder="Enter voucher code"
                            />
                            <button
                                type="button"
                                class="ml-3 inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                            >
                                Apply
                            </button>
                        </div>
                        <p v-if="voucherError" class="mt-2 text-sm text-red-600">{{ voucherError }}</p>
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
                    </div>

                    <!-- Purchase Actions -->
                    <div class="mt-8 flex justify-end space-x-4">
                        <button
                            type="button"
                            class="px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                        >
                            Cancel
                        </button>
                        <button
                            type="submit"
                            class="px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                        >
                            Purchase Ticket
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>


<script>
import {ref, onMounted} from 'vue'
import {useEvent} from '@/composables/useEvent'
import {useUser} from '@/composables/useUser'
import {useVoucher} from '@/composables/useVoucher'
import {useRoute} from 'vue-router'

export default {
    name: 'PurchaseTicket',
    setup() {
        const route = useRoute()
        const {error, loading, getEvent} = useEvent()
        const {customerError, customerLoading, getCurrentCustomer} = useUser()
        const {voucherError, voucherLoading, getUserVoucher} = useVoucher()

        onMounted(async () => {
            if (route.params.id) {
                try {
                    const response = await getEvent(route.params.id)
                    console.log(response)
                } catch (err) {
                    console.error('Error fetching event:', err)
                }
                try {
                    const response = await getUserVoucher()
                    console.log(response)
                } catch (err) {
                    console.error('Error fetching vouchers:', err)
                }
                try {
                    const response = await getCurrentCustomer()
                    console.log(response)
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
            voucherLoading
        }
    }
}
</script>

<style scoped>
.purchase-ticket {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
}

.form-group {
    margin-bottom: 15px;
}

.form-group label {
    display: block;
    margin-bottom: 5px;
}

.voucher-input {
    display: flex;
    gap: 10px;
    margin-bottom: 10px;
}

.error-message {
    color: red;
    margin: 5px 0;
}

.form-actions {
    margin-top: 20px;
    display: flex;
    gap: 10px;
}
</style>