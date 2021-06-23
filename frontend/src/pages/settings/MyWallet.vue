<!--fixme 내 지갑-->
<template>
  <q-page class="bg-white">
    <!--fixme 선물 안내문  -->
    <section class="q-pa-sm bg-grey-4" style="font-size: 1rem">
        회원님에 <span class="text-weight-bold">포인트</span>를 아래 QR 코드로 선물 할수 있습니다.
    </section>

    <!--fixme QR  -->
    <section class="q-pa-md text-weight-bold" style="font-size: 1rem">
      내 지갑 QR
    </section>
    <q-separator class="" style="height: 2px"/>

    <!--fixme QR 생성 -->
    <section class="q-pa-lg flex flex-center">
      <q-btn flat class="bg-grey-4" style="width: 33vw; height: 33vw;">
        <q-icon name="add" color="blue" size="12vw"/>
      </q-btn>
    </section>

    <!--fixme 문구  -->
    <section class="text-center text-grey-8">
      버튼을 누르면 <span class="text-weight-bold">QR</span>이 생성됩니다.
    </section>
    <q-separator class="q-mt-lg" style="height: 2px"/>

    <!--fixme 유의사항  -->
    <section class="q-pa-sm text-weight-bold" style="font-size: 0.9rem">
      선물받기 전 꼭 확인 하세요!
      <div class="q-gutter-y-xs q-mt-sm text-grey-7" style="font-size: 0.75rem">
        ⊙ 현금화를 사용 하지 못합니다.<q-space/>
        ⊙ 광고,상업적인 목적으로 사용 되지 않습니다.<q-space/>
        ⊙ 위 QR코드는 한번 생성하면 재발급이 안됩니다.<q-space/>
        ⊙ 타인에게 노출되지 않게 유의 하세요.<q-space/>
        ⊙ 문제발생시 email : <span class="text-black text-weight-bold">coookkingofficial@gmail.com.</span><q-space/>
      </div>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';

  export default {
    name: 'MyWalet',
    computed:{
      ...mapGetters(['getLayout'])
    },
    data(){
      return{
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions([]),

      /**======================================
       * 클릭 이벤트
       ========================================*/

    },

    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.title = "내 지갑";
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
