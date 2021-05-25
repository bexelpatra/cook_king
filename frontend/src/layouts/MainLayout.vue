<!--fixme q-Layout 에 view="LHh lpr lFf" 안적으면 footer가 안먹힘-->
<template>
  <q-layout view="lHh lpr lFf">
    <q-header v-if="headerLayout">
      <q-toolbar>
        <!--fixme 뒤로가기 버튼-->
        <q-btn
          v-if="backbotton"
          flat
          dense
          round
          icon="arrow_back"
          color="black"
        />

        <q-toolbar-title class="text-h5 text-weight-bold absolute-center">
          {{title}}
        </q-toolbar-title>

        <!--fixme 즐겨찾기 추가 버튼-->
        <q-space/>
        <section>
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

      </q-toolbar>
    </q-header>
    <q-footer v-if="bottomFooter">
      <q-tabs
        v-model="tab"
        class="bg-black full-width"
        dense
        align="justify"
      >
        <q-tab class="col text-red" name="home" icon="home" label="홈" @click="mainPage"/>
        <q-tab class="col text-blue" name="list" icon="list" label="레시피" @click="recipePage"/>
        <q-tab class="col text-green" name="tag" icon="tag" label="태그" @click="tagPage"/>
        <q-tab class="col text-purple" name="archive" icon="archive" label="즐찾" @click="mybookPage"/>
        <q-tab class="col text-yellow" name="how_to_reg" icon="how_to_reg" label="내정보" @click="myinfoPage"/>
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
      //태그
      tagPage(){ this.$router.push('tag'); },
      //즐찾
      mybookPage(){ this.$router.push('bookmark'); },
      //내정보
      myinfoPage(){ this.$router.push('my'); }
    },
    beforeCreate() {},
    created() {},
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
