<!--fixme 게시물 작성-->
<template>
  <q-page class="bg-white q-pa-sm">
    <section>

    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';

  export default {
    name: 'Recipe',
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
      this.getLayout.title = '게시물작성';
      this.getLayout.bottomFooter = true;
      this.getLayout.headerLayout = true;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
