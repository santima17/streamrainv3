import Vue from 'vue'
import VueRouter  from 'vue-router';
// Header & footer
import Streamrain from './vue/Streamrain.vue'
// Session
import Login from './vue/session/Login.vue';
import Logout from './vue/session/Logout.vue';
import Signup from './vue/session/Signup.vue';
// Nav
import Home from './vue/nav/Home.vue';
import Catalog from './vue/nav/Catalog.vue';

Vue.use(VueRouter);

// TODO: Esto tiene que ir definido en otro lado ========
const tenant = 'Default Tenant';
// ======================================================

const routes = [
  {
    path: '/login',
    component: Login
  },
  {
    path: '/logout',
    component: Logout
  },
  {
    path: '/signup',
    component: Signup
  },
  {
    path: '/',
    component: Home,
    props: {
      tenant
    }
  },
  {
    path: '/catalog',
    component: Catalog,
    props: {
      tenant
    }
  }
];

const router = new VueRouter({
  routes,
  mode: 'history'
});

new Vue({
  el: '#streamrain',
  router,
  render: h => h(Streamrain, {
    props: {
      tenant
    }
  })
})
