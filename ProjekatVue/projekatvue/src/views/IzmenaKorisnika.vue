<script src="../router/index.js"></script>
<template>
  <div class="row centered-form">
    <div class="col-sm-12">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">Dodavanje korisnika</h3>
        </div>
        <div class="panel-body">
          <form role="form" @submit.prevent="dodaj">
            <div class="row">
              <div class="col-xs-6 col-sm-6 col-md-6">
                <div class="form-group">
                  <input type="text" v-model="ime" id="first_name" class="form-control input-sm" placeholder="First Name">
                </div>
              </div>
              <div class="col-xs-6 col-sm-6 col-md-6">
                <div class="form-group">
                  <input type="text" v-model="prezime" id="last_name" class="form-control input-sm" placeholder="Last Name">
                </div>
              </div>
            </div>
            <br>
            <div class="form-group">
              <input type="email" v-model="email" id="email" class="form-control input-sm" placeholder="Email Address">
              <select  id="select1">
                <option value="Admin">Admin</option>
                <option value="ContentCreator">ContentCreator</option>
              </select>
            </div>

            <br>
            <input type="submit" value="Izmeni" class="btn btn-info btn-block"><br>
            <div>
                <span v-for="(error, index) in errors" :key="index">
                  <b style="color:red;">{{error}}</b><br>
                </span>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions, mapState} from "vuex";
import router from "../router";
export default {
  name: "IzmenaKorisnika",
  data(){
    return{
      ime:'',
      prezime:'',
      email:'',
      errors: []
    }
  },
  methods:{
    ...mapActions(['dodaj_korisnika', 'ucitaj_korisnike', 'izmeni_korisnika']),
    dodaj(){
      let postoji = 0;
      for(let i=0; i<this.korisnici.length; i++){
        if(this.korisnici[i].email === this.email && this.korisnici[i].id != parseInt(this.$route.params.id)){
          postoji = 1;
        }
      }
      if (postoji){
        this.errors.push("Postoji korisnik sa tim emailom");
      }
      if (this.password != this.passwordConfirmation){
        this.errors.push("Lozinke se ne poklapaju");
      }
      if (!this.email || !this.ime || !this.prezime){
        this.errors.push("Sva polja moraju da budu popunjena");
      }
      if (this.errors.length == 0){
        const selectedElement = document.querySelector('#select1').value;
        const payload = JSON.stringify({ime: this.ime, prezime: this.prezime,
          tipKorisnika:selectedElement, email:this.email, id:parseInt(this.$route.params.id)});
        this.izmeni_korisnika(payload);
        router.push({path: '/korisnici'})
      }
    }
  },
  computed:{
    ...mapState(['korisnici'])
  },
  mounted() {
    this.ucitaj_korisnike();
    for (let i = 0; i < this.korisnici.length; i++) {
      if (this.korisnici[i].id === parseInt(this.$route.params.id)) {
        this.ime = this.korisnici[i].ime;
        this.prezime = this.korisnici[i].prezime;
        this.email = this.korisnici[i].email;
        document.getElementById('select1').value = this.korisnici[i].tipKorisnika;
      }
    }
  }
}
</script>

<style scoped>

</style>