<template>
  <div>
    <form @submit.prevent="izmeni">
      <div class="form-group">
        <br>
        <label for="username">Naslov</label>
        <input v-model="naslov" type="text" class="form-control" id="username" aria-describedby="usernameHelp">
      </div>
      <br>
      <span>Izaberi kategoriju: </span>
      <select  id="select1">
        <option v-for="kategorija in kategorije" :key="kategorija.id">{{kategorija.ime}}</option>
      </select>


      <div class="form-group">
        <br>
        <label for="komentar">Tekst</label>
        <textarea v-model="tekst"  class="form-control" id="komentar" rows="10"></textarea>
      </div>

      <div class="form-group">
        <br>
        <label for="tag">Tagovi:</label>
        <input v-model="tagovi" type="text" class="form-control" id="tag">
      </div>

      <button type="submit" class="btn btn-primary mt-2">Izmeni</button>
    </form>
  </div>
</template>

<script>
import {mapActions, mapState} from "vuex";
import router from "../router";

export default {
  name: "IzmeniVest",
  data(){
    return{
      tagovi:'',
      naslov:'',
      tekst:'',
      tagoviNiz: [],
    }
  },
  mounted() {
    this.ucitaj_kategorije();
    for (let i = 0; i < this.vesti.length; i++) {
      if (this.vesti[i].id === parseInt(this.$route.params.id)) {
        for(let j = 0; j<this.vesti[i].tagovi.length; j++){
          this.tagovi += this.vesti[i].tagovi[j].tag;
          if(j !=this.vesti[i].tagovi.length-1){
            this.tagovi +=',';
          }
        }
        this.naslov = this.vesti[i].naslov;
        this.tekst = this.vesti[i].tekst;
        document.getElementById('select1').value = this.vesti[i].kategorija.ime;
      }
    }
  },
  methods:{
    ...mapActions(['ucitaj_kategorije', 'izmeni_vest']),
    izmeni(){
      const selectedElement = document.querySelector('#select1').value;

      let idKategorije;
      for(let i =0; i<this.kategorije.length; i++){
        if (this.kategorije[i].ime == selectedElement){
          idKategorije = this.kategorije[i].id;
        }
      }

      this.tagoviNiz = this.tagovi.split(',');
      let tagici = [];

      for(let i =0; i<this.tagoviNiz.length; i++){
        tagici.push(this.tagoviNiz[i]);
        // tagici.push(JSON.stringify({tag: this.tagoviNiz[i]}));
      }
      const jwt = localStorage.getItem('jwt');
      let ident =-1;
      if (jwt) {
        const payload = JSON.parse(atob(jwt.split('.')[1]));
        ident = payload.id;
      }

      const vest = JSON.stringify({naslov: this.naslov, tekst: this.tekst, kategorija: idKategorije,  autor: parseInt(ident), tagovi: tagici});
      this.izmeni_vest(vest);
      router.push({path:'/vestiAdmin'})

    }

  },
  computed:{
    ...mapState(['kategorije', 'vesti', 'currentUser'])
  }
}
</script>

<style scoped>

</style>