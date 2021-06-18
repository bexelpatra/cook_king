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
  import {myUtil} from "boot/myUtil";

  export default {
    name: "Intro",
    computed: {
      ...mapGetters(['isLogIn']),
    },
    data() {
      return {
        util : new myUtil(this),
        t : LocalStorage.getItem("t"),
        appVersion : LocalStorage.getItem("US_VS")
      }
    },

    methods: {
      ...mapMutations(['setLogIn']),
      ...mapActions(['fetchServer']),
      async gotoMain() {
        let self = this;
        let message = '';
        await this.fetchServer({path : 'user/user',param:{t:this.t, type : 0}})
          .then(value => {
            LocalStorage.set("t",value.token);
            LocalStorage.set("e",value.email);
            this.setLogIn(true);
            message = '로그인 성공'
          })
          .catch(reason => {
            message = '비회원'
          })
        this.util.goTo('main')
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
