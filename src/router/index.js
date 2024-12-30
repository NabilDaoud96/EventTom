
import { createRouter, createWebHistory } from "vue-router";

// Layouts
import Kunde from "../layouts/Kunde.vue";
import Auth from "../layouts/Auth.vue";

// Kunde Views
import Dashboard from "../views/kunde/dashboard/Index.vue";
import IndexTickets from "../views/kunde/ticket/Index.vue";
import BuyTicket from "../views/kunde/ticket/BuyTicket.vue";
import Profile from "../views/kunde/profile/Edit.vue";
import IndexEvents from "../views/kunde/event/Index.vue";
import Show from "../views/kunde/event/Show.vue";



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
                component: IndexTickets,
            },
            {
                path: "/kunde/events",
                component: IndexEvents,
            },
            {
                path: "/kunde/profile",
                component: Profile,
            },
            {
                path: "/kunde/buy_ticket",
                component: BuyTicket,
            },
            {
                path: "/kunde/event_show",
                component: Show,
            },
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