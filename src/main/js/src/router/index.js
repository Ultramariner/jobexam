import {createRouter, createWebHistory} from 'vue-router';
const WelcomePage = () => import("../views/WelcomePage.vue");

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/vue/welcome',
            name: "WelcomePage",
            component: WelcomePage,
            props: (route) => route.query,
        },

    ]
});
export default router;