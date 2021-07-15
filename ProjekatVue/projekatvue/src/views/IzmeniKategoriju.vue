<template>
  <div>
    <form @submit.prevent="izmeni">
      <div class="form-group">
        <label for="username">Naziv</label>
        <input v-model="naziv" type="text" class="form-control" id="username" aria-describedby="usernameHelp">
      </div>
      <div class="form-group">
        <label for="komentar">Opis</label>
        <textarea v-model="opis" class="form-control" id="komentar" rows="5"></textarea>
      </div>
      <button type="submit" class="btn btn-primary mt-2">Izmeni</button>
    </form>
  </div>
</template>

<script>
import router from "../router";
import {mapState, mapActions} from 'vuex';

export default {
  name: "IzmeniKategoriju",
  data() {
    return {
      naziv: '',
      opis: '',
      id: -1,
    }
  },
  methods: {
    ...mapActions(['izmeni_kategoriju']),
    izmeni() {
      const payload = JSON.stringify({ime: this.naziv, opis: this.opis, id: this.id});
      this.izmeni_kategoriju(payload);
      router.push({path: `/kategorijeAdmin`})
    }

  },
  computed: {
    ...mapState(['kategorije']),

  },
  mounted() {
    for (let i = 0; i < this.kategorije.length; i++) {
      if (this.kategorije[i].id === parseInt(this.$route.params.id)) {   /// AKO NE RADI STAVI U MOUNTED
        this.naziv = this.kategorije[i].ime;
        this.opis = this.kategorije[i].opis;
        this.id = this.kategorije[i].id;
      }
    }
  }
}
</script>

<style scoped>

</style>