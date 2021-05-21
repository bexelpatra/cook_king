<template>
  <q-page class="bg-white q-pa-sm">
    <!-- fixme set1 -->
    <section>
      <div class="text-h5 text-weight-bold q-pb-sm">
        인기 레시피
      </div>
      <div class="flex flex-center full-width" style="border: solid 1px red; height: 50vw">
        여기는 슬라이드 이미지<br>
        하단에는 레시피 이름
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

      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions([]),
    },

    beforeCreate() {},
    created() {},
    beforeMount() {
      this.getLayout.title = '테스트1';
      this.getLayout.headerLayout = true;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
