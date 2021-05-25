<!--fixme 레시피 게시물-->
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
    name: 'Board',
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
      this.getLayout.title = '레시피 정보';
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
