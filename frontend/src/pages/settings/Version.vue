<!--fixme 내 정보-->
<template>
  <q-page class="bg-white">
    <!--fixme security 보안-->
    <section>
      <q-btn
        class="full-width items-start flex q-my-md text-h6 text-grey-7"
        icon="admin_panel_settings"
        flat
        label="개인/보안"
      />
      <q-separator class=""/>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';

  export default {
    name: 'MyInfo',
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
      this.getLayout.headerLayout = true;
      this.getLayout.title = "앱 버전";
      this.getLayout.backbotton = false;
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
