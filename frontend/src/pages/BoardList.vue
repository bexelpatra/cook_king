<!--fixme 게시물 리스트
수정 : q-input : borderless,dark 변경

-->
<template>
  <q-page class="bg-white q-pa-sm">
    <section>
      <!-- 검색창   -->
      <div class="flex" style="height: auto">

        <!-- 검색 조건을 입력하는 부분
              select 에 label을 넣을지 확인 해볼 것-->
        <q-select
          dense
          label="선택"
          class="q-pl-md"
          style="width: 28vw"
          borderless
          v-model="searchOption"
          :options="options"
        />
        <q-input
          dense
          dark
          borderless
          v-model="keyword"
          input-class="text-right text-black"
          style="border-radius: 10px"
          class="q-pr-sm q-mr-xs full-height col bg-grey-3">
          <template v-slot:append>
            <q-icon color="black" v-if="keyword === ''" name="search" />
            <q-icon color="black" v-else name="clear" class="cursor-pointer" @click="keyword = ''" />
          </template>
        </q-input>

        <q-btn
          flat
          dense
          class="bg-grey-1"
          style="border-radius: 3px"
          label="검색"
          size="1.1rem"
          @click="searching()"
        />
      </div>

    </section>

    <section class="q-mt-sm">
      <!--     반복문을 돌리면서 검색 결과를 보여줘야 한다. -->
      <!--      기본값으로 나오는 것들은 무엇을 보여줄지 정해야 한다.-->
      <div v-if="recipeList!=null">
        <div v-for="recipe in recipeList">
          <q-btn dense flat class="full-width" >
            <q-card flat class="flex full-width" style="height:5em;" @click.prevent="pageMove('',recipe)">
              <div class="q-pa-sm full-height" style="width: 20%;">
                <img :src=recipe.src height="55" width="55"/>
              </div>
              <div class="q-ml-xs q-pa-sm full-height" style="width: 70%;">
                <div class="text-weight-bold text-left" style="font-size: 1.2em">{{strSummary(recipe.name,20)}}</div>
                <div class="text-left">{{strSummary(recipe.introduce,16)}}</div>
              </div>
            </q-card>
          </q-btn>
          <q-separator class="bg-grey-4"/>
        </div>
        <q-btn flat class="full-width" @click="ttest(tt)">
          <span>더보기</span>
        </q-btn>
      </div>
      <div v-else>
        <q-card>
          <q-card-section>
          </q-card-section>
        </q-card>
      </div>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';
  import * as myUtil from 'boot/myUtilsOldVertion';

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
          src : 'imgs/1.png',
        },{
          name : 'ds',
          introduce : '집에서 해먹으면 워워우어워워 소리가라는 음식!아니야 이건 좀더 길어야지만 해',
          src : 'imgs/2.png',
        }
        ],
        searchOption:'',
        options : [{value : 1, label : '이름'},{value : 2, label : '재료'},{value : 3, label : '키워드'}],
        tt : 0,
        ttt : 0,
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions([]),
      ttest(arg){
        this.ttt = myUtil.comma(arg);
        myUtil.notify(this,"왜 ㅇ난ㅇㄹ모",'info')
        return myUtil.comma(arg);
      },
      testt(arg){
        myUtil.notify(this,"나와라!",'info');
      },
      testtt(){
        myUtil.pageMove(this,"Main",{name : 'dd',c8 : 'sadfnklasjkl'})
        this.$router.push({name :'',query : {}})
      },
      // 문자길이 줄이기
      strSummary : (string, num)=>{
        return myUtil.strSummary(string,num);
      },
      // 페이지 이동
      pageMove : (to,query) =>{
        // myUtil.pageMove(this,to,query);
      },
      // 키워드로 검색하기
      searching : args =>{
        this.searchOption.label;
      }
    },

    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.backbotton = false;
      this.getLayout.title = "레시피"
      this.getLayout.bookmarkbtn = false;
      this.getLayout.bottomFooter = true;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
