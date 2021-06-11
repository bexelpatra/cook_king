<template>
  <q-page class="bg-white q-pa-sm">
    <section>
      <q-card flat>
        <q-card-section>
          <div class="row full-width">
            <q-input class="col" label="이메일" v-model="email" :disable="ec"/>
            <q-btn
              flat
              dense
              class="bg-grey-3"
              label="중복확인"
              @click="emailCheck(email)"/>
          </div>

          <div v-if="true" class="q-my-md">
            <q-btn
              dense
              size="lg"
              label="메일 발송"
              class="full-width signUp bg-blue text-white text-weight-bold"
              @click="sendCert" :disable="false"/>
          </div>
          <div v-if="true" class="row full-width">
            <q-input class="col" label="인증번호" v-model="cert">
              <div class="q-ma-md">{{clock}}</div>
            </q-input>
            <q-btn
              flat
              dense
              class="bg-grey-3"
              label="인증하기"
              @click="emailCert(cert)"/>
          </div>
          <div v-if="true" class="q-mt-md">
<!--            <q-input label="비밀번호" v-model="password"></q-input>-->
<!--            <q-input label="비밀번호 확인" v-model="password2"></q-input>-->
            <q-input v-model="password"
                     label="비밀번호"
                     filled :type="isPwd ? 'password' : 'text'">
              <template v-slot:append>
                <q-icon
                  :name="isPwd ? 'visibility_off' : 'visibility'"
                  class="cursor-pointer"
                  @click="isPwd = !isPwd"/>
              </template>
            </q-input>

            <q-input
              class="q-mt-sm"
              label="비밀번호 확인"
              v-model="password2"
              filled :type="isPwd2 ? 'password' : 'text'">
              <template v-slot:append>
                <q-icon
                  :name="isPwd2 ? 'visibility_off' : 'visibility'"
                  class="cursor-pointer"
                  @click="isPwd2 = !isPwd2"/>
              </template>
            </q-input>
          </div>
        </q-card-section>

        <q-separator  />

        <q-card-actions>
          <q-btn
            dense
            size="lg"
            class="full-width signUp bg-blue text-white text-weight-bold"
            label="가입 하기"
            @click="signUp()"
            :disable="false"
          />
<!--          <div>{{comment}}</div>-->
<!--          <div class="full-width text-center q-mt-sm">-->
<!--            <span>이미 계정이 있습니다. ->-->
<!--              <q-btn color="primary" label="로그인"/>-->
<!--            </span>-->
<!--          </div>-->
        </q-card-actions>
      </q-card>
    </section>

    <!-- fixme 이메일 이용약관   -->
    <section class="q-pa-sm">
      <div class="text-h6 q-mb-sm">이메일 이용약관</div>
      <q-card class="q-pa-sm" style="font-size: 1.1rem">
        <span>※ 모두의 레시피는 수집 목적으로 이메일을
          <span class="q-ml-sm">이용 하지 않고 단순 이메일 인증으로 </span>
          <span class="q-ml-sm">회원 가입 하게 되어 있습니다.</span>
        </span>
      </q-card>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapActions,mapMutations,mapState} from 'vuex';
  import {LocalStorage} from 'quasar';

  import {myUtil} from "boot/myUtil";

  export default {
    name: "SignUp",
    computed :{
      ...mapGetters(['getLayout']),
    },
    watch:{
      PW : ()=>{
        this.getAnswer({pw :this.password,pw2:this.password2});
      }
    },
    data(){
      return {
        util :new myUtil(this),
        email:'',
        checkDuplicate : false,
        checkEmail :false,
        checkPassword :false,

        comment : '',
        p1_duplicate : false,
        p2_sendMail : false,
        p3_cert : false,
        p4_pwLength : false,
        p5_samePw : false,
        sendAgain : false,
        password :'',
        password2 : '',
        inputModel : '',
        ec : false,
        cert :'',
        isPwd: true,
        isPwd2: true,

        ticktock :'',
        clock :'3:00',
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions(['duplicateCheck']),
      test(x){
        console.log(x.strSummary("123123",3))
      },
      // 기존에 저장된 메일이 있는지 확인
      emailCheck(email){
        let self = this;
        this.p1_duplicate = !this.p1_duplicate;
        // this.duplicateCheck({email:email,
        // onSuccess :(res) =>{
        //   if(res.data.result == 1){
        //     self.checkDuplicate = !self.checkDuplicate;
        //     self.ec = true;
        //     this.util.notify(res.data.desc,'info');
        //   }else {
        //     this.util.notify(res.data.desc,'info');
        //   }
        // },
        // onFail : (error )=>{
        //   this.util.notify(error,'warn');
        // }
        // })
      },
      // 인증메일 보내기
      sendCert(){
        this.sendAgain = false;
        this.myTimer();
      },
      // 이메일 인증하기
      emailCert(){
        // 타이머추가, 인증번호 입력받기
        this.checkEmail = !this.checkEmail;
      },
      // 회원가입하기
      signUp(){
        //1. 이메일인증 확인
        //2. 비밀번호 확인(유효성 및 복잡성)
      },
      getAnswer(args){
        _.debounce(()=>{
          console.log("@@@@@@")
          if(args.pw == args.pw2){
            this.checkPassword = true;
            this.comment ='같음'
          }else {
            this.checkPassword=false;
            this.comment ='다름'
          }
        }, 1000);
      },
      myTimer(){
        clearInterval(this.ticktock)
        let t = 179;
        let m = '';
        let sec = '';
        this.ticktock = setInterval(()=>{
          m = parseInt(t/60);
          sec = t%60 < 10 ? '0'+t%60: t%60;
          t--;
          this.clock = m+":"+sec;

          if(t<55){
            this.sendAgain = true;
          }
          if(t<0){
            this.clock = '시간 만료'
            clearInterval(this.ticktock)
          }
        },1000)
      },
    },

    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.backbotton = true;
      this.getLayout.title = "회원가입"
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
