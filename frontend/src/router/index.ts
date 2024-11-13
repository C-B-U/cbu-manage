import { createWebHistory, createRouter } from "vue-router";

import Main from "../components/HelloWorld.vue";
import MemberManage from "../components/MemberManage.vue";

const routes = [
  {
    path: "/",
    name: "main",
    component: Main,
  },
  {
    path: "/member-manage",
    name: "member-manage",
    component: MemberManage,
  },
  
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;