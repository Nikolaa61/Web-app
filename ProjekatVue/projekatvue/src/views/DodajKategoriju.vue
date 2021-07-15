<template>
  <div>
    <form @submit.prevent="dodaj">
      <div class="form-group">
        <label for="username">Naziv</label>
        <input v-model="naziv" type="text" class="form-control" id="username" aria-describedby="usernameHelp">
      </div>
      <div class="form-group">
        <label for="komentar">Opis</label>
        <textarea v-model="opis"  class="form-control" id="komentar" rows="5"></textarea>
      </div>
      <button type="submit" class="btn btn-primary mt-2">Dodaj</button>
      <small v-if="postoji" style="color: red"> <b>Ne mozes da dodas kategoriju sa istim imenom</b></small>
    </form>
  </div>

</template>

<script>
import router from "../router";
import { mapActions } from 'vuex';

export default {
  name: "DodajKategoriju",
  data(){
    return{
      naziv:'',
      opis:'',
      postojeceKategorije:[],
      postoji : null
    }
  },
  mounted() {
    fetch('http://localhost:8081/api/kategorije',  { method: 'get' }).then((response) => {
      if (!response.ok)
        throw response;
      return response.json();
    }).then((jsonData) => {
      this.postojeceKategorije = jsonData;
    }).catch((error) => {
      if (typeof error.text === 'function')
        error.text().then((errorMessage) => {
          alert(errorMessage);
        });
      else
        alert(error);
    });
    // uvezi kategorije i proveri da li postoji sa tim imenom, ako postoji spreci dodavanje i ispisi crveni tekst
  },
  methods:{
    ...mapActions(['nova_kategorija']),
    dodaj(){
      let postoji = 0;
      for(let i=0; i<this.postojeceKategorije.length; i++){
        if(this.postojeceKategorije[i].ime === this.naziv){
          postoji = 1;
        }
      }
      if (!postoji){
        const payload = JSON.stringify({ime: this.naziv, opis: this.opis});
        this.nova_kategorija(payload);
        postoji = null;
        router.push({path: `/kategorijeAdmin`})
      }else{
        this.postoji = true;
      }


    }
  }
}
</script>

<style scoped>

</style>