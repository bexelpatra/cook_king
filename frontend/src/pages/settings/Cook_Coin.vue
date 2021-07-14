<!--fixme 내 코인-->
<template>
  <q-page class="bg-white">
    <!--fixme 내 코인 정보 -->
    <section class="q-pa-sm bg-blue-2" style="height: 30vw">
      <div class="q-py-sm flex">
        <span class="q-ml-md absolute text-weight-bold" style="left: 0; font-size: 1rem">내 코인</span>
        <span class="q-mr-md absolute text-h6 text-weight-regular text-blue" style="right: 0;">{{myPoint}} Coin</span>
      </div>

<!--      <div class="q-mt-xl flex">-->
<!--        <span class="q-ml-md absolute" style="left: 0; font-size: 0.8rem">선물코인</span>-->
<!--        <span class="q-mr-md absolute" style="right: 0; font-size: 0.8rem">{{myPoint}} Coin</span>-->
<!--      </div>-->
    </section>

    <!--fixme 선물하기 버튼 -->
    <section class="full-width row bg-blue-2" style="border-top: solid 1px grey">
      <q-btn
        flat
        dense
        class="col"
        color="black"
        size="1rem"
        icon="badge"
        style="border-right: solid 1px gray"
        label="내 지갑"
        @click="gotoMyWallet"
      />
      <q-btn
        flat
        dense
        class="col"
        color="black"
        size="1rem"
        icon="add"
        label="선물하기"
        @click="gotoPreSent"
      />
    </section>

    <!--fixme 선물 내역 -->
    <section class="q-pa-sm" style="border-bottom: solid 1px grey">
      <div class="row flex-center">
        <span class="col text-weight-bold" style="font-size: 1rem">Coin 선물 내역</span>
        <q-btn
          flat
          dense
          class="col-5 bg-grey-3 text-weight-bold text-grey-7"
          label="상세내역"
          @click="gotoCoinList"
        >
          <q-icon name="chevron_right"/>
        </q-btn>
      </div>
    </section>

    <!--fixme 내역 -->
    <section>
      <div class="absolute-center" v-if="!receiveList &&!sendList">
        <span>이하 내역이없습니다.</span>
      </div>
      <div v-for="(receive) in receiveList">
        보낸 사람 : {{receive.f}}
        금액 : [{{receive.value}}] Cook
      </div>
      <br>
      <q-space style="border-bottom: 1px solid gray"/>
      <div v-for="(send) in sendList">
        받는 사람 : {{send.t}}
        금액 : [{{send.value}}] Cook
      </div>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';
  import {myUtil} from 'boot/myUtil'

  export default {
    name: 'Cook_Coin',
    computed:{
      ...mapGetters(['getLayout'])
    },
    data(){
      return{
        util : new myUtil(this),
        single: null,
        options: ['전체선물','받은선물','보낸선물'],
        myPoint : 0,
        receiveList :[],
        sendList : [],
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions(['fetchServer']),

      getMyPoint(){
        this.fetchServer({path : 'chain/balance',param :{token : LocalStorage.getItem('t')}})
          .then(result =>{
            console.log(result)
            this.myPoint = result.balance;
            this.receiveList = result.receive;
            this.sendList = result.send;
          })
          .catch(reason => {
            console.log(reason)
          })
      },

      /**======================================
       * 클릭 이벤트
       ========================================*/

      gotoMyWallet(){
        this.util.goTo('mywallet');
      },

      gotoPreSent(){
        this.util.goTo('present');
      },

      gotoCoinList(){
        this.util.goTo('coinlist',{myPoint : this.myPoint, receiveList : this.receiveList,sendList : this.sendList});
      }

    },

    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.title = "내 코인";
      this.getLayout.backbotton = true;
      this.getLayout.mainbackbotton = false;
      this.getLayout.bookmarkbtn = false;
      this.getLayout.bottomFooter = false;
      this.getLayout.addcontent = false;
      this.getMyPoint();
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
