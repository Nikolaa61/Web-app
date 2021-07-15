<template>
  <div class="container-fluid" style="background: deepskyblue">
    <b-row>
      <b-col sm="3 p-0 m-0">
        <h1 style="color: white">Web Projekat</h1>
      </b-col>
      <b-col sm="1 p-0 m-0">
        <h3>
          <router-link style="color: white" to="/" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'Vesti'}">Vesti</router-link>
        </h3>
      </b-col>
      <b-col sm="1 p-0 m-0">
        <h3>
          <router-link style="color: white" to="/cms" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'CMS'}">CMS</router-link>
        </h3>
      </b-col>
      <b-col sm="5 p-2 m-0" >
        <h3 style="color: white" v-if="korisnik">
          <span>{{korisnik}}</span>
          <button v-on:click="odjava" class="m-1 btn btn-warning">Odjavi se</button>
        </h3>
      </b-col>
    </b-row>
  </div>


</template>

<script>
import {mapActions, mapState} from "vuex";
import router from "../router";

export default {
  name: "Navbar",
  data(){
    return {
      selectedVest: null,
      vesti: [],
      sviKomentari: [],
      odabraneVesti: [],
      proba : '',
      imePrezimee : null,
    }
  },
  filters: {
    pocetakTeksta(tekst){
      return tekst.substring(0, 100).concat(" ...");
    }
  },
  methods:{
    ...mapActions(['skloni_korisnika', 'set_korisnik', 'remove_korisnik']),
    formatDate(date) {
      // eslint-disable-next-line no-undef
      return moment(date).format('DD.MM.YYYY hh:mm:ss');
    },
    odjava(){
      localStorage.removeItem('jwt');
      this.skloni_korisnika();
      this.remove_korisnik();
      router.push({name: `Login`})
    },
    povecajBrojPoseta(id){
      if(!this.$cookies.get(id)){
        this.$cookies.set(id, 1, {expires :'1Y'});
        for(let i =0; i<this.vesti.length; i++){
          if(this.vesti[i].id == id){
            this.vesti[i].brojPoseta+= 1;
          }
        }
        fetch(`http://localhost:8081/api/vesti/povecaj/${id}`, {
          method: 'POST',
        }).catch(e =>{
          alert("Greska pri povecanju poseta komentara" + e);
        });
      }else{
        console.log("vec pregledana vest");
      }
    },
    napuniKomentare(id){
      fetch(`http://localhost:8081/api/komentari/${id}`,  { method: 'get' }).then((response) => {
        if (!response.ok)
          throw response;
        return response.json();
      }).then((jsonData) => {
        this.sviKomentari = jsonData;
      }).catch((error) => {
        if (typeof error.text === 'function')
          error.text().then((errorMessage) => {
            alert(errorMessage);
          });
        else
          alert(error);
      });
    }
  },
  mounted() {
    fetch('http://localhost:8081/api/vesti',  { method: 'get' }).then((response) => {
      if (!response.ok)
        throw response;

      return response.json()
    }).then((jsonData) => {
      this.vesti = jsonData;
    }).catch((error) => {
      if (typeof error.text === 'function')
        error.text().then((errorMessage) => {
          alert(errorMessage);
        });
      else
        alert(error);
    });
    const jwt = localStorage.getItem('jwt');
    if (jwt) {
      const payload = JSON.parse(atob(jwt.split('.')[1]));
      this.set_korisnik(payload.nick);
    }
  },
  computed:{
    ...mapState(['currentUser', 'korisnik'])
  }
}
</script>

<style scoped>

</style>