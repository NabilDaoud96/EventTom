
import { createRouter, createWebHistory } from "vue-router";

// Layouts
import Kunde from "../layouts/Kunde.vue";
import EventManager from "../layouts/EventManager.vue";
import Auth from "../layouts/Auth.vue";

// Kunde Views
import KundeDashboard from "../views/kunde/dashboard/Index.vue";
import IndexTickets from "../views/kunde/ticket/Index.vue";
import BuyTicket from "../views/kunde/ticket/BuyTicket.vue";
import KundeProfile from "../views/kunde/profile/Edit.vue";
import IndexEventsKunde from "../views/kunde/event/Index.vue";
import ShowEventsKunde from "../views/kunde/event/Show.vue";

// EventManager Views
import EventManagerDashboard from "../views/eventmanager/Managerdashboard/Index.vue";
import IndexKunde from "../views/eventmanager/ManagerKunden/Index.vue";
import IndexEventsEventManger from "../views/eventmanager/ManagerEvent/Index.vue";
import ShowEventsEventManger from "../views/eventmanager/ManagerEvent/Show.vue";
import EventMangerProfile from "../views/eventmanager/Profile/Profile.vue";
import VourcherCreate from "../views/eventmanager/Vourcher/Create.vue";



// Auth Views
import Login from "../views/auth/Login.vue";
import Register from "../views/auth/Register.vue";

// // routes

const routes = [
    // Kunde Routes
    {
        path: "/kunde",
        redirect: "/kunde/dashboard",
        component: Kunde,
        children: [
            {
                path: "/kunde/dashboard",
                component: KundeDashboard,
            },
            {
                path: "/kunde/tickets",
                component: IndexTickets,
            },
            {
                path: "/kunde/events",
                component: IndexEventsKunde,
            },
            {
                path: "/kunde/profile",
                component: KundeProfile,
            },
            {
                path: "/kunde/buy_ticket",
                component: BuyTicket,
            },
            {
                path: "/kunde/event_show",
                component: ShowEventsKunde,
            },
        ],
    },
    // EventManger Routes
    {
        path: "/EventManger",
        redirect: "/EventManger/dashboard",
        component: EventManager,
        children: [
            {
                path: "/event_manager/dashboard",
                component: EventManagerDashboard,
            },
            {
                path: "/event_manager/events",
                component: IndexEventsEventManger,
            },
            {
                path: "/event_manager/event_show",
                component: ShowEventsEventManger,
            },
            {
                path: "/event_manager/kunden",
                component: IndexKunde,
            },
            {
                path: "/event_manager/create_vourcher",
                component: VourcherCreate,
            },
            {
                path: "/event_manager/profile",
                component: EventMangerProfile,
            },
            
        ],
    },
    // Auth Router
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