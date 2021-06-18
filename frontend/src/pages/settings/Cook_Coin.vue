<!--fixme 내 코인-->
<template>
  <q-page class="bg-white">
    <!--fixme 내 코인 정보 -->
    <section class="q-pa-sm bg-blue-2" style="height: 30vw">
      <div class="q-py-sm flex">
        <span class="q-ml-md absolute text-weight-bold" style="left: 0; font-size: 1rem">내 코인</span>
        <span class="q-mr-md absolute text-h6 text-weight-regular text-blue" style="right: 0;">0 Coin</span>
      </div>

      <div class="q-mt-xl flex">
        <span class="q-ml-md absolute" style="left: 0; font-size: 0.8rem">선물코인</span>
        <span class="q-mr-md absolute" style="right: 0; font-size: 0.8rem">0 Coin</span>
      </div>
    </section>

    <!--fixme 선물하기 버튼 -->
    <section class="full-width row bg-blue-2" style="border-top: solid 1px grey">
      <q-btn
        flat
        dense
        class="col"
        color="blue"
        size="1rem"
        icon="add"
        label="선물하기"
      />
    </section>

    <!--fixme 선물 이력 -->
    <section class="q-pa-sm" style="border-bottom: solid 1px grey">
      <div class="row">
        <span class="q-mt-sm col text-weight-bold" style="font-size: 1rem">Coin 선물 내역</span>
        <q-select
          filled
          dense
          class="col"
          v-model="single"
          :options="options"
          label="선택"
        />
      </div>
    </section>

    <!--fixme 내역 -->
    <section>
      <div class="absolute-center">
        <span>이하 내역이없습니다.</span>
      </div>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';

  export default {
    name: 'Cook_Coin',
    computed:{
      ...mapGetters(['getLayout'])
    },
    data(){
      return{
        single: null,
        options: ['전체선물','받은선물','보낸선물']
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
      this.getLayout.title = "내 코인";
      this.getLayout.backbotton = true;
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
