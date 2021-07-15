<template>
  <div>
    <div class="col-12">
      <br>
      <table class="table" style="border: 1px solid">
        <thead>
        <tr>
          <th scope="col">Naslov</th>
          <th scope="col">Tekst</th>
          <th scope="col">Kategorija</th>
          <th scope="col">Datum</th>
          <th scope="col">Brisanje</th>
          <th scope="col">Izmena</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="vest in vestiZaPrikaz" :key="vest.id" >
          <td>
            <a v-on:click="otvoriVest(vest.id)" >{{ vest.naslov }}</a>
          </td>
          <td>{{ vest.tekst | pocetakTeksta}}</td>
          <td>{{ vest.kategorija.ime }}</td>
          <td>{{ formatDate(vest.vremeKreiranja) }}</td>
          <td>
            <button v-on:click="remove(vest.id)" class="btn btn-danger">obrisi</button>
          </td>
          <td>
            <button v-on:click="change(vest.id)" class="btn btn-warning">Izmeni</button>
          </td>
        </tr>
        </tbody>
      </table>
      <button v-on:click="add" class="btn btn-primary">dodaj</button>
      <div v-if="currPage > 1">
        <button v-on:click="smanji">&lt;</button>
      </div>
      <div v-if="(vesti.length - currPage*5 > 0)">
        <button v-on:click="povecaj">&gt;</button>
      </div>

    </div>

  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';
import router from "../router";
export default {
  name: "VestiAdmin",
  computed:{
    ...mapState(['vesti']),
  },
  methods:{
    ...mapActions(['ucitaj_vesti', 'obrisi_vest']),
    smanji(){
      this.vestiZaPrikaz = [];
      this.currPage--;
      let i =(this.currPage-1)*5;
      while(i<(this.currPage-1)*5 + 5 && i<this.vesti.length){
        this.vestiZaPrikaz.push(this.vesti[i]);
        i++;
      }

    },
    povecaj(){
      this.vestiZaPrikaz = [];
      this.currPage++;
      let i =(this.currPage-1)*5;
      while(i<(this.currPage-1)*5 + 5 && i<this.vesti.length){
        this.vestiZaPrikaz.push(this.vesti[i]);
        i++;
      }
    },
    formatDate(date) {
      // eslint-disable-next-line no-undef
      return moment(date).format('DD.MM.YYYY hh:mm:ss');
    },
    remove(id){
      this.obrisi_vest(id);
    },
    change(id){
      router.push({path: `/izmeniVest/${id}`});
    },
    add(){
      router.push({path:'/dodajVest'});
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
  filters: {
    pocetakTeksta(tekst){
      return tekst.substring(0, 100).concat(" ...");
    }
  },
  data(){
    return{
      currPage: 1,
      vestiZaPrikaz: []
    }
  },
  mounted() {
    this.ucitaj_vesti();
    let i =this.currPage-1;
    while(i<this.currPage-1 + 5 && i<this.vesti.length){
      this.vestiZaPrikaz.push(this.vesti[i]);
      i++;
    }

  }
}
</script>

<style scoped>

</style>