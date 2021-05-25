<!--
fixme 21.05.24 splide 이미지 사이즈 조정및 페이지 추가 해야됨.
fixme 추가적으로  레이아웃 설명
-->
<template>
  <q-page class="bg-white q-pa-sm">
    <!-- fixme set1 -->
    <section>
      <div class="text-h5 text-weight-bold q-pb-sm">
        인기 레시피
      </div>
      <div class="q-px-md">
        <q-card flat style="width: 90vw; border-radius: 10px;" >
          <q-card-section class="no-padding">
            <div>
              <!-- fixme #709
              splide NPM 설치
              설치 :npm install @splidejs/vue-splide
              슬라이드 카드뷰 사용 하기 위한
              NPM
              -->
              <splide
                :slides="slides"
                class="no-padding q-mb-xs"
                :options="splideOptions"
                @splide:moved = 'onMounted'
              >
                <splide-slide>
                  <div
                    class="q-mx-xs flex flex-center text-white"
                    style="height: 170px;border-radius: 5px 5px; background-color: black">
                  </div>
                </splide-slide>
                <splide-slide>
                  <div
                    class="q-mx-xs flex flex-center text-white"
                    style="height: 170px; border-radius: 5px 5px; background-color: pink">
                  </div>
                </splide-slide>
              </splide>
            </div>
          </q-card-section>
        </q-card>
      </div>
    </section>

    <!-- fixme set2 -->
    <section class="q-mt-lg">
      <div class="text-h5 text-weight-bold q-pt-md q-pb-sm">
        오늘의 레시피
      </div>
      <div>
        <q-btn-group flat>
          <q-btn outline style="width: 60vw; height: 35vw"/>
          <q-btn class="q-ml-md" outline style="width: 30vw"/>
        </q-btn-group>
      </div>
    </section>

    <!-- fixme set3-->
    <section class="q-mt-lg">
      <div class="text-h5 text-weight-bold q-pt-md q-pb-sm">
        추천 레시피
      </div>
      <div>
        <q-btn-group flat>
          <q-btn outline style="width: 60vw; height: 35vw"/>
          <q-btn class="q-ml-md" outline style="width: 30vw"/>
        </q-btn-group>
      </div>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';

  export default {
    name: 'Main',
    computed:{
      ...mapGetters(['getLayout'])
    },
    data(){
      return{
        slides : [],
        splideOptions : {
          type: 'slide',
          focus : 'center',
          padding : {
            right : '1rem',
          },
          arrows : false,
          drag : true
        }
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions([]),

      onMounted(index){
        console.log(index)
        this.index = index;
      }
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
