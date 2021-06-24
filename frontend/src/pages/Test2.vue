<template>
    <q-page>
      <section>

      </section>
    </q-page>
</template>

<script>
    import {mapGetters,mapActions,mapMutations,mapState} from 'vuex';
    import {LocalStorage} from 'quasar';
    import {myUtil} from "boot/myUtil";

    export default {
        name: "Test2",
        computed :{
          ...mapGetters(['getLayout'])
        },
        watch:{
        },
        data(){
          return {
          }
        },
        methods: {
          ...mapMutations([]),
          ...mapActions([]),
        beforeCreate() {},
        created() {
          window.onpopstate = ()=>{}
        },
        beforeMount() {
          this.getLayout.bottomFooter = true;
        },
        mounted() {},
        beforeUpdate() {},
        updated() {},
        beforeDestroy() {},
        destroyed() {}
      }
    }
</script>
