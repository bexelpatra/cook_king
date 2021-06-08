<!--fixme 로그인-->
<template>
  <q-page class="q-pa-md">
    <section class="absolute-center">
      <q-card style="border: solid 1px black">
        <q-input/>
        <q-input/>
      </q-card>
      <q-btn/>
      <div>회원가입</div><div>비밀번호찾기</div>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';

  export default {
    name: 'Login',
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
      this.getLayout.headerLayout = true;
      this.getLayout.backbotton = false;
      this.getLayout.title = "로그인"
      this.getLayout.bookmarkbtn = false;
      this.getLayout.bottomFooter = false;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
