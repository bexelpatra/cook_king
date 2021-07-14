<!--fixme 선물하기 -->
<template>
  <q-page class="bg-white">
    <!--fixme 선물 포인트 확인하기 -->
    <section class="q-pa-md bg-blue-1" style="height: 80vw; font-size: 1rem">
      <section class="row">
        <div class="col text-weight-bold">
          내 포인트
        </div>
        <div class="text-right col">
          {{myPoint}}
          <span class="text-weight-bold">포인트</span>
        </div>
      </section>

      <section class="q-mt-md row">
        <div class="col text-weight-bold">
          선물 가능 포인트
        </div>
        <div class="text-right col">
          {{myPoint}}
          <span class="text-weight-bold">포인트</span>
        </div>
      </section>

      <section class="q-mt-md">
        <div class="row text-weight-bold">
          선물주소
        </div>
        <q-input
          class="q-mt-sm full-width bg-white"
          dense
          square
          outlined
          placeholder="필수값 입력"
          v-model="text"
        >
          <template v-slot:append>
            <q-icon v-if="text !== ''" name="close" @click="text = ''" class="cursor-pointer" />
          </template>
        </q-input>
      </section>

      <section class="q-mt-md row flex-center">
        <div class="col text-weight-bold">
          선물 포인트
        </div>
          <q-input
            class="col-7 bg-white"
            dense
            square
            outlined
            placeholder="최소 0.1"
            v-model="point"
            type="number"
          >
            <template v-slot:append>
              <q-icon v-if="point !== ''" name="close" @click="point = ''" class="cursor-pointer" />
              <span class="text-grey-6 text-weight-bold" style="font-size: 0.8rem">포인트</span>
            </template>
          </q-input>
      </section>

      <section class="q-mt-sm text-right flex-center">
        <span class="text-right">{{util.comma(point)}} 포인트 </span> <q-space/>
          <span v-if="point" class="text-red" style="font-size: 0.8rem">보내는 포인트 꼭 확인 해주세요.</span>
      </section>
    </section>

    <!--fixme 유의사항  -->
    <section class="q-pa-md text-weight-bold" style="font-size: 1.1rem">
      선물하기 전 꼭 확인 하세요!
      <div class="q-gutter-y-xs q-mt-sm text-grey-6" style="font-size: 0.85rem">
        ⊙ 현금화를 사용 하지 못합니다.<q-space/>
        ⊙ 광고,상업적인 목적으로 사용 되지 않습니다.<q-space/>
        ⊙ 위 QR코드는 한번 생성하면 재발급이 안됩니다.<q-space/>
        ⊙ 타인에게 노출되지 않게 유의 하세요.<q-space/>
        ⊙ 문제발생시 email : <span class="text-black text-weight-bold">coookkingofficial@gmail.com</span><q-space/>
      </div>
    </section>

    <!--fixme 선물하기 버튼-->
    <q-footer>
      <q-btn
        flat
        class="full-width text-weight-bold"
        style="height: 15vw"
        size="lg"
        label="선물하기"
        @click="sendPoint"
      />
    </q-footer>

  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';
  import {myUtil} from "boot/myUtil";

  export default {
    name: 'Present',
    computed:{
      ...mapGetters(['getLayout'])
    },
    data(){
      return{
        util : new myUtil(this),
        myPoint : 0,
        point: '',
        text : '',
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
        })
        .catch(reason => {
          console.log(reason)
        })
      },
      sendPoint(){
        this.fetchServer({
          path : 'chain/coin',
          param :{
            token : LocalStorage.getItem('t'),
            publicKey : this.text,
            value : this.point
          },
          method : 'post'
        })
          .then(result =>{
            console.log(result)
            // this.myPoint = result.balance;
            this.util.goTo('cookcoin')
          })
          .catch(reason => {
            console.log(reason)
          })
      },

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
      this.getLayout.title = "선물";
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
