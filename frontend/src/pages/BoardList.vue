<!--fixme 게시물 리스트-->
<template>
  <q-page class="bg-white q-pa-sm">
    <section>
      <!-- fixme 1,2차 분류 -->
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

      <!-- fixme 검색창 -->
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

      <!-- fixme 게시물 -->
      <section class="q-mt-sm">
        <!--     반복문을 돌리면서 검색 결과를 보여줘야 한다. -->
        <!--      기본값으로 나오는 것들은 무엇을 보여줄지 정해야 한다.-->
        <div v-if="recipeList!=null">
          <div v-for="recipe in recipeList">
            <q-btn dense flat class="full-width" >
              <q-card @click="contentPage(recipe)" flat class="flex full-width" style="height:5em;">
                <div class="q-pa-sm full-height" style="width: 20%;">
                  <img :src=recipe.url height="55" width="55"/>
                </div>
                <div class="q-ml-xs q-pa-sm full-height" style="width: 70%;">
                  <div class="text-weight-bold text-left" style="font-size: 1.2em">{{strSummary(recipe.title,20)}}</div>
                  <div class="text-left">{{strSummary(recipe.description,16)}}</div>
                </div>
              </q-card>
            </q-btn>
            <q-separator class="bg-grey-4"/>
          </div>

          <q-btn flat class="full-width" @click="">
            <span>더보기</span>
          </q-btn>
        </div>
<!--        fixme 수정해야하낟-->
        <div v-else>
          아무것도 없음
        </div>

      </section>

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
        recipeList : [],
        recipePage:-1,
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
      ...mapActions(['fetchServer']),

      // 문자길이 줄이기
      strSummary : (string, num)=>{
        if(string ==null) return '';
        return myUtil.strSummary2(string,num);
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

      contentPage(recipe){
        this.$router.push({path:'content',query : {recipe : recipe}});
        // this.util.goTo('/content',{recipe : recipe})
      },

      /**=======================================
       * sever 통신구간
       =========================================*/

      getRecipes(){
        this.fetchServer({path :'/recipe/recipes',param:{firstCategory:[0,1,2,3],secondCategory:[0,1,2,3,4,5],keyword : ''}})
          .then(value => {
            value.recipes.forEach(recipe =>{this.recipeList.push(recipe); console.log(recipe)})
            this.recipePage = value.recipes.length>0? value.recipes[value.recipes.length-1].id :-1;
          })
          .catch(error => console.log(error))
      },

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
      this.getRecipes();
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
