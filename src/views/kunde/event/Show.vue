<template>
  <div class="flex justify-center items-center min-h-screen p-4">
    <!-- Event Karte, auf 50% der Seite begrenzt -->
    <div class="bg-white shadow-lg rounded-lg overflow-hidden max-w-md w-full">
      <div class="bg-blue-500 text-white p-4 rounded-t-lg">
        <!-- Navbar für den Titel -->
        <h2 class="text-2xl font-semibold text-center">{{ event.name }}</h2>
      </div>
      <div class="p-6">
        <!-- Event Details: Datum, Ort, Verfügbare Tickets und Preis -->
        <p class="mt-2 text-center text-gray-600">Date: {{ event.date }}</p>
        <p class="mt-2 text-center text-gray-600">Location: {{ event.location }}</p>
        <p class="mt-2 text-center text-gray-600">Available Tickets: {{ event.availableTickets }}</p>
        <p class="mt-4 text-center text-xl font-bold">Price: €{{ event.price }}</p>
        
        <!-- Buttons für Ticketkauf und Gutscheinverwendung -->
        <div class="flex justify-between mt-6">
          <button 
            class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 focus:outline-none"
            @click="buyTicket"
          >
            <i class="fa fa-shopping-cart mr-2"></i> Buy Ticket
          </button>

          <!-- Gutscheinbutton, der nur angezeigt wird, wenn der Event Gutschein hat -->
          <button 
            v-if="event.hasVoucher" 
            class="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600 focus:outline-none"
            @click="useVoucher"
          >
            <i class="fa fa-gift mr-2"></i> Use Voucher
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // Event-Informationen als statisches Array
      event: {
        id: 1,
        name: "Music Concert",
        date: "01/01/2025",
        location: "Berlin",
        price: 50.00,
        availableTickets: 100, // Verfügbare Tickets
        hasVoucher: true // Zeigt an, ob ein Gutschein verfügbar ist
      }
    };
  },
  methods: {
    // Methode zum Ticketkauf
    buyTicket() {
      alert('Ticket purchased!');
      // Ticketkauf logik hier hinzufügen
    },
    // Methode zum Anwenden des Gutscheins
    useVoucher() {
      if (this.event.hasVoucher) {
        const discount = 10; // Beispiel für einen Rabatt durch Gutschein
        this.event.price -= discount; // Preis anpassen
        alert(`Voucher applied! New price: €${this.event.price}`);
        this.event.hasVoucher = false; // Gutschein nach der Verwendung deaktivieren
      }
    }
  }
};
</script>

<style scoped>
/* Optional Styling für das Event-Kartenlayout */
.bg-white {
  background-color: white;
}
.shadow-lg {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
.rounded-lg {
  border-radius: 8px;
}
.overflow-hidden {
  overflow: hidden;
}
.text-gray-600 {
  color: #4a4a4a;
}
.bg-blue-500 {
  background-color: #3b82f6;
}
.text-white {
  color: white;
}
</style>
