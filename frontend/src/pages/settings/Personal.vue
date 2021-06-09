<!--fixme 핀/생체정보 설정-->
<template>
  <q-page class="bg-white q-pa-md">
    <!-- 핀/생체 설정 -->
    <section>
      <div class="flex q-my-md">
        <div class="q-mt-sm text-h6 text-weight-bold" >핀/생체 사용 안함</div>
        <q-space/>
        <!-- input 설정이 없어서 오류 발생-->
        <q-checkbox class="justify-end" color="red" @input="(value, evt)=>{useCertificate(value,0)}" v-model="security"  val="noSecurity"/>
      </div>

      <q-separator class="q-my-md"/>

      <!-- Pin -->
      <section>
        <div class="flex">
          <span class="flex flex-center text-h6 text-weight-bold">핀 등록하기</span>
          <q-space/>
          <q-btn class="q-mr-lg text-weight-bold" label="핀 변경" :disable="haspChange" outline @click="PinChange"></q-btn>
          <!-- input 설정이 없어서 오류 발생-->
          <q-checkbox class="justify-end" color="red" :disable="haspDisable" @input="(value, evt)=>{useCertificate(value,1)}" v-model="hasp" val="passWord"/>
        </div>
      </section>

      <q-separator class="q-my-md"/>

      <!-- fingerprint -->
      <section>
        <div class="flex">
          <div class="flex flex-center text-h6 text-weight-bold">
            생체 등록
          </div>
          <q-space/>
          <!-- input 설정이 없어서 오류 발생-->
          <q-toggle class=" justify-end" color="red" :disable="fingerDisable" @input="(e)=>{useCertificate(e,2)}" v-model="finger" />
        </div>
      </section>
    </section>

    <q-separator  class="q-my-md"/>

    <!-- 안내 문구 -->
    <section>
      <div class="q-pa-sm">
        <span class="text-weight-bold text-red-4"  style="font-size: 0.9rem">
          ● 핀 등록해야 생체 등록 가능합니다.<q-space/>
          ● 생체는 지원 안되는 핸드폰이 있습니다.<q-space/>
          <br>
          <span class="text-blue-5" style="font-size: 0.8rem">
            <span class="text-grey-7">#핸드폰 생체 등록/확인 하기</span> <q-space/>
            핸드폰 설정 -> 생체 인식 및 보안 -> 얼굴/지문 터치 <q-space/>
          </span>
          <br>
          ● 비밀번호가 기억나지 나지 않으면<q-space/>
          -> <span class="text-black">cookkingofficial@gmail.com</span> 으로 문의주세요.
        </span>
      </div>
    </section>

    <q-separator class="q-my-md"/>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';

  export default {
    name: 'Personer',
    computed:{
      ...mapGetters(['getLayout'])
    },
    data(){
      return{
        appVersion: LocalStorage.getItem("US_VS"),
        security: false,// 보안사용 체크박스
        finger: true, // 생채 인증
        fingerDisable: false,
        hasp : true, // 비밀번호
        haspDisable: false,
        haspChange: false,
        nextpage: "my",
        havePw: true,
        havePal: false,
        certOrder : '',
        p: '',
        checked : true,
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions([]),

      /** Click 구간 */
      PinChange(){
        this.$router.push('pinregistration');
      },
    },

    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.title = "핀/생체";
      this.getLayout.backbotton = true;
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
