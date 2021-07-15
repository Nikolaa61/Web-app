<template>
  <div class="card-body">
    <h1>
      {{vest.naslov}}
    </h1>
    <p>
      Autor: <b>{{vest.autor.ime + " " +vest.autor.prezime}}</b>
      <br>
      <small>
        {{formatDate(vest.vremeKreiranja)}}
      </small>
      <br><br>
      <span>Tagovi: </span>
      <span v-for="(tag, index) in vest.tagovi" :key="index">
        <b><a @click="vestiZaTag(tag.tag)" >{{tag.tag+" "}}</a></b>
      </span>
      <br>
      <br>
      {{vest.tekst}}
    </p>

    <h6>New comment</h6>

    <form @submit.prevent="komentarisi">
      <div class="form-group">
        <label for="username">Username</label>
        <input v-model="autor" type="text" class="form-control" id="username" aria-describedby="usernameHelp" placeholder="Enter username">
      </div>
      <div class="form-group">
        <label for="komentar">Komentar</label>
        <textarea v-model="komentar"  class="form-control" id="komentar" rows="5" placeholder="Komentar"></textarea>
      </div>
      <button type="submit" class="btn btn-primary mt-2">Komentarisi</button>
    </form>


    <br>
    <h6>Komentari:</h6>
    <div v-for="komentar in sviKomentari" :key="komentar.komentar">
      <div class="card p-2 mt-1 mb-1" style="border: 1px solid">
        <h3>{{komentar.autor}}</h3>
        <p>{{komentar.komentar}}</p>
        <small>{{formatDate(komentar.datumKreiranja)}}</small>
      </div>

    </div>


  </div>
</template>

<script>
import router from "@/router";
export default {
  name: "Vest",
  data(){
    return {
      autor : '',
      komentar: '',
    }
  },
  props: {
    vest : {
      type: Object,
      required: true
    },
    sviKomentari: Array
  },
  methods:{
    formatDate(date) {
      // eslint-disable-next-line no-undef
      return moment(date).format('DD.MM.YYYY');
    },
    vestiZaTag(tag){
      router.push({path: `/sveVesti/${tag}`})
    },
    komentarisi(){
      fetch('http://localhost:8081/api/komentari', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          autor: this.autor,
          komentar: this.komentar,
          id_vesti: this.vest.id,
          datumKreiranja : new Date()
        })
      }).then(response => {
        this.autor ='';
        this.komentar ='';
        return response.json();
      }).then(comment => {
        this.sviKomentari.unshift(comment); //dodajemo na pocetak zbog sortiranja
        //this.sviKomentari.push(comment);
      }).catch(e=>{
        alert(e);
      });
    }
  }
}
</script>

<style scoped>

</style>