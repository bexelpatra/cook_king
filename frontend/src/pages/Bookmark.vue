<!--fixme 즐겨찾기-->
<template>
  <q-page class="q-pa-md">
    <section class="flex justify-between">
      <div v-for="index in 3" :key="index">
        <q-btn flat dense>
          <q-card style="width: 42vw; height: 50vw">
            <img class="justify-between" src="https://cdn.quasar.dev/img/mountains.jpg">
            <div class="flex q-ml-sm">
              <div class="text-h6">Card #{{ index }}</div>
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
    name: 'Bookmark',
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
    created() {
      window.onpopstate = ()=>{}
    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.backbotton = false;
      this.getLayout.title = "즐겨찾기"
      this.getLayout.bookmarkbtn = false;
      this.getLayout.bottomFooter = true;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
