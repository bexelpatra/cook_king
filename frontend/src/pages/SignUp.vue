<template>
  <q-page class="bg-white q-pa-sm">
    <section>
      <q-card class="text-black" flat>
        <q-card-section>
          <div class="full-width text-h6" style="text-align: center">우주대레시피입니다</div>
        </q-card-section>

        <q-card-section>
          <div class="flex full-width">
            <q-input label="이메일" v-model="email" style ="width: 70%"></q-input>
             <q-btn
               flat
               style=" width: 30%;font-size: 0.9em;"
               @click="duplicateCheck(email)">중복 확인</q-btn>
          </div>
          <div v-if="checkDuplicate" class="flex full-width">
            <q-input label="인증번호" v-model="email" style ="width: 70%"></q-input>
            <q-btn
              flat
              style=" width: 30%;font-size: 0.9em;"
              @click="emailCert(email)">인증하기</q-btn>
          </div>
          <div v-if="checkEmail&&checkDuplicate">
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
        ...mapGetters(['getLayout'])
      },
      watch:{
        // PW : (pw,pw2)=>{
        //   this.getAnswer();
        // }
      },
      data(){
        return {
          util :new myUtil(this),
          email:'',
          checkDuplicate : false,
          checkEmail :false,
          checkPassword :false,
          password :'',
          password2 : '',

        }
      },
      methods:{
        ...mapMutations([]),
        ...mapActions([]),
        test(x){
          console.log(x.strSummary("123123",3))
        },
        // 기존에 저장된 메일이 있는지 확인
        duplicateCheck(email){
          this.checkDuplicate = !this.checkDuplicate;
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
          }, 500);
        }
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
