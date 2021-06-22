<!--fixme 로그인-->
<template>
  <q-page class="q-pa-md">
    <section class="full-width">
      <!--fixme 타이틀 및 아이콘 -->
      <section class="flex flex-center q-mt-lg">
        <q-icon class="q-pb-xl row text-grey-4" style="font-size: 6rem; "
                name="thumb_up">
        <span class="text-weight-bold text-h5">
          <span class="text-purple">모두의 </span>
          <span class="text-weight-regular text-black">레시피</span>
        </span>
        </q-icon>
      </section>

      <!--fixme 아이디, 패스워드 로그인 -->
      <section>
        <div>
          <q-input outlined class="q-mt-sm" v-model="email" :dense="emaildense"  label="이메일"/>
          <q-input outlined class="q-mt-xs" v-model="password" type="password" label="비밀번호"/>
        </div>
        <q-btn
          dense
          class="q-mt-sm full-width bg-blue text-white text-weight-bolder"
          style="font-size: 1.2rem"
          label="로그인"
          @click="loginBtn"
        />
      </section>

      <!--fixme 회원 가입 및 비밀번호 찾기 -->
      <section class="q-mt-sm row">
        <q-btn flat dense class="col" label="회원가입" @click="SignupPage"/>
        <q-btn flat dense class="col" label="비밀번호 찾기" @click="FindPW"/>
      </section>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';
  import {myUtil} from "boot/myUtil";

  export default {
    name: 'Login',
    computed:{
      ...mapGetters(['getLayout','isLogIn'])
    },
    data(){
      return{
        email : '',
        password : '',

        emaildense: false,
        pwdense: false,

        to: '',
        from : '',
        query : '',
        util : new myUtil(this),
      }
    },
    methods:{
      ...mapMutations(['setLogIn','setFavorite']),
      ...mapActions(['fetchServer','userInfo']),

      /**======================================
       * 클릭 이벤트
       ========================================*/
      SignupPage(){this.$router.push('signup');},

      FindPW(){this.$router.push('findpw');},

      loginBtn(to,from){
        if (this.email == null || this.email == '' || this.password == null || this.password == ''){
          this.$q.notify({
            message : '이메일/비밀번호 입력해주세요.',
            type : 'negative'
          })
          return;
        }
        this.fetchServer({path : 'user/user',param:{e:this.email,p:this.password,type : 1}})
        .then(success => {
          if(success.status==200){
            console.log(success);
            LocalStorage.set("t",success.user.token);
            LocalStorage.set("e",success.user.email);
            this.userInfo({token : LocalStorage.getItem('t')})
            let self = this;
            let timer = setTimeout(function () {
              self.util.goTo('main')
            },700)
            this.util.notify('로그인 성공','info')
          }else{
            this.util.notify("로그인에 실패했습니다",'warning')
          }
        })
        .catch(reason => {console.log(reason)})
        // 로그인 서버 연동
        // this.$router.push({path : '/addcontent', query : {to: to, from : from}});

        },


    },

    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
      this.query = this.$router.history.current.query;
    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.backbotton = true;
      this.getLayout.title = "로그인"
      this.getLayout.bookmarkbtn = false;
      this.getLayout.bottomFooter = false;
      this.getLayout.addcontent = false;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
