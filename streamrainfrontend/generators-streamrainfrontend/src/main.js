import Vue from 'vue'
import VueRouter  from 'vue-router';
import VueResource from 'vue-resource'
// Header & footer
import Streamrain from './vue/Streamrain.vue'
// Content
import AlwaysAvailable from './vue/content/AlwaysAvailable.vue';
import Casts from './vue/content/Casts.vue';
import Categories from './vue/content/Categories.vue';
import Featured from './vue/content/Featured.vue';
import LiveOnly from './vue/content/LiveOnly.vue';
import Similar from './vue/content/Similar.vue';
// Resources
import Janus from './vue/resources/janus/Janus.vue';
import JanusServer from './vue/resources/janus/JanusServer.vue';
// Nav
import NotFound from './vue/nav/NotFound.vue';
import Home from './vue/nav/Home.vue'; 
// Session
import Login from './vue/session/Login.vue';
import Logout from './vue/session/Logout.vue';

Vue.use(VueRouter);
Vue.use(VueResource);

// ======================================================
const config = require('./assets/config');
// ======================================================

const eventBus = new Vue();

const routes = [
  // Content
  {
    path: '/content/alwaysAvailable',
    component: AlwaysAvailable,
    meta: {
      title: `${config.tenant.name} | Always Available Content`
    }
  },
  {
    path: '/content/casts',
    component: Casts,
    meta: {
      title: `${config.tenant.name} | Casts`
    }
  },
  {
    path: '/content/categories',
    component: Categories,
    meta: {
      title: `${config.tenant.name} | Categories`
    }
  },
  {
    path: '/content/featured',
    component: Featured,
    meta: {
      title: `${config.tenant.name} | Featured Content`
    }
  },
  {
    path: '/content/liveOnly',
    component: LiveOnly,
    meta: {
      title: `${config.tenant.name} | Live Only Content`
    }
  },
  {
    path: '/content/similar',
    component: Similar,
    meta: {
      title: `${config.tenant.name} | Similar Content`
    }
  },
  // Resources
  {
    path: '/resources/janus',
    component: Janus,
    meta: {
      title: `${config.tenant.name} | Janus Resources`
    }
  },
  {
    path: '/resources/janus/:id',
    component: JanusServer,
    meta: {
      title: `${config.tenant.name} | Janus Server`
    }
  },
  // Nav
  {
    path: '/notFound',
    component: NotFound,
    meta: {
      title: `${config.tenant.name} | Page Not Found`
    }
  },
  {
    path: '/',
    component: Home,
    meta: {
      title: `${config.tenant.name} | Home`
    }
  },
  // Session
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
      config,
      eventBus
    }
  })
})
