import { createApp } from "vue";
import App from "./App.vue";
import router from "./router"; // Importiere den Router
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"

const app = createApp(App);

app.use(router); // Aktiviere den Router
app.mount("#app"); // Montiere die App im <div id="app">