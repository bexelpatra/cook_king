<template>
  <q-page class="bg-white">
    <!-- fixme set1 -->
    <section>
      <div class="q-pa-md text-h5 text-weight-bold">
        인기 레시피
      </div>
      <div class="q-px-xs">
        <q-card flat style="width: 98vw; border-radius: 10px;" >
          <q-card-section class="no-padding">
            <div>
              <splide
                :slides="mainslides"
                class="no-padding q-mb-xs"
                :options="mainOptions"
                @splide:moved = 'onMounted'
              >
                <splide-slide>
                  <div
                    class="q-mx-xs flex flex-center"
                    style="height: 80vw;border-radius: 5px 5px;">
                    <div class="q-ml-xs bg-blue absolute-top" style="width: 91vw; height: 55vw">
                      <q-img style="height: 55vw" src="imgs/1.png"></q-img>
                    </div>
                    <div class="q-mx-xs absolute-bottom text-h5" style="margin-bottom: 10vw">
                      "된장찌개"
                    </div>
                  </div>
                </splide-slide>
                <splide-slide>
                  <div
                    class="q-mx-xs flex flex-center"
                    style="height: 80vw; border-radius: 5px 5px;">
                  </div>
                  <div class="q-ml-xs bg-blue absolute-top" style="width: 91vw; height: 55vw">
                    <q-img style="height: 55vw" src="imgs/2.png"></q-img>
                  </div>
                  <div class="q-mx-xs absolute-bottom text-h5" style="margin-bottom: 10vw">
                    "김치찌개"
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
      <div class="q-pa-md text-h5 text-weight-bold q-pt-md">
        한식 레시피
      </div>
      <div class="q-px-xs">
        <q-card flat style="width: 98vw; border-radius: 10px;" >
          <q-card-section class="no-padding">
            <div>
              <splide
                :slides="serveslides"
                class="no-padding q-mb-xs"
                :options="serveOptions"
                @splide:moved = 'onMounted'
              >
                <splide-slide>
                  <div
                    class="q-mx-xs flex flex-center text-white"
                    style="height: 130px;border-radius: 5px 5px; background-color: black">
                  </div>
                </splide-slide>
                <splide-slide>
                  <div
                    class="q-mx-xs flex flex-center text-white"
                    style="height: 130px; border-radius: 5px 5px; background-color: pink">
                  </div>
                </splide-slide>
              </splide>
            </div>
          </q-card-section>
        </q-card>
      </div>
    </section>

    <!-- fixme set3-->
    <section class="q-mt-lg">
      <div class="q-pa-md text-h5 text-weight-bold q-pt-md">
        양식 레시피
      </div>
      <div class="q-px-xs q-mb-md">
        <q-card flat style="width: 98vw; border-radius: 10px;" >
          <q-card-section class="no-padding">
            <div>
              <splide
                :slides="serveslides"
                class="no-padding q-mb-xs"
                :options="serveOptions"
                @splide:moved = 'onMounted'
              >
                <splide-slide>
                  <div
                    class="q-mx-xs flex flex-center text-white"
                    style="height: 130px;border-radius: 5px 5px; background-color: black">
                  </div>
                </splide-slide>
                <splide-slide>
                  <div
                    class="q-mx-xs flex flex-center text-white"
                    style="height: 130px; border-radius: 5px 5px; background-color: pink">
                  </div>
                </splide-slide>
              </splide>
            </div>
          </q-card-section>
        </q-card>
      </div>
    </section>

    <!-- fixme set4-->
    <section class="q-mt-lg">
      <div class="q-pa-md text-h5 text-weight-bold q-pt-md">
        일식 레시피
      </div>
      <div class="q-px-xs q-mb-md">
        <q-card flat style="width: 98vw; border-radius: 10px;" >
          <q-card-section class="no-padding">
            <div>
              <splide
                :slides="serveslides"
                class="no-padding q-mb-xs"
                :options="serveOptions"
                @splide:moved = 'onMounted'
              >
                <splide-slide>
                  <div
                    class="q-mx-xs flex flex-center text-white"
                    style="height: 130px;border-radius: 5px 5px; background-color: black">
                  </div>
                </splide-slide>
                <splide-slide>
                  <div
                    class="q-mx-xs flex flex-center text-white"
                    style="height: 130px; border-radius: 5px 5px; background-color: pink">
                  </div>
                </splide-slide>
              </splide>
            </div>
          </q-card-section>
        </q-card>
      </div>
    </section>

    <!-- fixme set5-->
    <section class="q-mt-lg">
      <div class="q-pa-md text-h5 text-weight-bold q-pt-md">
        중식 레시피
      </div>
      <div class="q-px-xs q-mb-md">
        <q-card flat style="width: 98vw; border-radius: 10px;" >
          <q-card-section class="no-padding">
            <div>
              <splide
                :slides="serveslides"
                class="no-padding q-mb-xs"
                :options="serveOptions"
                @splide:moved = 'onMounted'
              >
                <splide-slide>
                  <div
                    class="q-mx-xs flex flex-center text-white"
                    style="height: 130px;border-radius: 5px 5px; background-color: black">
                  </div>
                </splide-slide>
                <splide-slide>
                  <div
                    class="q-mx-xs flex flex-center text-white"
                    style="height: 130px; border-radius: 5px 5px; background-color: pink">
                  </div>
                </splide-slide>
              </splide>
            </div>
          </q-card-section>
        </q-card>
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
        //main recipe
        mainslides : [],
        mainOptions : {
          type: 'slide',
          focus : 'center',
          padding : {
            right : '1rem',
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
            right : '5rem',
          },
          arrows : false,
          drag : true,
          pagination : false
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
