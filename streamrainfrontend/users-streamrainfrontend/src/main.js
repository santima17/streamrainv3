import Vue from 'vue'
import VueRouter  from 'vue-router';
import VueResource from 'vue-resource'
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
// Streaming
import LiveOnly from './vue/streaming/LiveOnly.vue'; 
import AlwaysAvailable from './vue/streaming/AlwaysAvailable.vue';

Vue.use(VueRouter);
Vue.use(VueResource);

// ======================================================
const config = require('./assets/config');
// ======================================================

const eventBus = new Vue();

const routes = [
  {
    path: '/notFound',
    component: NotFound,
    meta: {
      title: `${config.tenant.name} | Page Not Found`
    }
  },
  {
    path: '/login',
    component: Login,
    meta: {
      title: `${config.tenant.name} | Log In`
    }
  },
  {
    path: '/logout',
    component: Logout,
    meta: {
      title: `${config.tenant.name} | Log Out`
    }
  },
  {
    path: '/signup',
    component: Signup,
    meta: {
      title: `${config.tenant.name} | Sign Up!`
    }
  },
  {
    path: '/',
    component: Home,
    meta: {
      title: `${config.tenant.name} | Home`
    }
  },
  {
    path: '/catalog',
    component: Catalog,
    meta: {
      title: `${config.tenant.name} | Catalog`
    }
  },
  {
    path: '/live/:streamId',
    component: LiveOnly,
    meta: {
      title: `${config.tenant.name} | Live!`
    }
  },
  {
    path: '/vod',
    component: AlwaysAvailable,
    meta: {
      title: `${config.tenant.name} | Vod!`
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
  } 
  else {
    document.title = to.meta.title;
    if (to.path !== '/login' && to.path !== '/signup' && to.path !== '/') {
      const session = JSON.parse(localStorage.getItem(`streamrain-${config.tenant.name.replace(/\s/g, '')}-session`));
      if (session !== undefined && session !== null) {
        next();
      }
      else {
        eventBus.$emit('removeVueSession', null);
        next('/login');
      }
    } else {
      next();
    }
  }
});

new Vue({
  el: '#streamrain',
  router,
  render: h => h(Streamrain, {
    props: {
      config,
      eventBus
    }
  })
})
