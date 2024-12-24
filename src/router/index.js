import { createRouter, createWebHistory } from "vue-router";
import UserLogin from "../views/UserLogin.vue";
import Dashboard from "../views/Dashboard.vue"; // Neue Dashboard-Seite
import RedeemVoucher from "../views/RedeemVoucher.vue"; // Gutschein-Seite
import BuyTickets from "../views/BuyTickets.vue"; // Ticketkauf-Seite

const routes = [
    {
        path: "/login",
        name: "UserLogin",
        component: UserLogin,
    },
    {
        path: "/dashboard",
        name: "Dashboard",
        component: Dashboard, // Dashboard Route
    },
    {
        path: "/redeem-voucher",
        name: "RedeemVoucher",
        component: RedeemVoucher, // Gutschein-Seite
    },
    {
        path: "/buy-tickets",
        name: "BuyTickets",
        component: BuyTickets, // Ticketkauf-Seite
    },
    // Weitere Routen...
];

const router = createRouter({
  history: createWebHistory(), // Nutzt das moderne Browser-History-API
  routes,
});

export default router;