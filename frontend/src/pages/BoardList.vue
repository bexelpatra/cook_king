<!--fixme 게시물 리스트-->
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
    name: 'BoardList',
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
      this.getLayout.bottomFooter = true;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
