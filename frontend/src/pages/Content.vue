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
      <div class="z-top q-ma-sm  absolute-top-right" v-if="logIn">
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
          <img :src="'data:image/jpeg;base64,'+recipe.bytes" class="full-width" style="height: 62vw">
          <div class="q-pa-sm q-ml-sm text-h4 text-weight-bold">{{recipe.title}}</div>
        </div>
        <!-- 간단 설명 및 소개 + 태그들 -->
        <div class="q-px-md">
          <div class="text-grey-7" style="font-size: 1rem">"{{recipe.description}}"</div>
        </div>

        <q-separator class="q-my-md"/>

        <!-- 재료 -->
        <section>
          <div class="q-pa-sm q-ml-sm text-h5 text-weight-bold">재료</div>
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

        <q-separator class="q-mt-md"/>

        <!-- 컨텐츠 이미지 뿌리기 -->
        <q-card-section>
          <!-- todo 반복문 돌림녀서 뽑아낸다. -->
          <div class="text-h5 text-weight-bold">레시피</div>
          <div class="q-mt-md relative-position" v-for="(content,index) in recipe.contentList">
            <div v-if="index !=0">
              <div class="q-ml-xs absolute-top-left"><q-badge>{{index}}</q-badge></div>
              <img :src="'data:image/jpeg;base64,'+content.bytes" class="full-width " style="height: 60vw"/>
              <div style="font-size: 1rem; white-space: pre-line; overflow: auto">
                {{content.description}}
              </div>
              <q-separator class="q-my-lg"/>
            </div>

          </div>
        </q-card-section>
      </q-card>

      <!-- fixme 페이지 업 -->
      <q-page-scroller class="absolute-bottom-right" :scroll-offset="150" :offset="[18, 18]">
        <q-btn fab icon="keyboard_arrow_up" color="blue-6"/>
      </q-page-scroller>
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
      ...mapGetters(['getLayout','getUser','isLogIn'])
    },
    data(){
      return{
        bookmark : false,
        change: false,
        logIn : false,
        util : new myUtil(this),
        recipe :{},
        dot :'',

        favoriteMark : false,
        updateMark : false,
      }
    },
    methods:{
      ...mapMutations(['setFavorite']),
      ...mapActions(['fetchServer','userInfo']),

      /**=================================
       *  클릭 이벤트
       ===================================*/
      backBtn(){ this.$router.back()},

      changeBtn(){
        console.log('게시물 수정 버튼');
        this.util.goTo('updatecontent',{recipe : this.recipe})
      },
      // 즐겨찾기 추가하기
      async addFavorite(){
        this.fetchServer({
          path : 'user/favorite-recipes',
          method :'post',
          param :{
            token : LocalStorage.getItem("t"),
            recipeId : this.recipe.id}
        })
          .then(result =>{
            console.log(result)
            this.bookmark = !this.bookmark;
            this.userInfo({token :LocalStorage.getItem('t')});
          })
          .catch(reason => console.log(reason))
      },
      getContentList(recipeId){
        this.fetchServer({path : 'recipe/recipe/content',param:{recipe : recipeId}})
        .then(success =>{
          console.log(success)
          if(success.status==200){
            this.recipe.contentList = success.recipe.contentList;
          }
        })
        .catch(reason => {console.log(reason)})
      },
    },

    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
    },
    beforeMount() {
      this.getLayout.headerLayout = false;
      this.getLayout.backbotton = false;
      this.getLayout.mainbackbotton = false;
      this.getLayout.title = "레시피"
      this.getLayout.bookmarkbtn = false;
      this.getLayout.bottomFooter = false;
      this.getLayout.addcontent = false;


      this.recipe = this.util.getQuery().recipe;
      this.getContentList(this.recipe.id);
      console.log(this.recipe)
      // 수정하기버튼
      if(this.getUser.id!=null){
        this.change = this.getUser.id == this.recipe.usersDto.id;
        this.bookmark = this.getUser.myFavoriteRecipe.map(myFavoriteRecipe=> myFavoriteRecipe.id).includes(this.recipe.id);
        this.logIn = this.getUser ==null || this.getUser.id == 0 ? false:true;
      }

    },
    mounted() {
    },
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {
      this.userInfo({token : LocalStorage.getItem('t')});
    }
  }
</script>
<style type="text/css">
  .ingredient{
    position: absolute;
  }
</style>
