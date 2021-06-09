<template>
  <q-layout view="lHh lpr lFf">
    <q-header ref="header" v-if="headerLayout" class="bg-white text-black">
      <q-toolbar class="flex items-center">
        <!--fixme 뒤로가기 버튼-->
        <q-btn
          v-if="backbotton"
          flat
          dense
          round
          icon="arrow_back"
          color="black"
          @click="backBtn"
        />

        <q-toolbar-title class="no-padding text-h5 text-weight-bold absolute-center">
          {{title}}
        </q-toolbar-title>

        <!--fixme 즐겨찾기 추가 버튼-->
        <q-space/>
        <section v-if="bookmarkbtn">
          <q-btn
            v-if="bookmark"
            flat
            dense
            round
            icon="favorite_border"
            @click="bookmark = false"
          />
          <q-btn
            v-if="!bookmark"
            flat
            dense
            round
            icon="favorite"
            @click="bookmark = true"
          />
        </section>

        <q-btn
          v-if="addcontent"
          flat
          dense
          round
          icon="edit"
          @click="contentBtn"
        />

      </q-toolbar>
    </q-header>
    <q-footer v-if="bottomFooter">
      <q-tabs
        v-model="tab"
        class="bg-black full-width"
        dense
        indicator-color="transparent"
        active-color="white"
      >
        <q-tab class="col text-white" name="home" icon="home" label="홈" @click="mainPage"/>
        <q-tab class="col text-white" name="list" icon="list" label="레시피" @click="recipePage"/>
        <q-tab class="col text-white" name="archive" icon="archive" label="즐찾" @click="mybookPage"/>
        <q-tab class="col text-white" name="how_to_reg" icon="how_to_reg" label="내정보" @click="myinfoPage"/>
      </q-tabs>
    </q-footer>

    <q-page-container>
      <router-view />
    </q-page-container>

  </q-layout>
</template>

<script>
  import {mapActions,mapMutations,mapGetters} from 'vuex';
  import {LocalStorage} from 'quasar';

  export default {
    name: 'MainLayout',
    computed:{
      ...mapGetters([]),
    },
    data () {
      return {
        tab: 'home',

        //fixme setLayout
        bottomFooter: true,
        headerLayout: false,
        title : '',
        backbotton: true,
        bookmark: true,
        bookmarkbtn: true,
        addcontent: false,
      }
    },
    methods:{
      ...mapMutations([
        'setLayout'
      ]),
      ...mapActions([]),

      //fixme 페이지 이동 클릭 이벤트
      //홈
      mainPage(){ this.$router.push('main'); },
      //레시피
      recipePage(){ this.$router.push('boardlist'); },
      //즐찾
      mybookPage(){ this.$router.push('bookmark'); },
      //내정보
      myinfoPage(){ this.$router.push('myinfo'); },
      //뒤로가기
      backBtn(){ this.$router.back()},
      //게시물작성
      contentBtn(){ this.$router.push('')}
    },
    beforeCreate() {},
    created() {
      window.onpopstate = () => {};
    },
    beforeMount() {
      this.setLayout(this);
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
