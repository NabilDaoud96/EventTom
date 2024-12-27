import { createRouter, createWebHistory } from "vue-router";
import UserLogin from "../views/UserLogin.vue"; // Import der Login-Seite

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