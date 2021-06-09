<!--fixme 로그인-->
<template>
  <q-page class="q-pa-md">
    <section class="full-width">
      <div class="flex flex-center q-mt-xl">
        <q-icon class="q-pb-xl row text-grey-4" style="font-size: 6rem; "
                name="thumb_up">
        <span class="text-weight-bold text-h5">
          <span class="text-purple">모두의 </span>
          <span class="text-weight-regular text-black">레시피</span>
        </span>
        </q-icon>
      </div>

      <div>
        <q-input outlined class="q-mt-sm" v-model="email" :dense="emaildense"  label="이메일"/>
        <q-input outlined class="q-mt-xs" v-model="password" type="password" label="비밀번호"/>
      </div>
      <q-btn
        dense
        class="q-mt-sm full-width bg-blue text-white text-weight-bolder"
        style="font-size: 1.2rem"
        label="로그인"
        @click="mainPage"
      />
      <div class="q-mt-sm row">
        <q-btn flat dense class="col" label="회원가입" @click="SignupPage"/>
        <q-btn flat dense class="col" label="비밀번호 찾기" @click="FindPW"/>
      </div>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';

  export default {
    name: 'Login',
    computed:{
      ...mapGetters(['getLayout'])
    },
    data(){
      return{
        email : '',
        password : '',

        emaildense: false,
        pwdense: false,
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions([]),

      /**==================================
       * 디바이스
       ====================================*/

      //fixme 디바이스 빽버튼 사용
      Device() {
        let self = this;
        document.addEventListener("backbutton", this.exitBtn, false);
      },

      //fixme 빽버튼 메소드
      exitBtn() {
        if (window.cordova && window.cordova.platformId !== 'android') {
          return;
        }
        let linkSrc = window.location.href.split("#")[1];
        if (linkSrc === "/login") {
          this.$q.dialog(
            {
              title: '<div class="text-h5 text-weight-bolder"><span class="text-orange-6">모두의</span> 레시피 종료</div>',
              message: '<div class="q-mt-lg  text-subtitle1 text-weight-thin ">모두의 레시피을 종료 하시겠습니까?</div>',
              html: true,
              ok: {
                flat: true,
                label: '네',
                textColor: 'primary',
                size: 'lg'
              },
              cancel: {
                flat: true,
                label: '아니오',
                textColor: 'negative',
                size: 'lg'
              },
            }
          )
            .onOk(() => {
              this.exitApp();
            })
            .onCancel(() => {
              console.log('Cancel');
            });
        }
      },

      //fixme 앱종료
      exitApp() {
        console.log("exitApp", navigator);
        navigator.app.exitApp();
      },

      /**======================================
       * 클릭 이벤트
       ========================================*/
      SignupPage(){this.$router.push('signup');},
      FindPW(){this.$router.push('findpw');},
      mainPage(){this.$router.push('main');},


    },

    beforeCreate() {},
    created() {
      history.pushState(null, null, location.href);
      window.onpopstate = ()=>{
        this.exitApp();
      }
    },
    beforeMount() {
      this.getLayout.headerLayout = false;
      this.getLayout.backbotton = false;
      this.getLayout.title = "로그인"
      this.getLayout.bookmarkbtn = false;
      this.getLayout.bottomFooter = false;
      this.getLayout.addcontent = false;
      document.addEventListener("deviceready", this.Device, false);
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {
      document.removeEventListener("backbutton", this.exitBtn);
      document.removeEventListener("deviceready", this.Device);
    },
    destroyed() {}
  }
</script>
