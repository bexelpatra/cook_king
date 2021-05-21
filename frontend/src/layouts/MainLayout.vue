<template>
  <q-layout>
    <q-header v-if="headerLayout">
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
          @click="leftDrawerOpen = !leftDrawerOpen"
        />

        <q-toolbar-title class="text-h5 text-weight-bold absolute-center">
          {{title}}
        </q-toolbar-title>

        <q-space/>
        <q-btn
          flat
          dense
          round
          icon="menu"
          aria-label="Menu"
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
  import {mapActions,mapMutations,mapGetters} from 'vuex';
  import {LocalStorage} from 'quasar';

  export default {
    name: 'MainLayout',
    computed:{
      ...mapGetters([]),
    },
    data () {
      return {
        leftDrawerOpen: false,
        title: "쿠킹",
        headerLayout: true,
      }
    },
    methods:{
      ...mapMutations([
        'setLayout'
      ]),
      ...mapActions([]),
    },
    beforeCreate() {},
    created() {},
    beforeMount() {
      this.setLayout(this);
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
