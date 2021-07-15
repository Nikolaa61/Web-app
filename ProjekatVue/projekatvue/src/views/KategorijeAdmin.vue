<template>
  <div class="col-12">
    <br>
    <table class="table">
      <tbody>
      <tr v-for="kategorija in kategorije" :key="kategorija.ime" style="border: 1px solid">
        <td>{{kategorija.ime}}</td>
        <td>{{kategorija.opis}}</td>
        <td>
          <button v-on:click="remove(kategorija.id); selectedComponent = kategorija" class="btn btn-danger">obrisi</button>
          <br>
          <small v-if="multiple_value_boolean(kategorija.id)" style="color: red"><b> Nije moguce brisanje, postoje vesti iz te kategorije </b></small>
        </td>
        <td>
          <button v-on:click="change(kategorija.id)" class="btn btn-warning">Izmeni</button>
        </td>
      </tr>
      </tbody>
    </table>
    <button v-on:click="add" class="btn btn-primary">dodaj</button>
  </div>
</template>

<script>
import router from "../router";
import { mapState, mapActions } from 'vuex';

export default {
  name: "KategorijeAdmin",
  computed :{
    ...mapState(['kategorije'])
  },
  data() {
    return {
      selectedKategorija: null,
      moze : Boolean,
      selectedComponent : null
    }
  },
  methods:{
    ...mapActions(['ucitaj_kategorije', "izbaci_kategoriju", 'ucitaj_korisnika']),

    multiple_value_boolean(id) {
      return this.selectedComponent!=null  && this.selectedComponent.id == id && this.moze == false;
    },
    change(id){
      console.log(id);
      router.push({path:`/izmeniKategoriju/${id}`})
    },
    add(){
      router.push({path:'/dodajKategoriju'})
    },
    remove(id){
      fetch(`http://localhost:8081/api/kategorije/${id}`, {method: 'delete'}).then((response) => {
        if (!response.ok)
          throw response;

        return response.json();
      }).then((jsonData) => {
        this.moze = jsonData;
        if(this.moze === true){
          this.izbaci_kategoriju(this.selectedComponent.id);
          this.selectedComponent = null;
        }
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
      this.ucitaj_kategorije();
  }
}
</script>

<style scoped>

</style>