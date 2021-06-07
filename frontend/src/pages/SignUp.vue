<template>
  <q-page class="bg-white q-pa-sm">
    <section>
      <q-card class="text-black" flat>
        <q-card-section>
          <div class="full-width text-h6" style="text-align: center">우주대레시피입니다</div>
        </q-card-section>

        <q-card-section>
          <div class="flex full-width">
            <q-input label="이메일" v-model="email" style ="width: 70%" :disable="ec"></q-input>
             <q-btn
               flat
               style=" width: 30%;font-size: 0.9em;"
               @click="emailCheck(email)">중복 확인</q-btn>
          </div>
          <div v-if="p1_duplicate" class="full-width">
            <q-btn
              flat
              style=" width: 30%;font-size: 0.9em;"
              @click="sendCert" :disable="sendAgain">메일 발송</q-btn>
          </div>
          <div v-if="p2_sendMail" class="flex full-width">
            <q-input label="인증번호" v-model="cert" style ="width: 70%"></q-input>
            <q-btn
              flat
              style=" width: 30%;font-size: 0.9em;"
              @click="emailCert(cert)">인증하기</q-btn>
          </div>
          <div v-if="p1_duplicate&&p2_sendMail">
            <q-input label="비밀번호" v-model="password"></q-input>
            <q-input label="비밀번호 확인" v-model="password2"></q-input>
          </div>
        </q-card-section>

        <q-separator dark />

        <q-card-actions>
          <q-btn
            class="full-width signUp"
            style="height: 2.3em;"
            flat
            label="계정 만들기"
            @click="signUp()"
            :disable="(!checkDuplicate||!checkEmail)"
          />
          <div style="text-align: center" class="full-width q-mt-sm">
            <span>이미 계정이 있습니다. ->
              <q-btn color="primary" label="로그인"/>
            </span>
          </div>
        </q-card-actions>
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
          this.getAnswer(this.password,this.password2);
        }
      },
      data(){
        return {
          util :new myUtil(this),
          email:'',
          checkDuplicate : false,
          checkEmail :false,
          checkPassword :false,

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

          ticktock :'',
          clock :'',
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
          this.duplicateCheck({email:email,
          onSuccess :(res) =>{
            if(res.data.result == 1){
              self.checkDuplicate = !self.checkDuplicate;
              self.ec = true;
              this.util.notify(res.data.desc,'info');
            }else {
              this.util.notify(res.data.desc,'info');
            }

          },
          onFail : (error )=>{
            this.util.notify(error,'warn');
          }
          })
        },
        // 인증메일 보내기
        sendCert(){
          this.sendAgain = false;
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
        getAnswer(){
          _.debounce((pw,pw2)=>{
            if(pw===pw2){
              this.checkPassword = true;
            }else {
              this.checkPassword=false;
            }
          }, 1000);
        },
        myTimer(){
          clearInterval(this.ticktock)
          let t = 60;
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
        this.getLayout.bottomFooter = true;
        // this.getLayout.headerLayout = true;
        // this.getLayout.title = "회원가입";
      },
      mounted() {},
      beforeUpdate() {},
      updated() {},
      beforeDestroy() {},
      destroyed() {}
    }
</script>
