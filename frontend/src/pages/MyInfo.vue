<!--fixme 내 정보-->
<template>
  <q-page class="bg-white">
    <!--fixme Mycontent 내 게시물-->
    <section>
      <q-btn
        class="full-width items-start flex text-h6 text-grey-7"
        style="height: 17vw"
        icon="folder"
        flat
        label="내 게시물"
        @click="myConTentPage"
      />
      <q-separator/>
    </section>

    <!--fixme security 핀/생체-->
    <section>
      <q-btn
        class="full-width items-start flex text-h6 text-grey-7"
        style="height: 17vw"
        icon="admin_panel_settings"
        flat
        label="핀/생체"
        @click="securitybtn"
      />
      <q-separator/>
    </section>

    <!--fixme setting 설정-->
    <section>
        <q-btn
          class="full-width items-start flex text-h6 text-grey-7"
          style="height: 17vw"
          icon="savings"
          flat
          label="쿡_코인"
          @click="cookCoin"
        />
      <q-separator/>
    </section>

    <!--fixme version 버전-->
    <section>
      <div class="row">
        <q-icon class="q-ma-md items-start text-h4 text-grey-7 "
                name="phonelink_setup"/>
        <div class="q-my-md text-grey-6 text-h6">AppVersion V. 1.0.4</div>
<!--        <div class="q-my-md text-grey-6 text-h6">AppVersion V.{{ appVersion }}</div>-->
      </div>
      <q-separator/>
    </section>

  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';
  import {myUtil} from "boot/myUtil";

  export default {
    name: 'MyInfo',
    computed:{
      ...mapGetters(['getLayout'])
    },
    data(){
      return{
        appVersion: LocalStorage.getItem("US_VS"),
        util :new myUtil(this),
      }
    },
    methods:{
      ...mapMutations(['setFavorite','setLogIn']),
      ...mapActions(['fetchServer']),

      /**======================================
       * 클릭 이벤트
       ========================================*/
      securitybtn(){
        // this.$router.push('personer');
        this.$q.notify({
          message : '기능 추가 예정 입니다.',
          type : 'info'
        })
      },
      myConTentPage(){
        this.$router.push('mycontent');
      },
      cookCoin(){
        // this.$q.notify({
        //   message : '기능 추가 예정 입니다.',
        //   type : 'info'
        // })
        this.util.goTo('cookcoin');
      }
    },

    beforeCreate() {},
    created() {
      let self = this;
      window.onpopstate = ()=>{}
      console.log('myInfo 입장')
      this.fetchServer({path : 'user/user',param:{t:LocalStorage.getItem('t'), type : 0}})
        .then(success => {
          // 로그인 성공시
          if(success.status==200){
            this.setFavorite(success.user.favorite);
            this.setLogIn(true);
          }else{
            let timer = setTimeout(function () {
              self.util.goTo('login')
            },700);
            self.util.notify('로그인 정보가 없습니다.','warning');
          }
        })
        .catch(reason => {
          let timer = setTimeout(function () {
            this.util.goTo('login')
          },700);
          this.util.notify('로그인 정보가 없습니다.','warning');
        })
    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.title = "내정보";
      this.getLayout.backbotton = false;
      this.getLayout.mainbackbotton = false;
      this.getLayout.bookmarkbtn = false;
      this.getLayout.bottomFooter = true;
      this.getLayout.addcontent = false;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
