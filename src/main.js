import { createApp } from "vue";
import { createWebHistory, createRouter } from "vue-router";


// styles

import "@fortawesome/fontawesome-free/css/all.min.css";
import "@/assets/styles/tailwind.css";

// mouting point for the whole app

import App from "@/App.vue";

// layouts

import Kunde from "@/layouts/Kunde.vue";
import Auth from "@/layouts/Auth.vue";

// views for Kunde layout

import Dashboard from "@/views/kunde/Dashboard.vue";
import Tickets from "./views/kunde/Tickets.vue"
import Profile from "./views/kunde/Profile.vue";
import Events from "./views/kunde/Events.vue";

// views for Auth layout

import Login from "@/views/auth/Login.vue";
import Register from "@/views/auth/Register.vue";


// routes

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


// Router erstellen

const router = createRouter({
  history: createWebHistory(),
  routes,
});

createApp(App).use(router).mount("#app");