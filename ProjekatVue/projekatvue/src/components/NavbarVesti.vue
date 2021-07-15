<template>
  <div class="container-fluid" style="background: deepskyblue">
    <b-row>
      <b-col sm="3 p-0 m-0">
        <h3>
          <router-link style="color: white" to="/homePage" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'HomePage'}">HomePage</router-link>
        </h3>
      </b-col>
      <b-col sm="1 p-0 m-0">
        <h3>
          <router-link style="color: white" to="/najcitanije" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'Najcitanije'}">Najcitanije</router-link>
        </h3>
      </b-col>
      <b-col sm="4 p-0 m-0">
        <h3>
          <router-link style="color: white" to="/svakaStavka" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'CMS'}">CMS</router-link>
        </h3>
      </b-col>
    </b-row>
  </div>
</template>

<script>
export default {
  name: "NavbarVesti"
}
</script>

<style scoped>

</style>