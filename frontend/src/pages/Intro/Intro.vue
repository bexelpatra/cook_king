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
      ...mapMutations(['setLogIn','setFavorite']),
      ...mapActions(['fetchServer']),
      async gotoMain() {
        let self = this;
        let message = '';
        await this.fetchServer({path : 'user/user',param:{t:this.t, type : 0}})
          .then(user => {
            if (user.status == 200){
              LocalStorage.set("t",user.token);
              LocalStorage.set("e",user.email);
              this.setFavorite(user.favorite);
              this.setLogIn(true);
              message = '로그인 성공'
              console.log('로그인성공');
            } else{this.catch()};
          })
          .catch(reason => {
            console.log('비회원');
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

      let list = [1,2,3,4,5,6,7,8];
      let flatList = [[1,2,],[3,4],[5,6]]

      // list
      //   .filter(num => {
      //     return num%2==0
      //   })
      //   .forEach(num=>{console.log(num)})

      // list
      // .map(num => {
      //   return num*=10
      // })
      // .forEach(num =>consol.log(num))
      // for (let number of list) {
      //   if(number %2==0){
      //     console.log(number);
      //   }
      // }

      // flatList.map(value => value).forEach(value => console.log(value));
      // flatList.flatMap(value => value).forEach(value => console.log(value));
    },
    beforeMount() {},
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
