import Vue from 'vue'
import VueRouter  from 'vue-router';
// Header & footer
import Streamrain from './vue/Streamrain.vue'
// Session
import Login from './vue/session/Login.vue';
import Logout from './vue/session/Logout.vue';
import Signup from './vue/session/Signup.vue';
// Nav
import NotFound from './vue/nav/NotFound.vue';
import Home from './vue/nav/Home.vue';
import Catalog from './vue/nav/Catalog.vue';

Vue.use(VueRouter);

// TODO: Esto tiene que ir definido en otro lado ========
const tenant = 'Default Tenant';
const backend = 'http://localhost:3002';
// ======================================================

const eventBus = new Vue();

const routes = [
  {
    path: '/notFound',
    component: NotFound,
    props: {
      tenant
    },
    meta: {
      title: `${tenant} | Page Not Found`
    }
  },
  {
    path: '/login',
    component: Login,
    meta: {
      title: `${tenant} | Log In`
    }
  },
  {
    path: '/logout',
    component: Logout,
    meta: {
      title: `${tenant} | Log Out`
    }
  },
  {
    path: '/signup',
    component: Signup,
    meta: {
      title: `${tenant} | Sign Up!`
    }
  },
  {
    path: '/',
    component: Home,
    props: {
      tenant
    },
    meta: {
      title: `${tenant} | Home`
    }
  },
  {
    path: '/catalog',
    component: Catalog,
    props: {
      tenant,
      eventBus
    },
    meta: {
      title: `${tenant} | Catalog`
    }
  }
];

const router = new VueRouter({
  routes,
  mode: 'history'
});

router.beforeEach((to, from, next) => {
  if (!to.matched.length) {
    next('/notFound');
  } else {
    document.title = to.meta.title;
    next();
  }
});

new Vue({
  el: '#streamrain',
  router,
  render: h => h(Streamrain, {
    props: {
      tenant,
      backend,
      eventBus
    }
  })
})
