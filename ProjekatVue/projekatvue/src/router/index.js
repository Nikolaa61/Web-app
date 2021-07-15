import Vue from 'vue'
import VueRouter from 'vue-router'
import Vesti from '../views/Vesti.vue'

Vue.use(VueRouter)


const routes = [
  {
    path: '/',
    name: 'Vesti',
    component: Vesti,
    children: [
      {
        path: '/homePage',
        name: 'HomePage',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/HomePage.vue')
      },
      {
        path: '/najcitanije',
        name: 'Najcitanije',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/Najcitanije.vue')
      },
      {
        path: '/svakaStavkaKategorije',
        name: 'SvakaStavkaKategorije',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/SvakaStavkaKategorije.vue')
      },
      {
        path: '/singleVestAdmin/:id',
        name: 'SingleVestAdmin',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/SingleVestAdmin.vue')
      }
    ]
  },
  {
    path: '/cms',
    name: 'CMS',
    meta: {
      authRequired: true,
    },

    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/CMS.vue'),
    children: [
      {
        path: '/kategorijeAdmin',
        name: 'KategorijeAdmin',
        meta: {
          authRequired: true,
        },
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/KategorijeAdmin.vue')
      },
      {
        path: '/dodajKategoriju',
        name: 'DodajKategoriju',
        meta: {
          authRequired: true,
        },
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/DodajKategoriju.vue')
      },
      {
        path: '/dodajVest',
        name: 'DodajVest',
        meta: {
          authRequired: true,
        },
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/DodajVest.vue')
      },
      {
        path: '/dodajKorisnika',
        name: 'DodajKorisnika',
        meta: {
          authRequired: true,
          adminRequired: true
        },
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/DodajKorisnika.vue')
      },
      {
        path: '/korisnici',
        name: 'Korisnici',
        meta: {
          authRequired: true,
          adminRequired: true
        },
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/Korisnici.vue')
      },
      {
        path: '/izmeniKategoriju/:id',
        name: 'IzmeniKategoriju',
        meta: {
          authRequired: true,
        },
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/IzmeniKategoriju.vue')
      },
      {
        path: '/izmeniKorisnika/:id',
        name: 'IzmenaKorisnika',
        meta: {
          authRequired: true,
          adminRequired: true
        },
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/IzmenaKorisnika.vue')
      },
      {
        path: '/izmeniVest/:id',
        name: 'IzmeniVest',
        meta: {
          authRequired: true,
        },
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/IzmeniVest.vue')
      },
      {
        path: '/vestiAdmin',
        name: 'VestiAdmin',
        meta: {
          authRequired: true,
        },
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/VestiAdmin.vue')
      },
      {
        path: '/pretragaVesti',
        name: 'PretragaVesti',
        meta: {
          authRequired: true,
        },
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/PretragaVesti.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path:'/sveVesti/:tag',
    name: 'SveVesti',
    component: () => import(/* webpackChunkName: "about" */ '../views/SveVesti.vue'),
  }
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.authRequired) {


    const jwt = localStorage.getItem('jwt');

    if (!jwt) {
      next({name: 'Login'});
      return;
    }
    const payload = JSON.parse(atob(jwt.split('.')[1]));
     /// payload.role i payload.status
    const expDate = new Date(payload.exp * 1000);
    const role = payload.role;
    if (to.meta.adminRequired){
      if (role != 'Admin'){
        alert("Samo admin moze da lista korisnike");
        return;
      }
    }
    if (expDate < new Date()) {
      next({name: 'Login'});
      return;
    }
  }

  next();
});
export default router
