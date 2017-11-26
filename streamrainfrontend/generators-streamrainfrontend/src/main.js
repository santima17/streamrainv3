import Vue from 'vue'
import VueRouter  from 'vue-router';
import VueResource from 'vue-resource'
// Header & footer
import Streamrain from './vue/Streamrain.vue'
// Content
import AlwaysAvailable from './vue/content/AlwaysAvailable.vue';
import LiveOnly from './vue/content/LiveOnly.vue';
import Catalogo from './vue/content/Catalogo.vue';
// Resources
import Janus from './vue/resources/janus/Janus.vue';
import JanusServer from './vue/resources/janus/JanusServer.vue';
// Nav
import NotFound from './vue/nav/NotFound.vue';
import Home from './vue/nav/Home.vue'; 
// Session
import Login from './vue/session/Login.vue';
// Statistics
import Statistics from './vue/statistics/Statistics.vue';
// libs 
import Datetime from 'vue-datetime';

Vue.use(Datetime);
Vue.use(VueRouter);
Vue.use(VueResource);

// ======================================================
const config = require('./assets/config');
// ======================================================

const eventBus = new Vue();

const routes = [
  // Content
  {
    path: '/vod',
    component: AlwaysAvailable,
    meta: {
      title: `${config.tenant.name} | Always Available Content`
    }
  },
  {
    path: '/live',
    component: LiveOnly,
    meta: {
      title: `${config.tenant.name} | Live Only Content`
    }
  },
  {
    path: '/catalog',
    component: Catalogo,
    meta: {
      title: `${config.tenant.name} | Catalogo`
    }
},
  // Resources
  {
    path: '/janus',
    component: Janus,
    meta: {
      title: `${config.tenant.name} | Janus Resources`
    }
  },
  {
    path: '/janus/:id',
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
  // Statistics  
  {
    path: '/statistics',
    component: Statistics,
    meta: {
      title: `${config.tenant.name} | Reportes`
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
  else { // para cada pagina solicitada
    document.title = to.meta.title;
    if(to.path != '/login') { // si la pagina solicitada no es la de login
      const path = to.path;
      const session = JSON.parse(localStorage.getItem(`streamrain-${config.tenant.name.replace(/\s/g, '')}-session`)) || null;
      if(session !== undefined && session !== null) { // si tengo un token muestro la pagina solicitada   
        eventBus.$emit('pathActve',path); 
        next();
        } else { // si no tengo token redirecciono a login  
          next('/login');    
        }
    } else { // si la pagina solicitada es login borro el token de localstorage y muesto la pagina login
      eventBus.$emit('removeVueSession',null); 
      localStorage.removeItem(`streamrain-${config.tenant.name.replace(/\s/g, '')}-session`);
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
