<template>
  <div>
    <div v-if="!selectedVest">
      <div class="col-12">
        <table class="table">
          <thead>
          <tr>
            <th style="text-align: center; font-size: xxx-large" scope="col">Kategorije</th>
          </tr>
          </thead>
          <tbody>
          <tr style="text-align: center; font-size: xx-large" v-for="kategorija in kategorije" :key="kategorija.id">
            <td>
              <a v-on:click="vesti(kategorija.id)">{{  kategorija.ime  }}</a>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <div style="text-align: center" class="col-12" v-if="selectedKategorija">
        <div v-if="vestiIzKategorije.length > 0">
          <div class="col-12">
            <table class="table">
              <thead>
              <tr>
                <th scope="col">Naslov</th>
                <th scope="col">Tekst</th>
                <th scope="col">Kategorija</th>
                <th scope="col">Datum</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="vest in vestiIzKategorije" :key="vest.id">
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
  name: "SvakaStavkaKategorije",
  components: {
    Vest
  },
  data() {
    return {
      kategorije: [],
      vestiIzKategorije:[],
      selectedKategorija: null,
      selectedVest: null,
      sviKomentari : []
    }
  },
  methods: {
    vesti(id){
      for(let i =0; i <this.kategorije.length; i++){
        if (this.kategorije[i].id == id){
          this.selectedKategorija = this.kategorije[i];
        }
      }
      fetch(`http://localhost:8081/api/vesti/kategorija/${id}`, {method: 'get'}).then((response) => {
        if (!response.ok)
          throw response;

        return response.json()
      }).then((jsonData) => {
        this.vestiIzKategorije = jsonData;
      }).catch((error) => {
        if (typeof error.text === 'function')
          error.text().then((errorMessage) => {
            alert(errorMessage);
          });
        else
          alert(error);
      });
    },
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
  filters:{
    pocetakTeksta(tekst){
      return tekst.substring(0, 100).concat(" ...");
    }
  },
  mounted() {
    fetch('http://localhost:8081/api/kategorije', {method: 'get'}).then((response) => {
      if (!response.ok)
        throw response;

      return response.json()
    }).then((jsonData) => {
      this.kategorije = jsonData;
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

<style scoped>

</style>