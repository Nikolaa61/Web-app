<template>
  <div>
    <div class="container-fluid" style="background: deepskyblue">
      <b-row>
        <b-col sm="3 p-0 m-0">
          <router-link style="color: white" to="/kategorijeAdmin" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'KategorijeAdmin'}">Kategorije</router-link>
        </b-col>
        <b-col sm="3 p-0 m-0">
          <router-link style="color: white" to="/vestiAdmin" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'VestiAdmin'}">Vesti admin</router-link>
        </b-col>
        <b-col sm="3 p-0 m-0">
          <router-link style="color: white" to="/korisnici" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'Korisnici'}">Korisnici</router-link>
        </b-col>

        <b-col sm="2 p-0 m-0">
          <router-link style="color: white" to="/pretragaVesti" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'PretragaVesti'}"> <b-input style="width: 80%" v-model="pretraga" ></b-input></router-link>
        </b-col>
      </b-row>

    </div>
    <div class="container">
      <router-view/>
    </div>
  </div>

</template>

<script>

import {mapActions, mapState} from "vuex";

export default {
  name: 'CMS',
  computed:{
    ...mapState(['vesti', "pretraga", 'currentUser']),
  },
  methods: {
    ...mapActions(['ucitaj_vesti', "napuni_pretragu"]),
  },
  watch:{
    pretraga: function (){
      this.napuni_pretragu(this.pretraga);
    }
  }

}
</script>
