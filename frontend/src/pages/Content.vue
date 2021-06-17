<!--fixme 레시피 게시물-->
<template>
  <q-page class="bg-white">
    <!-- fixme 해더 버튼 -->
    <section>
      <q-btn
        dense
        flat
        icon="arrow_back"
        class="z-top q-ma-sm absolute-top-left"
        @click="backBtn"
      />
      <div class="z-top q-ma-sm  absolute-top-right">
        <q-btn
          v-if="bookmark"
          flat
          dense
          round
          icon="favorite_border"
          @click="addFavorite"
        />
        <q-btn
          v-if="!bookmark"
          flat
          dense
          round
          color="red"
          icon="favorite"
          @click="addFavorite"
        />
      </div>

      <q-btn
        v-if="change"
        flat
        dense
        round
        icon="create"
        @click="changeBtn"
        class="q-mr-sm absolute-top-right z-top"
        style="margin-top: 13vw"
      />
    </section>
    <!-- fixme 콘텐츠 -->
    <section>
      <q-card flat class="full-width">
        <!-- 메인사진과 요리이름 -->
        <div>
          <img :src="recipe.src" class="full-width" :height="y">
          <div class="q-pa-sm text-h4 text-weight-bold">{{recipe.name}}</div>
        </div>
        <!-- 간단 설명 및 소개 + 태그들 -->
        <div class="q-px-md">
          <div class="text-grey-7" style="font-size: 1rem">"{{recipe.intro}}"</div>
        </div>

        <q-separator class="q-my-sm"/>

        <!-- 재료 -->
        <section>
          <div class="q-pa-sm text-h5 text-weight-bold">재료</div>
          <!--todo 반복문 돌리면서 재료를 뽑아와야한다.-->
          <div class="q-mx-sm flex" style="position: relative; height: 1.7em;">
            <div class="absolute-center full-width text-grey-7" style="border: dotted 1px grey"/>
            <span class="q-px-sm ingredient bg-white" style="left: 0;">
              야야
            </span>
            <span class="q-px-sm ingredient bg-white" style="right: 0;">
              dddd
            </span>
          </div>
        </section>

        <!-- 컨텐츠 이미지 뿌리기 -->
        <q-card-section>
          <div class="flex full-width">
            <!-- todo 반복문 돌림녀서 뽑아낸다. -->
            <div>
              <div class="relative-position">
                <div class="absolute-top-left"><q-badge>1</q-badge></div>
                <img :src="recipe.src" class="full-width" :height="y*0.7">
                <div>
                  이런저런 설명들 궁시렁궁시렁
                </div>
              </div>
            </div>
          </div>
        </q-card-section>
      </q-card>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';
  import {myUtil} from "boot/myUtil";

  export default {
    name: 'Content',
    computed:{
      ...mapGetters(['getLayout','fetchServer'])
    },
    data(){
      return{
        bookmark : true,
        change: true,

        util : new myUtil(this),
        x : window.innerWidth,
        y : window.innerHeight*0.33,
        recipe :{
          name : '맛좋다;',
          intro : '이러쿵저러쿵 먹으면 좋다',
          src :'imgs/1.png',
        },
        dot :'',
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions([]),

      /**=================================
       *  클릭 이벤트
       ===================================*/
      backBtn(){ this.$router.back()},

      changeBtn(){
        console.log('게시물 수정 버튼');
      },
      // 즐겨찾기 추가하기
      addFavorite(){
        this.fetchServer({path : 'user/favorite-recipe',method :'patch',param :{token : LocalStorage.getToken(),recipeId : this.recipe.id}})
      }

    },

    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
    },
    beforeMount() {
      this.getLayout.headerLayout = false;
      this.getLayout.backbotton = false;
      this.getLayout.title = "레시피"
      this.getLayout.bookmarkbtn = false;
      this.getLayout.bottomFooter = false;
      this.getLayout.addcontent = false;
      this.recipe = this.util.getQuery().recipe;
    },
    mounted() {
    },
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
<style type="text/css">
  .ingredient{
    position: absolute;
  }
</style>
