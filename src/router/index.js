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
import Notifications from "@/views/kunde/notification/Index.vue";

// Event Creator Views
import EventCreatorProfile from "../views/event_creator/profile/Edit.vue";
import EventCreatorEvents from "../views/event_creator/event/Index.vue";
import EventCreatorCreateEvent from "../views/event_creator/create_event/Create.vue"
import EventCreatorDashboard from "@/views/event_creator/dashboard/Index.vue";
// Auth Views
import Login from "../views/auth/Login.vue";
import Register from "../views/auth/Register.vue";

// Voucher Views
import IndexVoucher from "@/views/kunde/voucher/Index.vue";
import PurchaseTicket from "@/views/kunde/ticket/PurchaseTicket.vue"
import store from "@/store";
import DynamicLayout from "@/layouts/DynamicLayout.vue";
import UpdateEventForm from "@/views/event_creator/create_event/UpdateEventForm.vue";

const routes = [
    {
        path: "/",
        redirect: "/dashboard",
        component: DynamicLayout,
        children: [
            {
                path: "/dashboard",
                component: Dashboard,
            },
            {
                path: "/tickets",
                meta: { requiresAuth: true, roles: ['ROLE_NONE'] },
                component: IndexTickets,
            },
            {
                path: "/vouchers",
                meta: { requiresAuth: true, roles: ['ROLE_NONE'] },
                component: IndexVoucher,
            },
            {
                path: "/events",
                meta: { requiresAuth: true, roles: ['ROLE_NONE'] },
                component: IndexEvents,
            },
            {
                path: "/profile",
                component: Profile,
            },
            {
                path: "/notifications",
                meta: { requiresAuth: true, roles: ['ROLE_NONE'] },

                component: Notifications,
            },
            {
                path: "/events/:id",
                name: 'EventShow',
                component: EventShow,
            },
            {
                path: '/purchase-ticket/:id',
                name: 'PurchaseTicket',
                meta: { requiresAuth: true, roles: ['ROLE_NONE'] },
                component: PurchaseTicket
            },
            {
                path: "/event-creator/dashboard",
                meta: { requiresAuth: true, roles: ['ROLE_EVENT_CREATOR'] },
                component: EventCreatorDashboard,
            },
            {
                path: "/event-creator/profile",
                meta: { requiresAuth: true, roles: ['ROLE_EVENT_CREATOR'] },

                component: EventCreatorProfile,
            },
            {
                path: "/event-creator/events",
                meta: { requiresAuth: true, roles: ['ROLE_EVENT_CREATOR'] },
                component: EventCreatorEvents,
            },
            {
                path: "/event-creator/create",
                meta: { requiresAuth: true, roles: ['ROLE_EVENT_CREATOR'] },
                component: EventCreatorCreateEvent,
            },
            {
                path: "/event-creator/update/:id",
                name: 'UpdateEventForm',
                meta: { requiresAuth: true, roles: ['ROLE_EVENT_CREATOR'] },
                component: UpdateEventForm,
            },
        ],
    },
    {
        path: "/",
        redirect: "/dashboard",
        component: Auth,
        meta: { requiresAuth: false },
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
        path: "/:pathMatch(.*)*",
        redirect: "/"
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach(async (to, from, next) => {
    // Check if route requires authentication
    if (to.matched.some(record => record.meta.requiresAuth)) {
        const isAuthenticated = store.getters['auth/isAuthenticated'];
        const userRoles = store.getters['auth/userRoles'];

        // Handle case where userRoles might be a Proxy
        const roles = Array.isArray(userRoles) ? userRoles :
            (userRoles && userRoles.valueOf ? userRoles.valueOf() : []);

        if (!isAuthenticated) {
            // Redirect to login if not authenticated
            next({
                path: '/auth/login',
                query: { redirect: to.fullPath } // Store intended destination
            });
            return;
        }

        if (to.matched.some(record => record.meta.roles)) {
            const requiredRoles = to.matched
                .filter(record => record.meta.roles)
                .map(record => record.meta.roles)
                .pop();

            const hasRequiredRole = roles.some(role =>
                requiredRoles.includes(role)
            );

            if (!hasRequiredRole) {
                next({ path: '/dashboard' });
                return;
            }
        }
    } else if (to.matched.some(record => record.meta.requiresAuth === false)) {
        const isAuthenticated = store.getters['auth/isAuthenticated'];

        if (isAuthenticated) {
            next({ path: '/dashboard' });
            return;
        }
    }

    // If all checks pass, proceed to route
    next();
});

export default router;