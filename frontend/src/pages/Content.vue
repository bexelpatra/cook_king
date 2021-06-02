<!--fixme 레시피 게시물-->
<template>
  <q-page class="bg-white">
    <section style="">
      <q-card flat class="full-width">
        <!-- 메인사진과 요리이름       -->
        <q-card-section class="no-padding bg-grey-5">
          <img :src="recipe.src" class="full-width" :height="y">
          <div class="text-h4 text-weight-bold" style="position: absolute; top: 5.2em; left: 0.3em">{{recipe.name}}</div>
        </q-card-section>
        <!-- 간단 설명 및 소개 + 태그들       -->
        <q-card-section class="">
          <div class="text-h5">{{recipe.intro}}</div>
          <div class="text-h7">잡솨바</div>
        </q-card-section>

        <!-- 재료        -->
        <q-card-section class="no-padding">
          <div class="text-h5 q-px-sm">재료</div>
          <!--todo 반복문 돌리면서 재료를 뽑아와야한ㄷ.-->
          <div class="full-width flex" style="position: relative; height: 1.7em;">
            <div class="q-px-sm ingredient bg-white" style="left: 0;">
              야야
            </div>
            <span>{{util.dot(x)}}</span>
            <div class="q-px-sm ingredient bg-white" style="right: 0;">
              dddd
            </div>
          </div>
        </q-card-section>

        <!-- 컨텐츠 이미지 뿌리기        -->
        <q-card-section>
          <div class="flex full-width">
<!--            todo 반복문 돌림녀서 뽑아낸다. -->
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
    name: 'Board',
    computed:{
      ...mapGetters(['getLayout'])
    },
    data(){
      return{
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
    },

    beforeCreate() {},
    created() {},
    beforeMount() {
      this.getLayout.bottomFooter = true;
      this.getLayout.headerLayout = false;
    },
    mounted() {},
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
