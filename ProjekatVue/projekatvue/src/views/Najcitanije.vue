<template>
  <div>
    <div class="col-12">
      <div v-if="!selectedVest">
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
          <tr v-for="vest in vesti" :key="vest.id">
            <!--          <th scope="row">{{ subject.id }}</th>-->
            <td>
              <a v-on:click="povecajBrojPoseta(vest.id); napuniKomentare(vest.id);selectedVest= vest" >{{ vest.naslov }}</a>
            </td>
            <td>{{ vest.tekst  | pocetakTeksta}}</td>
            <td>{{ vest.kategorija.ime}}</td>
            <td>{{ formatDate(vest.vremeKreiranja) }}</td>
          </tr>
          </tbody>
        </table>
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
  name: "Najcitanije",
  components: {
    Vest
  },
  data(){
    return {
      vesti: [],
      selectedVest : null,
      sviKomentari:[],
    }
  },
  filters: {
    pocetakTeksta(tekst){
      return tekst.substring(0, 100).concat(" ...");
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
    fetch('http://localhost:8081/api/vesti/najcitanije',  { method: 'get' }).then((response) => {
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

<style scoped>

</style>