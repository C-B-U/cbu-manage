import { createWebHistory, createRouter } from "vue-router";

import Main from "../components/HelloWorld.vue";
import MemManage from "../components/pages/MemManagePage.vue";
import PassMemManage from "../components/pages/PassMemManagePage.vue";
import TestLoginPage from "../components/pages/TestLoginPage.vue";
import JoinPage from "../components/pages/JoinPage.vue";

const routes = [
  {
    path: "/",
    name: "main",
    component: Main,
  },
  {
    path: "/manage-M",
    name: "manage-M",
    component: MemManage,
  },
  {
    path: "/manage-SA",
    name: "manage-SA",
    component: PassMemManage,
  },
  {
    path: "/login",
    name: "login",
    component: TestLoginPage,
  },
  {
    path: "/join",
    name: "join",
    component: JoinPage,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;