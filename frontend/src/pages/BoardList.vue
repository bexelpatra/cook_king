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
        <!-- 검색 조건을 입력하는 부분 -->
            <q-select
              style="width: 22%; font-size: 0.9em"
              v-model="searchOption"
              :options="options"
            />
              <q-input
                clearable
                filled
                class="q-py-lg-xs"
                v-model="keyword"
                style="width: 55%;font-size: 1em"
                type="text"
              >
                <template v-slot:append>
                  <q-icon name="search" />
                </template>
              </q-input>

            <q-btn
              flat
              label="검색"
              class=""
              size="1em"
              style="width: 20%;"
              dense
              @click="searching()"
            />
          </div>
        </q-card>
      </div>
    </section>

    <section class="q-mt-md">
<!--     반복문을 돌리면서 검색 결과를 보여줘야 한다. -->
<!--      기본값으로 나오는 것들은 무엇을 보여줄지 정해야 한다.-->
      <div v-if="recipeList!=null">
        <div v-for="recipe in recipeList"  >
          <q-card class="flex" style="width: 100%;height:5em;" @click.prevent="pageMove('',recipe)">
            <div class="q-pa-sm full-height" style="width: 20%;">
              <img :src=recipe.src class="" height="55" width="55"/>
            </div>
            <div class="q-pa-sm full-height" style="width: 70%;">
              <div class="text-weight-bold" style="font-size: 1.2em">{{strSummary(recipe.name,20)}}</div>
              <div class="q-py-xs">{{strSummary(recipe.introduce,16)}}</div>
            </div>
          </q-card>
        </div>

        <q-btn class="full-width" @click="ttest(tt)">
<!--          <q-icon name ="add_circle_outline"></q-icon>-->
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
