<!--fixme 내 게시물-->
<template>
  <q-page class="q-pa-md">
    <section class="flex justify-between">
      <div v-for="(recipe,index) in myRecipe" :key="index">
        <q-btn flat dense @click="contentPage(recipe)">
          <q-card style="width: 42vw">
            <div class="q-ml-sm absolute-top-left">{{index+1}}</div>
            <img :src="'data:image/jpeg;base64,' + recipe.bytes" style="height: 35vw"/>
            <q-separator/>
            <q-card-section>
              <div class="text-weight-bold" style="font-size: 1rem">{{util.strSummary(recipe.title,5)}}</div>
              <div class="text-grey-7" style="font-size: 0.8rem">{{util.strSummary(recipe.description,'6')}}</div>
            </q-card-section>
          </q-card>
        </q-btn>
      </div>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';
  import {myUtil} from "boot/myUtil";

  export default {
    name: 'MyConTent',
    computed:{
      ...mapGetters(['getLayout','getUser'])
    },
    data(){
      return{
        util : new myUtil(this),
        myRecipe :[],

      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions(['fetchServer','userInfo']),

      /**======================================
       * 클릭 이벤트
       ========================================*/
      contentPage(recipe){
        this.util.goTo('/content',{recipe : recipe})
      },
    },

    beforeCreate() {
    },
    created() {
      this.userInfo({token : LocalStorage.getItem('t')})
      this.myRecipe = this.getUser.myRecipe

      window.onpopstate = ()=>{}
      // 1번 : 서버에서 나의 레시피만 가져온다.
      // this.fetchServer({path :'user/user/'+LocalStorage.getItem('t')})
      // .then(success =>{
      //   if(success.status == 200){
      //     this.myRecipe = success.user.myRecipe;
      //     console.log(this.myRecipe)
      //   }
      // })
      // .catch(reason => console.log(reason))

      // 2번 : 사용자 정보를 업데이트

    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.backbotton = true;
      this.getLayout.title = "내 게시물"
      this.getLayout.bookmarkbtn = false;
      this.getLayout.mainbackbotton = false;
      this.getLayout.bottomFooter = false;
      this.getLayout.addcontent = false;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
