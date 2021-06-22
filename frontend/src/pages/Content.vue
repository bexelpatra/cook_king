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
      <div class="z-top q-ma-sm  absolute-top-right" v-if="isLogIn">
        <q-btn
          v-if="!bookmark"
          flat
          dense
          round
          icon="favorite_border"
          @click="addFavorite"
        />
        <q-btn
          v-if="bookmark"
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
          <img :src="recipe.url" class="full-width" :height="y">
          <div class="q-pa-sm text-h4 text-weight-bold">{{recipe.title}}</div>
        </div>
        <!-- 간단 설명 및 소개 + 태그들 -->
        <div class="q-px-md">
          <div class="text-grey-7" style="font-size: 1rem">"{{recipe.description}}"</div>
        </div>

        <q-separator class="q-my-sm"/>

        <!-- 재료 -->
        <section>
          <div class="q-pa-sm text-h5 text-weight-bold">재료</div>
          <!--todo 반복문 돌리면서 재료를 뽑아와야한다.-->
          <div class="q-mx-sm flex" style="position: relative; height: 1.7em;" v-for="stuff in recipe.stuffList">
            <div class="absolute-center full-width text-grey-7" style="border: dotted 1px grey"/>
            <span class="q-px-sm ingredient bg-white" style="left: 0;">
<!--              [12,12]-->
              {{stuff.split(':')[0]}}
            </span>
            <span class="q-px-sm ingredient bg-white" style="right: 0;">
              {{stuff.split(':')[1]}}
            </span>
          </div>
        </section>

        <!-- 컨텐츠 이미지 뿌리기 -->
        <q-card-section>
          <div class="flex full-width">
            <!-- todo 반복문 돌림녀서 뽑아낸다. -->
            <div>
              <div class="relative-position" v-for="(content,index) in recipe.contentList">
                <div v-if="index !=0">
                  <div class="absolute-top-left"><q-badge>{{index}}</q-badge></div>
                  <img :src="content.url" class="full-width" :height="y*0.7">
                  <div>
                    {{content.description}}
                  </div>
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
      ...mapGetters(['getLayout','getFavorite','isLogIn'])
    },
    data(){
      return{
        bookmark : false,
        change: true,
        logIn : this.isLogIn,
        util : new myUtil(this),
        x : window.innerWidth,
        y : window.innerHeight*0.33,
        recipe :{},
        dot :'',

        favoriteMark : false,
        updateMark : false,
      }
    },
    methods:{
      ...mapMutations(['setFavorite']),
      ...mapActions(['fetchServer']),

      /**=================================
       *  클릭 이벤트
       ===================================*/
      backBtn(){ this.$router.back()},

      changeBtn(){
        console.log('게시물 수정 버튼');
        this.util.goTo('updatecontent',{recipe : this.recipe})
      },
      // 즐겨찾기 추가하기
      addFavorite(){
        this.fetchServer({
          path : 'user/favorite-recipes',
          method :'post',
          param :{
            token : LocalStorage.getItem("t"),
            recipeId : this.recipe.id}})
        .then(result =>{
          console.log(result)
          this.setFavorite(result.favorite);
          this.bookmark = !this.bookmark;
        })
        .catch(reason => console.log(reason))
      },

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
      console.log(this.getFavorite)
      console.log(this.recipe.id)
      this.bookmark = this.getFavorite.includes(this.recipe.id);
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
