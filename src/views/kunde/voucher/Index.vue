<template>
  <div class="p-6 w-full">
    <div class="mb-6">
      <h2 class="text-2xl font-bold text-gray-800">My Vouchers</h2>
    </div>

    <div class="bg-white shadow-lg overflow-hidden w-full">
      <div class="overflow-x-auto w-full">
        <table class="w-full divide-y divide-gray-200">
          <thead>
          <tr>
            <th class="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Wert
            </th>
            <th class="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Ablaufdatum
            </th>
            <th class="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Status
            </th>
          </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="voucher in vouchers" :key="voucher.id" class="hover:bg-gray-50">
            <td class="px-6 py-4">
              <div class="text-sm font-medium text-gray-900">{{ voucher.amount }}€</div>
            </td>
            <td class="px-6 py-4">
              <div class="text-sm text-gray-500">{{ voucher.voucherValidUntil }}</div>
            </td>
            <td class="px-6 py-4">
                <span :class="[
                  voucher.used
                    ? 'bg-gray-100 text-gray-800'
                    : 'bg-green-100 text-green-800',
                  'px-2 inline-flex text-xs leading-5 font-semibold rounded-full'
                ]">
                  {{ voucher.used ? 'Benutzt' : 'Verfügbar' }}
                </span>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

    <BasePagination
        :current-page="currentPage - 1"
        :total-pages="totalPages"
        @page-change="handlePageChange"
    />

    <!-- Loading State -->
    <div v-if="loading" class="flex justify-center items-center mt-4">
      <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"></div>
    </div>

    <!-- Error State -->
    <div v-if="error" class="mt-4 p-4 bg-red-100 border border-red-400 text-red-700 rounded-md">
      {{ error }}
    </div>
  </div>
</template>

<script>
import { useVoucher } from '@/composables/useVoucher'
import BasePagination from '@/components/BasePagination.vue';

export default {
  components: {
    BasePagination
  },
  setup() {
    const { error, loading, getUserVoucher } = useVoucher()
    return {
      error,
      loading,
      getUserVoucher
    }
  },
  data() {
    return {
      vouchers: [],
      currentPage: 1,
      rowsPerPage: 10,
      totalElements: 0,
      totalPages: 0,
    }
  },
  methods: {
    handlePageChange(page) {
      this.currentPage = page + 1;
      this.fetchVouchers();
    },
    async fetchVouchers() {
      try {
        const response = await this.getUserVoucher()
        this.vouchers = response.content
        this.totalElements = response.totalElements
        this.totalPages = response.totalPages
      } catch (err) {
        console.error('Error fetching vouchers:', err)
      }
    }
  },
  created() {
    this.fetchVouchers()
  }
}
</script>