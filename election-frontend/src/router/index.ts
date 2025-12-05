import { createRouter, createWebHistory } from 'vue-router'
import ExamplePage from "@/pages/ExamplePage.vue";
import HomePage from "@/pages/HomePage.vue";
import LoginExample from "@/pages/LoginExample.vue";
import ComparisonPage from "@/pages/ComparisonPage.vue";
import ElectionResultsPage from "@/pages/ElectionResultsPage.vue";
import PartyPage from "@/pages/PartyPage.vue";
import ForumOverviewPage from "@/pages/ForumOverviewPage.vue";
import Register from "@/pages/Register.vue";
import ForumPostPage from "@/pages/ForumPostPage.vue";
import PartyDetails from "@/pages/PartyDetails.vue";
import Login from "@/pages/Login.vue";

const routes = [
    {
        path: '/',
        name: 'home',
        component: HomePage,
    },
    {
        path: '/example',
        name: 'example',
        component: ExamplePage,
    },
    {
        path: '/login',
        name: 'login',
        component: Login,
        meta: { hideLayout: true }  // ← No navbar/footer
    },
    {
        path: '/election-results',
        name: 'election-results',
        component: ElectionResultsPage,
    },
    {
        path: '/parties',
        name: 'parties',
        component: PartyPage,
    },
    {
        path: '/party/:id',
        name: 'party-details',
        component: PartyDetails,
    },
    {
        path: '/register',
        name: 'register',
        component: Register,
        meta: { hideLayout: true }
    },
    {
        path: '/compare',
        name: 'compare',
        component: ComparisonPage,
    },
    {
        path: '/forum',
        name: 'forum',
        component: ForumOverviewPage
    },
    {
        path: '/forum/:id',
        name: 'forum-post',
        component: ForumPostPage,
        props: true
    }
]

export const router = createRouter({
    history: createWebHistory(),
    routes,
})