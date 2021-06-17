<template>
  <q-page class="bg-white">
    <!-- fixme 인기 레시피  텍스트 왼쪽 마진 줘야됨. -->
    <section>
      <div class="q-pa-md text-h5 text-grey-6 text-weight-bold">
        <span class="text-blue">인기 </span>레시피
      </div>
      <div class="flex flex-center">
        <q-card flat style="width: 98vw; border-radius: 10px;" >
          <splide
            :slides="mainslides"
            class="no-padding q-mb-xs"
            :options="mainOptions"
            @splide:moved = 'onMounted'
          >
            <splide-slide v-for="(main,index) in mainList" :key="index">
              <div
                class="q-mx-xs"
                style="height: 80vw;border-radius: 5px 5px;"
              >
                <div class="q-ml-xs full-width absolute-top" style="width: 91vw; height: 55vw">
                  <img class="full-width" :src="main.src" style="height: 55vw;"/>
                </div>
                <div class="q-mx-xs absolute-bottom text-h5" style="margin-bottom: 10vw">
                  {{main.name}}
                </div>
              </div>
            </splide-slide>
          </splide>
        </q-card>
      </div>
    </section>

    <q-separator style="height: 3px"/>

    <!-- fixme 한식 레시피 -->
    <section class="q-mb-sm">
      <div class="q-pa-md text-h5 text-grey-6 text-weight-bold q-pt-md">
        <span class="p1">한식 </span>레시피
      </div>
      <div class="flex flex-center">
        <q-card flat style="width: 98vw; border-radius: 10px;" >
          <splide
            :slides="serveslides"
            class="no-padding q-mb-xs"
            :options="serveOptions"
            @splide:moved = 'onMounted'
          >
            <splide-slide v-for="(main,index) in mainList" :key="index">
              <div
                class="q-ml-xs q-mr-md  text-white"
                style="height: 130px;border-radius: 5px 5px;"
              >
                <img class="full-width" :src="main.src" style="height: 32vw;"/>
              </div>
              <div class="q-mx-xs text-h6">
                {{main.name}}
              </div>
            </splide-slide>
          </splide>
        </q-card>
      </div>
    </section>

    <q-separator style="height: 3px"/>

    <!-- fixme 양식 레시피-->
    <section class="q-mb-sm">
      <div class="q-pa-md text-h5 text-grey-6 text-weight-bold q-pt-md">
        <span class="text-black">양식</span> 레시피
      </div>
      <div class="flex flex-center">
        <q-card flat style="width: 98vw; border-radius: 10px;" >
          <splide
            :slides="serveslides"
            class="no-padding q-mb-xs"
            :options="serveOptions"
            @splide:moved = 'onMounted'
          >
            <splide-slide v-for="(main,index) in mainList" :key="index">
              <div
                class="q-ml-xs q-mr-md  text-white"
                style="height: 130px;border-radius: 5px 5px;"
              >
                <img class="full-width" :src="main.src" style="height: 32vw;"/>
              </div>
              <div class="q-mx-xs text-h6">
                {{main.name}}
              </div>
            </splide-slide>
          </splide>
        </q-card>
      </div>
    </section>

    <q-separator style="height: 3px"/>

    <!-- fixme 일식 레시피-->
    <section class="q-mb-sm">
      <div class="q-pa-md text-h5 text-grey-6 text-weight-bold q-pt-md">
        <span class="text-black">일식</span> 레시피
      </div>
      <div class="flex flex-center">
        <q-card flat style="width: 98vw; border-radius: 10px;" >
          <splide
            :slides="serveslides"
            class="no-padding q-mb-xs"
            :options="serveOptions"
            @splide:moved = 'onMounted'
          >
            <splide-slide v-for="(main,index) in mainList" :key="index">
              <div
                class="q-ml-xs q-mr-md  text-white"
                style="height: 130px;border-radius: 5px 5px;"
              >
                <img class="full-width" :src="main.src" style="height: 32vw;"/>
              </div>
              <div class="q-mx-xs text-h6">
                {{main.name}}
              </div>
            </splide-slide>
          </splide>
        </q-card>
      </div>
    </section>

    <q-separator style="height: 3px"/>

    <!-- fixme 중식 레시피-->
    <section class="q-mb-sm">
      <div class="q-pa-md text-h5 text-grey-6 text-weight-bold q-pt-md">
        <span class="text-black">중식</span> 레시피
      </div>
      <div class="flex flex-center">
        <q-card flat style="width: 98vw; border-radius: 10px;" >
          <splide
            :slides="serveslides"
            class="no-padding q-mb-xs"
            :options="serveOptions"
            @splide:moved = 'onMounted'
          >
            <splide-slide v-for="(main,index) in mainList" :key="index">
              <div
                class="q-ml-xs q-mr-md  text-white"
                style="height: 130px;border-radius: 5px 5px;"
              >
                <img class="full-width" :src="main.src" style="height: 32vw;"/>
              </div>
              <div class="q-mx-xs text-h6">
                {{main.name}}
              </div>
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
  import * as myUtil from 'boot/myUtilsOldVertion';

  export default {
    name: 'Main',
    computed:{
      ...mapGetters(['getLayout'])
    },
    data(){
      return{
        /** Splide 데이터 */
        //main recipe
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
            right : '3rem',
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
        index : 0,
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions([]),

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
        if (linkSrc === "/main") {
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
       * Boot import
       ====================================*/
      //fixme 문자길이 줄이기
      stringSummary : (string, num)=>{
        return myUtil.strSummary(string,num);
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
      this.getLayout.addcontent = false;
      document.addEventListener("deviceready", this.Device, false);
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
