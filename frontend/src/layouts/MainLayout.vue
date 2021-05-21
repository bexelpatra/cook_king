<template>
  <q-layout view="lHh Lpr lFf">
    <q-header>
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          v-if="menuList"
          @click="leftDrawerOpen = !leftDrawerOpen"
          style="border: 1px solid red"
        />

        <q-toolbar-title class="absolute-center" style="border: 10px solid red">
          {{title}}
        </q-toolbar-title>
        <q-space style="border: 1px solid red"/>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          v-if="menuList"
          @click="leftDrawerOpen = !leftDrawerOpen"
        />

      </q-toolbar>
    </q-header>

    <q-drawer
      v-model="leftDrawerOpen"
      show-if-above
      bordered
      content-class="bg-grey-1"
    >
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import {mapActions,mapGetters,mapMutations,mapState} from 'vuex';
import {LocalStorage} from 'quasar';

export default {
  name: 'MainLayout',
  computed :{
    ...mapGetters([]),
  },
  data () {
    return {
      leftDrawerOpen: false,
      title:'쿠킹',
      menuList : true,
    }
  },
  methods :{
    ...mapMutations([
      'setLayout',
    ]),
    ...mapActions([]),
  },
  beforeCreate() {

  },
  created() {
  },
  beforeMount() {
  },
  mounted() {
  },
  beforeUpdate() {
    this.setLayout(this);
  },
  updated() {
  },
  beforeDestroy() {
  },
  destroyed() {
  },
}

</script>
