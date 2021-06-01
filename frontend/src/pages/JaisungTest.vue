<template>
  <q-page>
    <splide :options="options">
      <div v-for="i in 10">
        <splide-slide>
          <div>{{i}}</div>
        </splide-slide>
      </div>
    </splide>
    <section>
      <div class="">
        test
      </div>
    </section>
  </q-page>
</template>

<script>
  import {mapActions,mapMutations,mapGetters} from 'vuex';
  import {LocalStorage} from 'quasar';
  import {Splide,SplideSlide} from '@splidejs/vue-splide';
  import '@splidejs/splide/dist/css/themes/splide-sea-green.min.css';


  export default {
    name: 'JaisungTest',
    components:{Splide,SplideSlide},
    computed:{
      ...mapGetters(['getLayout']),
    },
    data () {
      return {
        headerLayout: false,
        options:{direction : 'ltr',height : '1rem',arrows :''}
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions([]),
    },
    beforeCreate() {},
    created() {},
    beforeMount() {
      // this.getLayout.title = "테스트중입니다.";
    },
    mounted() {
    },
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>

<style scoped>

</style>

