<template>
  <q-layout>
    <div class="absolute full-height">
      <span class="q-mt-lg absolute-top text-center text-white text-h5 z-top">우주 대레시피</span>
      <img src="imgs/uro.png" class="full-width full-height"/>
      <q-icon class="q-ml-sm z-top absolute-center text-green-3" style="font-size: 8rem; margin-top: 30vh"
              name="thumb_up"/>
      <span class="q-mb-lg absolute-bottom text-center text-white text-h5 z-top">우주 대레시피</span>
    </div>
  </q-layout>
</template>

<script>
  import {LocalStorage, Platform} from 'quasar';
  import {mapGetters, mapMutations, mapActions} from 'vuex'

  export default {
    name: "Intro",
    computed: {
      ...mapGetters([]),
    },
    data() {
      return {}
    },

    methods: {
      ...mapMutations([]),
      ...mapActions([]),
      gotoMain() {
        this.$router.push('/Main');
      },
    },

    beforeCreate() {},
    created() {
      setTimeout(() => {
        this.gotoMain();
      }, 3000);
    },
    beforeMount() {},
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
