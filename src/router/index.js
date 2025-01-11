
import { createRouter, createWebHistory } from "vue-router";

// Layouts
import Kunde from "../layouts/Kunde.vue";
import Auth from "../layouts/Auth.vue";
import EventCreator from "../layouts/EventCreator.vue";

// Kunde Views
import Dashboard from "../views/kunde/dashboard/Index.vue";
import IndexTickets from "../views/kunde/ticket/Index.vue";
import BuyTicket from "../views/kunde/ticket/BuyTicket.vue";
import Profile from "../views/kunde/profile/Edit.vue";
import IndexEvents from "../views/kunde/event/Index.vue";
import EventShow from "../views/kunde/event/Show.vue";


// Event Creator Views
import EventCreatorProfile from "../views/event_creator/profile/Edit.vue";
import EventCreatorEvents from "../views/event_creator/event/Index.vue";
import EventCreatorCreateEvent from "../views/event_creator/create_event/Create.vue"


// Auth Views
import Login from "../views/auth/Login.vue";
import Register from "../views/auth/Register.vue";

// Voucher Views
import IndexVoucher from "@/views/kunde/voucher/Index.vue";

// // routes

const routes = [
    
    {
        path: "/",
        redirect: "/dashboard",
        component: Kunde,
        children: [
        {
            path: "/dashboard",
            component: Dashboard,
        },
        {
            path: "/tickets",
            component: IndexTickets,
        },
        {
            path: "/vouchers",
            component: IndexVoucher,
        },
        {
            path: "/events",
            component: IndexEvents,
        },
        {
            path: "/profile",
            component: Profile,
        },
        {
            path: "/buy_ticket",
            component: BuyTicket,
        }, 
        {
            path: "/event/:id",
            name: 'EventShow',
            component: EventShow,
        }, 
        ],
    },
    {
        path: "/event-creator",
        redirect: "/event-creator/dashboard",
        component: EventCreator,
        children: [
        {
            path: "/event-creator/profile",
            component: EventCreatorProfile,
        },
        {
            path: "/event-creator/events",
            component: EventCreatorEvents,
        },
        {
            path: "/event-creator/create_event",
            component: EventCreatorCreateEvent,
            
        },
        ],
    },
    {
        path: "/",
        redirect: "/dashboard",
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
