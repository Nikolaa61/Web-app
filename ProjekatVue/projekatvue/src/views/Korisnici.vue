<template>
  <div>
    <div class="col-12">
      <br>
      <table class="table" style="border: 1px solid">
        <thead>
        <tr>
          <th scope="col">Ime</th>
          <th scope="col">Prezime</th>
          <th scope="col">Email</th>
          <th scope="col">Tip korisnika</th>
          <th scope="col">Izmena</th>
          <th scope="col">Status</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="korisnik in korisnici" :key="korisnik.id_korisnika" >
          <td>{{korisnik.ime}}</td>
          <td>{{korisnik.prezime}}</td>
          <td>{{korisnik.email}}</td>
          <td>{{korisnik.tipKorisnika}}</td>
          <td>
            <button v-on:click="change(korisnik.id)" class="btn btn-warning">Izmeni</button>
          </td>
          <td v-if="aktivan_i_nijeAdmin(korisnik)">
            <button v-on:click="deaktiviraj(korisnik.id)" class="btn btn-primary">AKTIVAN</button>
          </td>
          <td v-else-if="neaktivan_i_nijeAdmin(korisnik)">
            <button v-on:click="aktiviraj(korisnik.id)" class="btn btn-danger">NEAKTIVAN</button>
          </td>
          <td v-else>
            <span>Aktivan</span>
          </td>

        </tr>
        </tbody>
      </table>
      <button v-on:click="add" class="btn btn-primary">dodaj</button>

    </div>

  </div>
</template>

<script>
import {mapActions, mapState} from "vuex";
import router from "../router";

export default {
  name: "Korisnici",
  methods:{
    ...mapActions(['ucitaj_korisnike', "aktiviraj_korisnika", 'deaktiviraj_korisnika']),
    aktivan_i_nijeAdmin(korisnik) {
      return korisnik.status =='ACTIVE' && korisnik.tipKorisnika != 'Admin';
    },
    neaktivan_i_nijeAdmin(korisnik){
      return korisnik.status =='INACTIVE' && korisnik.tipKorisnika != 'Admin';
    },
    aktiviraj(id){
      this.aktiviraj_korisnika(id);
    },
    deaktiviraj(id){
      this.deaktiviraj_korisnika(id);
    },
    add(){
     router.push({path:'/dodajKorisnika'});
    },
    change(id){
      router.push({path:`/izmeniKorisnika/${id}`})
    }
  },
  computed :{
    ...mapState(['korisnici'])
  },
  mounted() {
    this.ucitaj_korisnike();
  }
}
</script>

<style scoped>

</style>