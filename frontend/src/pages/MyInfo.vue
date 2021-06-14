<!--fixme 내 정보-->
<template>
  <q-page class="bg-white">
    <!--fixme Mycontent 내 게시물-->
    <section>
      <q-btn
        class="full-width items-start flex text-h6 text-grey-7"
        style="height: 17vw"
        icon="folder"
        flat
        label="내 게시물"
        @click="myConTentPage"
      />
      <q-separator/>
    </section>

<!--    &lt;!&ndash;fixme security 핀/생체&ndash;&gt;-->
<!--    <section>-->
<!--      <q-btn-->
<!--        class="full-width items-start flex text-h6 text-grey-7"-->
<!--        style="height: 17vw"-->
<!--        icon="admin_panel_settings"-->
<!--        flat-->
<!--        label="핀/생체"-->
<!--        @click="securitybtn"-->
<!--      />-->
<!--      <q-separator/>-->
<!--    </section>-->

<!--    &lt;!&ndash;fixme setting 설정&ndash;&gt;-->
<!--    <section>-->
<!--        <q-btn-->
<!--          class="full-width items-start flex text-h6 text-grey-7"-->
<!--          style="height: 17vw"-->
<!--          icon="settings"-->
<!--          flat-->
<!--          label="설정"-->
<!--        />-->
<!--      <q-separator/>-->
<!--    </section>-->

    <!--fixme version 버전-->
    <section>
      <div class="row">
        <q-icon class="q-ma-md items-start text-h4 text-grey-7 "
                name="phonelink_setup"/>
        <div class="q-my-md text-grey-6 text-h6">AppVersion V. 1.0.1</div>
<!--        <div class="q-my-md text-grey-6 text-h6">AppVersion V.{{ appVersion }}</div>-->
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

      /**======================================
       * 클릭 이벤트
       ========================================*/
      securitybtn(){
        this.$router.push('personer');
      },
      myConTentPage(){
        this.$router.push('mycontent');
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
      this.getLayout.addcontent = false;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
