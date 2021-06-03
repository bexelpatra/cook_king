<!--fixme 내 정보-->
<template>
  <q-page class="bg-white">
    <!--fixme security 보안-->
    <section>
      <q-btn
        class="full-width items-start flex text-h6 text-grey-7"
        style="height: 17vw"
        icon="admin_panel_settings"
        flat
        label="개인/보안"
        @click="securitybtn"
      />
      <q-separator/>
    </section>

    <!--fixme setting 설정-->
    <section>
        <q-btn
          class="full-width items-start flex text-h6 text-grey-7"
          style="height: 17vw"
          icon="settings"
          flat
          label="설정"
        />
      <q-separator/>
    </section>

    <!--fixme version 버전-->
    <section>
      <div class="row">
        <q-icon class="q-ma-md items-start text-h4 text-grey-7 "
                name="phonelink_setup"/>
        <div class="q-my-md text-grey-6 text-h6">AppVersion V.{{ appVersion }}</div>
      </div>
      <q-separator/>
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
        appVersion: LocalStorage.getItem("US_VS"),
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions([]),

      //페이지 이동
      securitybtn(){
        this.$router.push('personer');
      }
    },

    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.title = "내정보";
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
