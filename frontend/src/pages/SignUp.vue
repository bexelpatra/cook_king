<template>
  <q-page class="bg-white q-pa-sm">
    <section>
      <q-card flat>
        <q-card-section>

          <div class="row full-width">
            <q-input class="col" label="이메일" v-model="emailTemp" :disable="emailCertResult" />
          </div>

          <div v-if="true" class="q-my-md bg-grey-3" >
            <q-btn
              dense
              size="lg"
              label="메일 발송"
              class="full-width"
              @click="sendCert" :disable="!sendEmail"/>
          </div>

          <div v-if="sendCertResult" class="row full-width">
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

          <div v-if="emailCertResult" class="q-mt-md">
            <q-input v-model="password"
                     label="비밀번호"
                     filled
                     :type="isPwd ? 'password' : 'text'">
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
              filled
              :disable="this.password.length < 8"
              :hint="passwordComment"
              :type="isPwd2 ? 'password' : 'text'">
              <template v-slot:append>
                <q-icon
                  :name="isPwd2 ? 'visibility_off' : 'visibility'"
                  class="cursor-pointer"
                  @click="isPwd2 = !isPwd2"/>
              </template>
            </q-input>

          </div>
        </q-card-section>

        <q-separator/>

        <q-card-actions>
          <q-btn
            dense
            size="lg"
            class="full-width signUp bg-blue text-white text-weight-bold"
            label="가입 하기"
            @click="signUp()"
            :disable="false"
          />
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
  import {debounce} from 'lodash'
  import {myUtil} from "boot/myUtil";

  export default {
    name: "SignUp",
    computed :{
      ...mapGetters(['getLayout']),
    },
    watch:{
      password2 : function(){
        this.passwordCheck();
      },

      emailCertResult : function(flag){
        if(flag){
          this.finalEmail = this.emailTemp;
          this.sendCertResult = false;
          this.emailCheckResult = false;
          this.mailCheck = true;
        }

      }
    },
    data(){
      return {
        util :new myUtil(this),
        emailTemp:'',
        finalEmail : '',
        password :'',
        passwordLength : 0,
        password2 : '',
        inputModel : '',
        emailCheckResult : false,
        cert :'',
        mailCheck : false,
        sendEmail : true,
        isPwd: true,
        isPwd2: true,
        isSamePw:false,
        sendCertResult : false,
        emailCertResult : false,
        fixEmail : false,
        ticktock :'',
        clock :'1:00',

        passwordComment : ' ',
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions(['duplicateCheck','fetchServer']),
      test(x){
      },
      passwordCheck:_.debounce(function(){
        if(this.password === this.password2){
          console.log("동일합니다.")
          this.passwordComment = "동일한 비밀번호입니다.";
        }else{
          this.passwordComment = "비밀번호가 다릅니다.";
          console.log("비밀번호가 다릅니다.")
        }
      }, 1000),

      // deprecated 기존에 저장된 메일이 있는지 확인
      emailCheck(emailTemp) {
        let self = this;

        this.fetchServer({path: 'user/mail-duplication', param: {email: emailTemp}})
          .then(success => {
            let color = '';
            if (success.result == 1) {
              color = 'info';
              this.emailCheckResult = true;
            } else {
              color = 'warning'
              this.emailCheckResult = false;
            }
            this.util.notify(success.desc, color);
          })
          .catch(fail => {
            console.log(fail)
          })
      },
      // 인증메일 보내기
      // 인증 메일을 보내면서 타이머를 실행
      async sendCert(){
        let desc = '';
        let color = '';
        this.sendEmail = false;
        let self = this;
        setTimeout(function () {
          self.sendEmail = true;
        },3000)
        await this.fetchServer({path : 'user/mail-certification'
          ,method:'post'
          ,param :{email : this.emailTemp}
          ,body:{}})
          .then(success =>{
            desc = success.desc;
            color = 'info'
            if(success.status == 200){
              this.sendCertResult = true;
              this.myTimer();
            }
          })
          .catch(fail =>{
            console.log(fail)
            desc = '알수 없는 에러'
            color = 'warning'
          })
        this.util.notify(desc,color);
      },
      // 이메일 인증하기
      async emailCert(){
        let desc = '';
        let color = '';
        // 타이머추가, 인증번호 입력받기
        await this.fetchServer({path : 'user/mail-certification'
          ,method:'get'
          ,param :{email : this.emailTemp,number : this.cert}
          ,body:{}})
          .then(success =>{
            console.log(success)
            desc = success.desc;
            color = success.status == 200 ? 'info' : 'warning';
            if(success.status==200){
              this.emailCertResult = true;
              this.finalEmail = this.emailTemp;
            }
          })
          .catch(fail =>{
            console.log(fail)
            desc = '알수 없는 에러'
            color = 'warning'
          })
        this.util.notify(desc,color);
      },
      // 회원가입하기
      signUp(){
        //1. 이메일인증 확인
        //2. 비밀번호 확인(유효성 및 복잡성)
        if(this.password != this.password2) {
          return this.util.notify("비밀번호가 다릅니다.","warning");
        }
        // _.throttle(function(){
        //   console.log("dddddd")
          this.fetchServer({path : 'user/users',method : 'post',
            body : {
              email : this.finalEmail,
              password : this.password
            },
            headers :{
              'Content-Type':'application/json'
            }
          })
            .then(value => {
              // 회원가입 성공하면 어디로 가야 하는가?
              if(value.status == 200){
                this.util.goTo('main')
                LocalStorage.set("e",value.user.email);
                LocalStorage.set("t",value.user.token);
                this.util.notify(value.desc,'info');
              }else if(value.status==202){
                this.util.notify(value.desc,'warning');
              }
            })
            .catch(reason => {
              console.log(reason)
              this.util.notify(reason.desc,'warning')
            })

        //} ,3000)

      },
      myTimer(){
        this.sendEmail = false;
        clearInterval(this.ticktock)
        let t = 59;
        let m = '';
        let sec = '';
        this.ticktock = setInterval(()=>{
          m = parseInt(t/60);
          sec = t%60 < 10 ? '0'+t%60: t%60;
          t--;
          this.clock = m+":"+sec;

          if(t<0){
            this.sendEmail = true;
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
      this.getLayout.mainbackbotton = false;
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
