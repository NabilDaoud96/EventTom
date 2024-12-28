import { createRouter, createWebHistory } from "vue-router";

// Layouts
import Kunde from "../layouts/Kunde.vue";
import Auth from "../layouts/Auth.vue";

// Kunde Views
import Dashboard from "../views/kunde/Dashboard.vue";
import Tickets from "../views/kunde/Tickets.vue";
import Profile from "../views/kunde/Profile.vue";
import Events from "../views/kunde/Events.vue";
import BuyTickets from "../views/kunde/BuyTickets.vue";
import RedeemVoucher from "../views/kunde/RedeemVoucher.vue";

// Auth Views
import Login from "../views/auth/Login.vue";
import Register from "../views/auth/Register.vue";

// // routes

const routes = [
    {
        path: "/kunde",
        redirect: "/kunde/dashboard",
        component: Kunde,
        children: [
        {
            path: "/kunde/dashboard",
            component: Dashboard,
        },
        {
            path: "/kunde/tickets",
            component: Tickets,
        },
        {
            path: "/kunde/events",
            component: Events,
        },
        {
            path: "/kunde/profile",
            component: Profile,
        },
        {
            path: "/kunde/buy_tickets",
            component: BuyTickets,
        },
        {
            path: "/kunde/redeem-voucher",
            component: RedeemVoucher,
        }
        ],
    },
    {
        path: "/",
        redirect: "/auth/login",
        component: Auth,
        children: [
            {
                path: "/auth/login",
                component: Login,
            },
            {
                path: "/auth/register",
                component: Register,
            },
        ],
    },
    {
        path: "/:pathMatch(.*)*", redirect: "/"
        
    },
    
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
