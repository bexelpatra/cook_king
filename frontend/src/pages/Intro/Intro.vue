<template>
  <q-layout>
    <section>
      <div class="absolute full-height">
        <img src="video/IntroGif.gif" class="full-width full-height"/>
        <q-icon class="q-pb-xl row absolute-center text-white" style="font-size: 8rem; "
                name="thumb_up">
        <span class="text-weight-bold text-h5">
          <span class="text-purple">모두의 </span>
          <span class="text-weight-regular text-white">레시피</span>
        </span>
        </q-icon>
      </div>
    </section>
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
      return {
        appVersion : LocalStorage.getItem("US_VS")
      }
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
      }, 3800);
    },
    beforeMount() {},
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
