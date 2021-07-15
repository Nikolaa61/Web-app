import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'moment'
import VueSession from 'vue-session'
Vue.use(require('vue-cookies'))
import VueCookies from 'vue-cookies'

Vue.use(VueSession)
Vue.config.productionTip = false
Vue.use(BootstrapVue)
// Require dependencies
// Tell Vue to use the plugin
Vue.use(VueCookies);
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
