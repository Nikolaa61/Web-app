<template>
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
          <a v-on:click="otvoriVest(vest.id)" >{{ vest.naslov }}</a>
        </td>
        <td>{{ vest.tekst | pocetakTeksta}}</td>
        <td>{{ vest.kategorija.ime }}</td>
        <td>{{ formatDate(vest.vremeKreiranja) }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import {mapActions, mapState, mapGetters} from "vuex";
import router from "../router";

export default {
  name: "PretragaVesti",
  data(){
    return{
      vestiZaPrikaz:[],
    }
  },
  computed:{
    ...mapState(['vesti']),
    ...mapGetters({
      pretragaFromStore:'name'
    }),
    pretraga:{
      get(){
        return this.pretragaFromStore
      },
      set(newName){
        return newName
      }
    }
  },
  methods:{
    ...mapActions(['ucitaj_vesti']),
    formatDate(date) {
      // eslint-disable-next-line no-undef
      return moment(date).format('DD.MM.YYYY hh:mm:ss');
    },
    otvoriVest(id){
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
      router.push({path:`/singleVestAdmin/${id}`})
    }

  },
  watch:{
    pretraga: function (){
      console.log("promena");
      this.vestiZaPrikaz = [];
      for(let i =0; i<this.vesti.length; i++){
        if(this.vesti[i].tekst.includes(this.tekst) || this.vesti[i].naslov.includes(this.tekst)){
          this.vestiZaPrikaz.push(this.vesti[i]);
        }
      }
    }
  },
  filters: {
    pocetakTeksta(tekst){
      return tekst.substring(0, 100).concat(" ...");
    }
  },
  mounted() {
    console.log("ucitane vesti");
    this.ucitaj_vesti();
  }
}
</script>

<style scoped>

</style>