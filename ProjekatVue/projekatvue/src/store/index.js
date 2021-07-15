import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        kategorije: [],
        vesti: [],
        pretraga:'',
        korisnici:[],
        currentUser: null,
        korisnik:null
    },

    mutations: {
        set_kategorije: function(state, kategorije){
            state.kategorije = kategorije;
        },
        set_korisnici: function(state, korisnici){
            state.korisnici = korisnici;
        },
        korisnik: function(state, korisnik){
            state.korisnik = korisnik;
        },
        logout: function(state){
            state.korisnik = null;
        },
        set_korisnik: function(state, korisnik){
            state.currentUser = korisnik;
        },
        odjavi: function (state){
          state.currentUser = null;
        },
        set_vesti: function (state, vesti){
            state.vesti = vesti;
        },
        dodaj_kategoriju: function (state, body){
            state.kategorije.push(body);
        },
        set_pretraga: function (state, tekst){
            state.pretraga = tekst;
        },
        add_vest: function (state, body){
            state.vesti.push(body);
        },
        add_korisnik: function(state, body){
          state.korisnici.push(body);
        },
        izbrisi_kategoriju: function (state, id){
            for (var i = 0; i < state.kategorije.length; i++) {
                if (state.kategorije[i].id == id) {
                    state.kategorije.splice(i, 1);
                }
            }
        },
        setActive_korisnik: function(state, id){
            for (var i = 0; i < state.korisnici.length; i++) {
                if (state.korisnici[i].id == id) {
                    state.korisnici[i].status = 'ACTIVE';
                }
            }
        },
        deactovate_korisnik: function(state, id){
            for (var i = 0; i < state.korisnici.length; i++) {
                if (state.korisnici[i].id == id) {
                    state.korisnici[i].status = 'INACTIVE';
                }
            }
        },
        delete_vest: function(state, id){
            for (var i = 0; i < state.vesti.length; i++) {
                if (state.vesti[i].id == id) {
                    state.vesti.splice(i, 1);
                }
            }
        },
        change_kategorija: function(state, kategorija){
            for (var i = 0; i < state.kategorije.length; i++) {
                if (state.kategorije[i].id == kategorija.id) {
                    state.kategorije[i].ime = kategorija.ime;
                    state.kategorije[i].opis = kategorija.opis;
                }
            }
        },
        update_korisnik: function(state, korisnik){
            for (var i = 0; i < state.korisnici.length; i++) {
                if (state.korisnici[i].id == korisnik.id) {
                    state.korisnici[i].ime = korisnik.ime;
                    state.korisnici[i].prezime = korisnik.prezime;
                    state.korisnici[i].tipKorisnika = korisnik.tipKorisnika;
                    state.korisnici[i].email = korisnik.email;
                }
            }
        },
        change_vest: function(state, vest){
            for (var i = 0; i < state.vesti.length; i++) {
                if (state.vesti[i].id == vest.id) {
                    state.vesti[i] = vest;
                }
            }
        }

    },

    actions: {

        ucitaj_kategorije: function ({ commit }) {
            fetch('http://localhost:8081/api/kategorije', { method: 'get' }).then((response) => {
                if (!response.ok)
                    throw response;

                return response.json()
            }).then((jsonData) => {
                commit('set_kategorije', jsonData)
            }).catch((error) => {
                if (typeof error.text === 'function')
                    error.text().then((errorMessage) => {
                        alert(errorMessage);
                    });
                else
                    alert(error);
            });

        },

        ucitaj_korisnike: function ({ commit }) {
            fetch('http://localhost:8081/api/korisnici', { method: 'get' }).then((response) => {
                if (!response.ok)
                    throw response;

                return response.json()
            }).then((jsonData) => {
                commit('set_korisnici', jsonData)
            }).catch((error) => {
                if (typeof error.text === 'function')
                    error.text().then((errorMessage) => {
                        alert(errorMessage);
                    });
                else
                    alert(error);
            });

        },

        aktiviraj_korisnika: function ({commit}, id){
            fetch(`http://localhost:8081/api/korisnici/aktiviraj/${id}`, {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then((response) => {
                if (!response.ok)
                    throw response;
                commit('setActive_korisnik', id);
            }).catch((error) => {
                if (typeof error.text === 'function')
                    error.text().then((errorMessage) => {
                        alert(errorMessage);
                    });
                else
                    alert(error);
            });
        },

        deaktiviraj_korisnika: function ({commit}, id){
            fetch(`http://localhost:8081/api/korisnici/deaktiviraj/${id}`, {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then((response) => {
                if (!response.ok)
                    throw response;
                commit('deactovate_korisnik', id);
            }).catch((error) => {
                if (typeof error.text === 'function')
                    error.text().then((errorMessage) => {
                        alert(errorMessage);
                    });
                else
                    alert(error);
            });
        },

        ucitaj_vesti: function ({ commit }) {
            fetch('http://localhost:8081/api/vesti', {method: 'get'}).then((response) => {
                if (!response.ok)
                    throw response;

                return response.json()
            }).then((jsonData) => {
                commit('set_vesti', jsonData);
            }).catch((error) => {
                if (typeof error.text === 'function')
                    error.text().then((errorMessage) => {
                        alert(errorMessage);
                    });
                else
                    alert(error);
            });

        },

        nova_kategorija: function ({commit}, payload){
            fetch('http://localhost:8081/api/kategorije/1', {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: payload
            }).then((response) => {
                if (!response.ok)
                    throw response;
                return response.json();
            }).then((jsonData) => {
                commit('dodaj_kategoriju', jsonData);
            }).catch((error) => {
                if (typeof error.text === 'function')
                    error.text().then((errorMessage) => {
                        alert(errorMessage);
                    });
                else
                    alert(error);
            });
        },

        izbaci_kategoriju: function({commit}, id){
            commit('izbrisi_kategoriju', id);
        },

        izmeni_kategoriju: function ({commit}, payload){
            fetch('http://localhost:8081/api/kategorije/2', {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: payload
            }).then((response) => {
                if (!response.ok)
                    throw response;
                return response.json();
            }).then((jsonData) => {
                commit('change_kategorija', jsonData);
            }).catch((error) => {
                console.log(payload);
                if (typeof error.text === 'function')
                    error.text().then((errorMessage) => {
                        alert(errorMessage);
                    });
                else
                    alert(error);
            });
        },

        izmeni_vest: function ({commit}, payload){
            fetch('http://localhost:8081/api/vesti/2', {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: payload
            }).then((response) => {
                if (!response.ok)
                    throw response;
                return response.json();
            }).then((jsonData) => {
                commit('change_vest', jsonData);
            }).catch((error) => {
                console.log(payload);
                if (typeof error.text === 'function')
                    error.text().then((errorMessage) => {
                        alert(errorMessage);
                    });
                else
                    alert(error);
            });
        },

        obrisi_vest: function ({commit}, id) {
            fetch(`http://localhost:8081/api/vesti/${id}`, {method: 'delete'
            }).then((response) => {
                if (!response.ok)
                    throw response;
                return;
            }).then(() => {
                commit('delete_vest', id);
            }).catch((error) => {
                if (typeof error.text === 'function')
                    error.text().then((errorMessage) => {
                        alert(errorMessage);
                    });
                else
                    alert(error);
            });
        },

        dodaj_vest: function ({commit}, payload){
            fetch('http://localhost:8081/api/vesti/1', {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: payload
            }).then((response) => {
                if (!response.ok)
                    throw response;
                return response.json();
            }).then((jsonData) => {
                commit('add_vest', jsonData);
            }).catch((error) => {
                console.log(payload);
                if (typeof error.text === 'function')
                    error.text().then((errorMessage) => {
                        alert(errorMessage);
                    });
                else
                    alert(error);
            });
        },

        napuni_pretragu: function({commit}, tekst){
            commit('set_pretraga', tekst);
        },

        dodaj_korisnika: function ({commit}, payload){
            fetch('http://localhost:8081/api/korisnici/1', {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: payload
            }).then((response) => {
                if (!response.ok)
                    throw response;
                return response.json();
            }).then((jsonData) => {
                commit('add_korisnik', jsonData);
            }).catch((error) => {
                console.log(payload);
                if (typeof error.text === 'function')
                    error.text().then((errorMessage) => {
                        alert(errorMessage);
                    });
                else
                    alert(error);
            });
        },

        izmeni_korisnika: function ({commit}, payload) {
            fetch('http://localhost:8081/api/korisnici/2', {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: payload
            }).then((response) => {
                if (!response.ok)
                    throw response;
                return response.json();
            }).then((jsonData) => {
                commit('update_korisnik', jsonData);
            }).catch((error) => {
                console.log(payload);
                if (typeof error.text === 'function')
                    error.text().then((errorMessage) => {
                        alert(errorMessage);
                    });
                else
                    alert(error);
            });
        },

        ucitaj_korisnika: function ({ commit }, email) {
            fetch(`http://localhost:8081/api/korisnici/${email}`, {method: 'get'}).then((response) => {
                if (!response.ok)
                    throw response;

                return response.json()
            }).then((jsonData) => {
                commit('set_korisnik', jsonData);
            }).catch((error) => {
                if (typeof error.text === 'function')
                    error.text().then((errorMessage) => {
                        alert(errorMessage);
                    });
                else
                    alert(error);
            });

        },

        skloni_korisnika: function({commit}){
            commit('odjavi');
        },

        set_korisnik: function({commit}, korisnik){
            commit('korisnik', korisnik);
        },

        remove_korisnik: function({commit}){
            commit('logout');
        }
    }

})
