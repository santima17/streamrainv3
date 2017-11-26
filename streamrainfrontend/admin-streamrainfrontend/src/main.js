import Vue from 'vue'
import VueRouter  from 'vue-router';
import VueResource from 'vue-resource'
// Header & footer
import Streamrain from './vue/Streamrain.vue'
// Nav
import CreateGenerator from './vue/nav/createGenerator.vue';
import ListGenerators from './vue/nav/listGenerators.vue';
import BlockContent from './vue/nav/blockContent.vue';
import BlockUser from './vue/nav/blockUser.vue';
import Home from './vue/nav/Home.vue'; 
import NotFound from './vue/nav/NotFound.vue';
// Session
import Login from './vue/session/Login.vue';
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
  // Nav
  {
    path: '/notFound',
    component: NotFound,
    meta: {
      title: `StreamRain | Page Not Found`
    }
  },
  {
    path: '/',
    component: Home,
    meta: {
      title: `StreamRain | Home`
    }
  },
  {
    path: '/blockuser',
    component: BlockUser,
    meta: {
      title: `StreamRain | Home`
    }
  },
  {
    path: '/blockContent',
    component: BlockContent,
    meta: {
      title: `StreamRain | Home`
    }
  },
  {
    path: '/createGenerator',
    component: CreateGenerator,
    meta: {
      title: `StreamRain | Home`
    }
  },
  {
    path: '/listGenerators',
    component: ListGenerators,
    meta: {
      title: `StreamRain | Home`
    }
  },
  // Session
  {
    path: '/login',
    component: Login,
    meta: {
      title: `StreamRain | Log In`
    }
  },
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
      const session = JSON.parse(localStorage.getItem(`streamrain-session`)) || null;
      if(session !== undefined && session !== null) { // si tengo un token muestro la pagina solicitada   
        eventBus.$emit('pathActve',path); 
        next();
        } else { // si no tengo token redirecciono a login  
          next('/login');    
        }
    } else { // si la pagina solicitada es login borro el token de localstorage y muesto la pagina login
      eventBus.$emit('removeVueSession',null); 
      localStorage.removeItem(`streamrain-session`);
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
