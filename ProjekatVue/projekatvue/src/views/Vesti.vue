<template>
  <div class="container-fluid">
    <div style="background: deepskyblue">
      <b-row>
        <b-col sm="3 p-0 m-0">
          <router-link style="color: white" to="/homePage" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'HomePage'}">Home page</router-link>
        </b-col>
        <b-col sm="3 p-0 m-0">
          <router-link style="color: white" to="/najcitanije" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'Najcitanije'}">Najcitanije</router-link>
        </b-col>
        <b-col sm="4 p-0 m-0">
          <router-link style="color: white" to="/svakaStavkaKategorije" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'SvakaStavkaKategorije'}">Svaka stavka kategorije</router-link>
        </b-col>
        <b-col sm="2 p-0 m-0">
          <b-input style="width: 80%" v-model="tekst" ></b-input>
        </b-col>
      </b-row>
    </div>
    <br>
    <div v-if="multiple_value_boolean">
      <div class="col-12">
        <table class="table" style="border: 1px solid">
          <thead>
          <tr>
            <th scope="col">Naslov</th>
            <th scope="col">Tekst</th>
            <th scope="col">Kategorija</th>
            <th scope="col">Datum</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="vest in vestiZaPrikaz" :key="vest.id">
            <td>
              <a v-on:click="povecajBrojPoseta(vest.id); napuniKomentare(vest.id);selectedVest= vest" >{{ vest.naslov }}</a>
            </td>
            <td>{{ vest.tekst | pocetakTeksta}}</td>
            <td>{{ vest.kategorija.ime }}</td>
            <td>{{ formatDate(vest.vremeKreiranja) }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div v-if="!tekst">
      <div class="container">
        <router-view/>
      </div>
    </div>

    <div class="col-sm-12">
      <vest v-if="selectedVest" :vest="selectedVest" :svi-komentari="sviKomentari"></vest>
    </div>

  </div>

</template>

<script>
import Vest from "./Vest";

export default {
  name: 'Vesti',
  components: {
    Vest

  },
  data(){
    return {
      vesti: [],
      tekst:'',
      vestiZaPrikaz:[],
      sviKomentari:[],
      selectedVest: null
    }
  },
  computed: {
    multiple_value_boolean() {
      return this.vestiZaPrikaz.length > 0 && !this.selectedVest;
    }
  },
  filters: {
    pocetakTeksta(tekst){
      return tekst.substring(0, 100).concat(" ...");
    }
  },
  watch:{
    tekst: function (){
      this.vestiZaPrikaz = [];
      for(let i =0; i<this.vesti.length; i++){
        if ((this.vesti[i].tekst.includes(this.tekst) || this.vesti[i].naslov.includes(this.tekst)) && this.tekst != false){
          this.vestiZaPrikaz.push(this.vesti[i]);
        }
      }
      if (this.vestiZaPrikaz.length == 0){
        this.selectedVest = null;
      }
    }
  },
  methods:{
    formatDate(date) {
      // eslint-disable-next-line no-undef
      return moment(date).format('DD.MM.YYYY hh:mm:ss');
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
    fetch('http://localhost:8081/api/vesti', {method: 'get'}).then((response) => {
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
  }
}
</script>
