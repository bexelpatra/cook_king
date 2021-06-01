<!--fixme 핀/생체정보 설정-->
<template>
  <q-page class="bg-white">
    <!--fixme security 보안-->
    <section class="q-pa-md">
      <div class="flex q-my-md">
        <div class="q-mt-sm text-h5 text-weight-bold" >보안 사용 안함</div>
        <q-space/>
        <q-checkbox class="justify-end" color="orange" @input="(value, evt)=>{useCertificate(value,0)}" v-model="security"  val="noSecurity"/>
      </div>

      <q-separator class="q-my-lg"/>

      <!-- 1차보안 -->
      <!-- fixme #804
      1차 보안은 Pin등록을 해야된다.
      체크가 안되어 있으면 위 보안 사용 안함 박스에 체크 해줘야 된다.
      -->
      <div>
        <div class="text-weight-bold text-h6 text-grey-6">1차보안</div>
        <div class="flex q-my-md" >
          <span class="q-mt-sm text-weight-bold " style="font-size: 1.0rem">비밀번호</span>
          <q-space/>
          <q-checkbox class=" justify-end" color="orange" :disable="haspDisable" @input="(value, evt)=>{useCertificate(value,1)}" v-model="hasp" val="passWord"/>
        </div>
        <q-btn class="full-width text-weight-bold" label="비밀번호 변경" :disable="haspChange" outline @click="goToRegistration"></q-btn>
      </div>

      <q-separator class="q-my-lg"/>

      <!-- fixme #805

      -->
      <div class="text-weight-bold text-h6 text-grey-6">2차보안
        <div class="notiText text-caption text-weight-thin text-blue-7">내 폰의 지문/안면인식 설정에 따라 지원됩니다.</div>
      </div>
      <!--      2차보안 핑거-->
      <div class="flex no-wrap q-my-md" >
        <div class="q-mt-sm text-weight-bold " style="font-size: 1.0rem">
          생체 인증

        </div>
        <q-space/>
        <q-toggle class=" justify-end" :disable="fingerDisable" @input="(e)=>{useCertificate(e,2)}" v-model="finger" color="orange"/>
      </div>
    </section>

    <q-separator/>

    <!-- fixme step3 -->
    <section>
      <div class="q-pa-md">
        <span class="notiText text-weight-bold text-red-4"  style="font-size: 0.8rem">
          ※ 1차 보안을 설정 해야 2차 보안 설정이 가능합니다.
          ※ 생체 인증 등록/변경은 휴대폰 설정에서 가능합니다.
        </span>
      </div>
    </section>
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

    },

    beforeCreate() {},
    created() {},
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.title = "핀/생체정보";
      this.getLayout.backbotton = false;
      this.getLayout.bookmarkbtn = false;
      this.getLayout.bottomFooter = false;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
