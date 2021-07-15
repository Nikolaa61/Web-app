<template>
  <div>
    <form @submit.prevent="login">
      <div class="form-group">
        <label for="email">Email</label>
        <input v-model="email" type="text" class="form-control" id="email">
      </div>
      <div class="form-group">
        <label for="pass">Password</label>
        <input v-model="password" type="password" class="form-control" id="pass">
      </div>
      <button type="submit" class="btn btn-primary mt-2">Login</button>
    </form>
  </div>
</template>

<script>
import {mapActions, mapState} from "vuex";

export default {
  name: "Login",
  data(){
    return{
      email:'',
      password:'',
      jwt:''
    }
  },
  computed:{
    ...mapState(['currentUser'])
  },
  methods:{
    ...mapActions(['ucitaj_korisnika', 'set_korisnik']),
    login() {
      fetch('http://localhost:8081/api/korisnici/login', {
        method: 'post',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          email: this.email,
          password: this.password
        })
      }).then(response => {
        return response.json()
      }).then((response) =>{
        if (response.message){
          alert(response.message);
        }else{
          this.jwt = response.jwt;
          localStorage.setItem('jwt', this.jwt);
          if (this.jwt) {
            const payload = JSON.parse(atob(this.jwt.split('.')[1]));
            this.set_korisnik(payload.nick);
          }
          this.$router.push({name: 'KategorijeAdmin'});
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
  }
}
</script>

<style scoped>

</style>