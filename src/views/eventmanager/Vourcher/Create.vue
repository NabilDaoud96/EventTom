<template>
    <div class="flex items-center justify-center min-h-screen bg-blueGray-100">
        <div class="w-full max-w-md px-4 py-8 bg-white shadow-lg rounded-lg">
            <h1 class="text-2xl font-semibold text-center text-blueGray-700 mb-6">Gutschein Erstellen</h1>
            <form @submit.prevent="createVoucher">
                <div class="mb-4">
                    <label for="voucherCode" class="block text-sm font-bold text-blueGray-600 mb-2">
                        Gutscheincode
                    </label>
                    <input id="voucherCode"
                           v-model="voucher.code"
                           type="text"
                           placeholder="Code eingeben"
                           class="border px-3 py-2 rounded w-full shadow focus:outline-none focus:ring focus:ring-blueGray-300 transition"
                           required />
                </div>
                <div class="mb-6">
                    <label for="voucherPrice" class="block text-sm font-bold text-blueGray-600 mb-2">
                        Preis (€)
                    </label>
                    <input id="voucherPrice"
                           v-model="voucher.price"
                           type="number"
                           placeholder="Preis eingeben"
                           class="border px-3 py-2 rounded w-full shadow focus:outline-none focus:ring focus:ring-blueGray-300 transition"
                           required />
                </div>
                <button type="submit"
                        class="w-full bg-emerald-500 text-white font-bold py-2 px-4 rounded shadow hover:bg-emerald-600 focus:outline-none transition">
                    Gutschein Erstellen
                </button>
            </form>
        </div>
    </div>
</template>

<script>export default {
  data() {
    return {
      voucher: {
        code: '',
        price: '',
      },
    };
  },
  methods: {
    createVoucher() {
      // Aktion ausführen, z. B. Daten an Backend senden
      console.log('Gutschein erstellt:', this.voucher);

      // Felder zurücksetzen
      this.voucher.code = '';
      this.voucher.price = '';

      alert('Gutschein erfolgreich erstellt!');
    },
  },
};</script>

<style>
    .min-h-screen {
        min-height: 100vh;
    }
</style>
