<!--fixme 게시물 리스트-->
<template>
  <q-page class="bg-white q-pa-sm">
    <section>
      <div class = "q-py-md" style="border: 1px solid olivedrab;">
        <div class="q-py-sm">
          <span class="text-weight-bold" style="font-size: 2em">레시피</span>
        </div>

        <!-- 검색창   -->
        <q-card flat style="width: 90vw; border-radius: 10px; border: 2px solid red; height: 30px" >
          <q-card-section class="no-padding" style="border: 1px solid red; height: 10px">
            <q-input class="q-py-lg-xs" v-model="keyword" aria-placeholder="사라져라" style="border: 1px solid green"></q-input>
          </q-card-section>
        </q-card>
      </div>
    </section>
    <section class="q-mt-md">
<!--     반복문을 돌리면서 검색 결과를 보여줘야 한다. -->
<!--      기본값으로 나오는 것들은 무엇을 보여줄지 정해야 한다.-->
      <div class="q-px-md flex" style="border: 3px solid red">
        <div class="q-px-lg-xl" style="border: 10px solid red"><img src=""/></div>
        <div class="q-px-lg">
          <div>제목</div>
          <div>간단설명</div>
        </div>
      </div>

    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';

  export default {
    name: 'BoardList',
    computed:{
      ...mapGetters(['getLayout'])
    },
    data(){
      return{
        keyword : '',
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
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
