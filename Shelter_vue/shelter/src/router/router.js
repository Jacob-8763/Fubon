import { createRouter, createWebHistory } from "vue-router";

import HomePage from "@/views/HomePage.vue";
import NotFound from "@/views/NotFound.vue";

const routes = [
    { path: "/", name: "homepage-link", component: HomePage },
    { path: "/:pathMatch(.*)*", name: "404-link", component: NotFound },

];

const router = createRouter({
    history: createWebHistory(),
    routes: routes,
});

export default router;