<!--fixme 게시물 리스트-->
<template>
  <q-page class="bg-white q-pa-sm">
    <section>
      <!-- 검색창   -->
      <div class="flex q-gutter-y-sm">
        <!-- 1차 분류 -->
        <q-select
          class="full-width"
          filled
          dense
          v-model="oneselect"
          :options="options1"
          label="1차 분류"
          multiple
          emit-value
          map-options
        >
          <template v-slot:option="{ itemProps, itemEvents, opt, selected, toggleOption }">
            <q-item
              v-bind="itemProps"
              v-on="itemEvents"
            >
              <q-item-section>
                <q-item-label v-html="opt.label" ></q-item-label>
              </q-item-section>
              <q-item-section side>
                <q-checkbox :value="selected" @input="toggleOption"></q-checkbox>

              </q-item-section>
            </q-item>
          </template>
        </q-select>

        <!-- 2차 분류 -->
        <q-select
          class="full-width"
          filled
          dense
          v-model="twoselect"
          :options="options2"
          label="2차 분류"
          multiple
          emit-value
          map-options
        >
          <template v-slot:option="{ itemProps, itemEvents, opt, selected, toggleOption }">
            <q-item
              v-bind="itemProps"
              v-on="itemEvents"
            >
              <q-item-section>
                <q-item-label v-html="opt.label"/>
              </q-item-section>
              <q-item-section side>
                <q-checkbox :value="selected" @input="toggleOption"/>
              </q-item-section>
            </q-item>
          </template>
        </q-select>
      </div>

      <div class="q-mt-sm row">
        <q-input
          dark
          borderless
          v-model="keyword"
          input-class="text-right text-black"
          class="q-pr-sm q-mr-xs col bg-grey-3 text-h6">
          <template v-slot:append>
            <q-icon color="black" v-if="keyword === ''" name="search" />
            <q-icon color="black" v-else name="clear" class="cursor-pointer" @click="keyword = ''" />
          </template>
        </q-input>

        <q-btn
          flat
          dense
          class="bg-grey-1 text-weight-bold"
          style="border-radius: 3px"
          label="검색"
          size="0.9rem"
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
            <q-card @click="contentPage" flat class="flex full-width" style="height:5em;" @click.prevent="pageMove('',recipe)">
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
        //1차 2차 분류
        oneselect: [],
        options1: [{val: 0, label: '한식'},{val: 1, label: '일식'},{val: 2, label: '중식'},{val: 3, label: '양식'}],
        twoselect: [],
        options2: [{val: 0, label: '기타'},{val: 1, label: '볶음'},{val: 2, label: '튀김'},{val: 3, label: '구이'},{val: 4, label: '찜'},{val: 5, label: '국물'},],

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
      // 카테고리 체크 외 키워드로 검색하기
      searching(){
        if (this.oneselect == null || this.oneselect == ''){
          this.$q.notify({
            message: "1차 분류를 선택 해주세요.",
            type : "negative"
          })
          return;
        } else if (this.twoselect == null || this.twoselect == ''){
          this.$q.notify({
            message : "2차 분류를 선택 해주세요.",
            type : "negative"
          })
          return;
        } this.searchOption.label;
      },
      // searching1 : args =>{
      // this.searchOption.label;
      // }

      contentPage(){
        this.$router.push('content');
      },



    /**=======================================
     * sever 통신구간
     =========================================*/

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
      this.getLayout.addcontent = true;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
