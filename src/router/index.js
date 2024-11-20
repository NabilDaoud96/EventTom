import { createRouter, createWebHistory } from "vue-router";
import UserLogin from "../views/UserLogin.vue"; // Import der Login-Seite

const routes = [
  {
    path: "/login",
    name: "UserLogin",
    component: UserLogin, // Diese Komponente wird geladen, wenn die URL "/login" ist
  },
  // Hier kannst du später weitere Seiten wie Dashboard hinzufügen
];

const router = createRouter({
  history: createWebHistory(), // Nutzt das moderne Browser-History-API
  routes,
});

export default router;