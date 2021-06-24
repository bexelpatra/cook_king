<template>
  <q-page class="bg-white">
    <!-- fixme 한식 레시피 -->
    <section class="q-mb-sm">
      <div class="q-pa-md text-h5 text-grey-6 text-weight-bold q-pt-md">
        <span class="text-black">한식</span> 인기
      </div>
      <div class="flex flex-center">
        <q-card flat style="width: 98vw; border-radius: 10px;" >
          <splide
            :slides="koreaList"
            class="no-padding q-mb-xs"
            :options="serveOptions"
            @splide:moved = 'onMounted'
          >
            <splide-slide v-for="(recipe,index) in koreaList" class="q-pr-sm" :key="index" style="height: 55vw">
              <q-btn outline color="grey" @click="contentPage(recipe)" style="border-radius: 12px">
                <div
                  class="text-white"
                  style="height: 130px;border-radius: 5px 5px;"
                >
                  <img class="full-width" :src="'data:image/jpeg;base64,' + recipe.bytes" style="height: 40vw;"/>
                </div>
                <div class="q-mt-lg full-width text-left text-h6 text-black">
                  "{{util.strSummary(recipe.title,10)}}"
                </div>
              </q-btn>
            </splide-slide>
          </splide>
        </q-card>
      </div>
    </section>

    <q-separator style="height: 3px"/>

    <!-- fixme 양식 레시피-->
    <section class="q-mb-sm">
      <div class="q-pa-md text-h5 text-grey-6 text-weight-bold q-pt-md">
        <span class="text-black">양식</span> 인기
      </div>
      <div class="flex flex-center">
        <q-card flat style="width: 98vw; border-radius: 10px;" >
          <splide
            :slides="westernList"
            class="no-padding q-mb-xs"
            :options="serveOptions"
            @splide:moved = 'onMounted'
          >
            <splide-slide v-for="(recipe,index) in westernList" class="q-pr-sm" :key="index" style="height: 55vw">
              <q-btn outline color="grey" @click="contentPage(recipe)" style="border-radius: 12px">
                <div
                  class="text-white"
                  style="height: 130px;border-radius: 5px 5px;"
                >
                  <img class="full-width" :src="'data:image/jpeg;base64,' + recipe.bytes" style="height: 40vw;"/>
                </div>
                <div class="q-mt-lg full-width text-left text-h6">
                  "{{util.strSummary(recipe.title,10)}}"
                </div>
              </q-btn>
            </splide-slide>
          </splide>
        </q-card>
      </div>
    </section>

    <q-separator style="height: 3px"/>

    <!-- fixme 일식 레시피-->
    <section class="q-mb-sm">
      <div class="q-pa-md text-h5 text-grey-6 text-weight-bold q-pt-md">
        <span class="text-black">일식</span> 인기
      </div>
      <div class="flex flex-center">
        <q-card flat style="width: 98vw; border-radius: 10px;" >
          <splide
            :slides="japanList"
            class="no-padding q-mb-xs"
            :options="serveOptions"
            @splide:moved = 'onMounted'
          >
            <splide-slide v-for="(recipe,index) in japanList" class="q-pr-sm" :key="index" style="height: 55vw">
              <q-btn outline color="grey" @click="contentPage(recipe)" style="border-radius: 12px">
                <div
                  class="text-white"
                  style="height: 130px;border-radius: 5px 5px;"
                >
                  <img class="full-width" :src="'data:image/jpeg;base64,' + recipe.bytes" style="height: 40vw;"/>
                </div>
                <div class="q-mt-lg full-width text-left text-h6">
                  {{util.strSummary(recipe.title,10)}}
                </div>
              </q-btn>
            </splide-slide>
          </splide>
        </q-card>
      </div>
    </section>

    <q-separator style="height: 3px"/>

    <!-- fixme 중식 레시피-->
    <section class="q-mb-sm">
      <div class="q-pa-md text-h5 text-grey-6 text-weight-bold q-pt-md">
        <span class="text-black">중식</span> 인기
      </div>
      <div class="flex flex-center">
        <q-card flat style="width: 98vw; border-radius: 10px;" >
          <splide
            :slides="chinaList"
            class="no-padding q-mb-xs"
            :options="serveOptions"
            @splide:moved = 'onMounted'
          >
            <splide-slide v-for="(recipe,index) in chinaList" class="q-pr-sm" :key="index" style="height: 55vw">
              <q-btn outline color="grey" @click="contentPage(recipe)" style="border-radius: 12px">
                <div
                  class="text-white"
                  style="height: 130px;border-radius: 5px 5px;"
                >
                  <img class="full-width" :src="'data:image/jpeg;base64,' + recipe.bytes" style="height: 40vw;"/>
                </div>
                <div class="q-mt-lg full-width text-left text-h6">
                  {{util.strSummary(recipe.title,10)}}
                </div>
              </q-btn>
            </splide-slide>
          </splide>
        </q-card>
      </div>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage, Platform} from 'quasar';
  import {myUtil} from "boot/myUtil";

  export default {
    name: 'Main',
    computed:{
      ...mapGetters(['getLayout','isLogIn'])
    },
    data(){
      return{
        /** Splide 데이터 */
        //main recipe
        util : new myUtil(this),
        mainslides : [],
        mainOptions : {
          type: 'slide',
          focus : 'center',
          padding : {
            // right : '1rem',
          },
          arrows : false,
          drag : true
        },
        //serve recipe
        serveslides : [],
        serveOptions : {
          type: 'slide',
          focus : 'center',
          padding : {
            right : '1.8rem',
          },
          arrows : false,
          drag : true,
          pagination : false
        },
        //게시물 출력
        mainList : [
          {
            name : ' "된장찌개" ',
            introduce : '집에서 해먹으면 워워우어워워 소리가라는 음식!아니야 이건 좀더 길어야지만 해',
            src : 'imgs/1.png',
          },
          {
            name : ' "김치찌개" ',
            introduce : '집에서 해먹으면 워워우어워워 소리가라는 음식!아니야 이건 좀더 길어야지만 해',
            src : 'imgs/2.png',
          },
          {
            name : ' "볶음밥" ',
            introduce : '집에서 해먹으면 워워우어워워 소리가라는 음식!아니야 이건 좀더 길어야지만 해',
            src : 'imgs/3.png',
          }
        ],
        koreaList:[],
        koreaPage : -1,
        japanList :[],
        japanPage : -1,
        chinaList :[],
        chinaPage : -1,
        westernList:[],
        westernPage : -1,
        index : 0,
      }
    },
    methods:{
      ...mapMutations(['setLogIn']),
      ...mapActions(['fetchServer']),

      /** Splide */
      onMounted(index){
        console.log(index)
        this.index = index;
      },

      /**==================================
       * 디바이스
       ====================================*/
      //fixme 디바이스 빽버튼 사용
      Device() {
        let self = this;
        document.addEventListener("backbutton", this.exitBtn, false);
      },

      //fixme 빽버튼 메소드
      exitBtn() {
        if (window.cordova && window.cordova.platformId !== 'android') {
          return;
        }
        let linkSrc = window.location.href.split("#")[1];
        if (linkSrc === "/main" || linkSrc === '/Main') {
          this.$q.dialog(
            {
              title: '<div class="text-h5 text-weight-bolder"><span class="text-orange-6">모두의</span> 레시피 종료</div>',
              message: '<div class="q-mt-lg  text-subtitle1 text-weight-thin ">모두의 레시피을 종료 하시겠습니까?</div>',
              html: true,
              ok: {
                flat: true,
                label: '네',
                textColor: 'primary',
                size: 'lg'
              },
              cancel: {
                flat: true,
                label: '아니오',
                textColor: 'negative',
                size: 'lg'
              },
            }
          )
            .onOk(() => {
              this.exitApp();
            })
            .onCancel(() => {
              console.log('Cancel');
            });
        }
      },

      //fixme 앱종료
      exitApp() {
        console.log("exitApp", navigator);
        navigator.app.exitApp();
      },

      /**==================================
       * 클릭이벤트
       ====================================*/
      //fixme Content 페이지 이동
      contentPage(recipe){
        this.$router.push({path:'content',query : {recipe : recipe}});
      },

      /**==================================
       * Boot import
       ====================================*/
      //fixme 문자길이 줄이기
      stringSummary : (string, num)=>{
        return this.util.strSummary(string,num);
      },
      // fixme 요리 상세보기
      getContent(recipe){
        this.util.goTo('/content',{recipe:recipe})
      },

      /**==================================
       * 서버 통신
       ====================================*/
      // fixme 카테고리별 요리 가져오기
      getRecipes(){
        let self = this;
        this.fetchServer({path : 'recipe/pop-recipes',param :{firstcategory:0,page : this.koreaPage}})
        .then(value => {
          value.recipes.forEach(recipe =>{this.koreaList.push(recipe);})
          self.koreaPage = value.recipes.length>0? value.recipes[value.recipes.length-1].id :-1;

        })
        .catch(reason => {
          console.log(reason)
        })
        this.fetchServer({path : 'recipe/pop-recipes',param :{firstcategory:1,page : this.koreaPage}})
        .then(value => {
          value.recipes.forEach(recipe =>{this.japanList.push(recipe);console.log(recipe)})
          self.japanPage = value.recipes.length>0? value.recipes[value.recipes.length-1].id :-1;
        })
        .catch(reason => {
          console.log(reason)
        })
        this.fetchServer({path : 'recipe/pop-recipes',param :{firstcategory:2,page : this.koreaPage}})
        .then(value => {
          value.recipes.forEach(recipe =>{this.chinaList.push(recipe); })
          self.chinaPage = value.recipes.length>0? value.recipes[value.recipes.length-1].id :-1;
        })
        .catch(reason => {
          console.log(reason)
        })
        this.fetchServer({
          path : 'recipe/pop-recipes',
          param :{firstcategory:3,page : this.koreaPage}})
        .then(value => {
          value.recipes.forEach(recipe =>{this.westernList.push(recipe); })
          self.westernPage = value.recipes.length>0? value.recipes[value.recipes.length-1].id :-1;
        })
        .catch(reason => {
          console.log(reason)
        })
      },

    },

    beforeCreate() {},
    created() {
      //fixme Intro로 못가게 하기 위한 코드
      history.pushState(null, null, location.href);
      window.onpopstate = ()=>{};
    },
    beforeMount() {
      this.getLayout.bottomFooter = true;
      this.getLayout.headerLayout = false;
      this.getLayout.mainbackbotton = false;
      this.getLayout.addcontent = false;
      document.addEventListener("deviceready", this.Device, false);
      this.getRecipes();
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {
      document.removeEventListener("backbutton", this.exitBtn);
      document.removeEventListener("deviceready", this.Device);
    },
    destroyed() {}
  }
</script>
