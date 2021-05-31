<!--fixme 게시물 리스트-->
<template>
  <q-page class="bg-white q-pa-sm">
    <section>
      <div class = "q-py-sm">
        <div class="q-py-sm q-px-sm">
          <span class="text-weight-bold" style="font-size: 2em">레시피</span>
        </div>

        <!-- 검색창   -->
        <q-card flat class="" style="border-radius: 10px; height: 4em" >
          <div class="flex">
            <q-select
              style="width: 25%;"
              v-model="searchOption"
              :options="options"
              me
            />
              <q-input
                clearable
                filled
                label="#검색"
                class="q-py-lg-xs"
                v-model="keyword"
                style="width: 55%;font-size: 2em"
              >

              </q-input>
            <q-btn
              flat
              label="검색"
              class=""
              size="1em"
              style="width: 20%;"
              dense
            />
          </div>
        </q-card>
      </div>
    </section>

    <section class="q-mt-md">
<!--     반복문을 돌리면서 검색 결과를 보여줘야 한다. -->
<!--      기본값으로 나오는 것들은 무엇을 보여줄지 정해야 한다.-->
      <q-card class="flex" style="width: 100%;height:5em;" v-for="recipe in recipeList">
        <div class="q-pa-sm full-height" style="width: 20%;"><img :src=recipe.scr class="full-height"/></div>
        <div class="q-pa-sm full-height" style="width: 70%;">
          <div class="text-weight-bold" style="font-size: 1.2em">{{strSummary(recipe.name,20)}}</div>
          <div class="q-py-xs">{{strSummary(recipe.introduce,16)}}</div>
        </div>
      </q-card>
      <q-space style="color: #999999"/>
      <q-input v-model="tt"/>
      <q-btn @click="ttest(tt)">{{ttt}}</q-btn>
      <q-btn @click="testt()">노티</q-btn>
      <q-btn @click="testtt()">가즈아</q-btn>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';
  import * as myUtil from 'boot/myUtils';

  export default {
    name: 'BoardList',
    computed:{
      ...mapGetters(['getLayout'])
    },
    data(){
      return{
        keyword : '',
        recipeList : [{
          name : '워워우어워워',
          introduce : '집에서 해먹으면 워워우어워워 소리가라는 음식!아니야 이건 좀더 길어야지만 해',
          scr : '',
        }
        ],
        searchOption:'',
        options : [1,2,3],
        tt : 0,
        ttt : 0,
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions([]),
      ttest(arg){
        this.ttt = myUtil.comma(arg);
        return myUtil.comma(arg);
      },
      testt(arg){
        myUtil.notify(this,"나와라!",'info');
      },
      testtt(){
        myUtil.pageMove(this,"Main",{name : 'dd',c8 : 'sadfnklasjkl'})
        this.$router.push({name :'',query : {}})
      },
      strSummary : (string, num)=>{
        return myUtil.strSummary(string,num);
      }
    },

    beforeCreate() {},
    created() {},
    beforeMount() {
      this.getLayout.bottomFooter = true;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
