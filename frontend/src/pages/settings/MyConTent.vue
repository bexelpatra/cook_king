<!--fixme 내 게시물-->
<template>
  <q-page class="q-pa-md">
    <section class="flex justify-between">
      <div v-for="index in 6" :key="index">
        <q-btn flat dense>
          <q-card style="width: 42vw; height: 50vw">
            <img class="justify-between" src="https://cdn.quasar.dev/img/mountains.jpg">
            <div class="flex q-ml-sm">
              <div class="text-h6">Card {{ index }}</div>
              <div class="text-subtitle2">by John Doe</div>
            </div>
          </q-card>
        </q-btn>
      </div>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';

  export default {
    name: 'MyConTent',
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

      /**======================================
       * 클릭 이벤트
       ========================================*/

    },

    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.backbotton = true;
      this.getLayout.title = "내 게시물"
      this.getLayout.bookmarkbtn = false;
      this.getLayout.bottomFooter = false;
      this.getLayout.addcontent = false;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
