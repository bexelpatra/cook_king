<!--fixme 즐겨찾기-->
<template>
  <q-page class="q-pa-md">
    <section class="flex justify-between">
      <div v-for="(recipe,index) in myFavoriteRecipe" :key="index">
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
    name: 'Bookmark',
    computed:{
      ...mapGetters(['getLayout','getUser'])
    },
    data(){
      return{
        util : new myUtil(this),
        myFavoriteRecipe : [],
      }
    },
    methods:{
      ...mapMutations(['setFavorite','setLogIn']),
      ...mapActions(['fetchServer','userInfo']),
      contentPage(recipe){
        this.util.goTo('/content',{recipe : recipe})
      },
    },

    beforeCreate() {},
    created() {
      let self = this;
      window.onpopstate = (event)=>{}
      /**
       * 로그인 정보 확인하기
       */
      this.fetchServer({path : 'user/user',param:{t:LocalStorage.getItem('t'), type : 0}})
        .then(success => {
          console.log(success)
          if(success.status==200){
            // this.setFavorite(success.user.favorite);
            // this.setLogIn(true);

          }else{
            let timer = setTimeout(function () {
              self.util.goTo('login')
            },700);
            self.util.notify('로그인 정보가 없습니다.','warning');
          }
        })
        .catch(reason => {
          let timer = setTimeout(function () {
            this.util.goTo('login')
          },700);
          this.util.notify('로그인 정보가 없습니다.','warning');
        })
    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.backbotton = false;
      this.getLayout.mainbackbotton = false;
      this.getLayout.title = "즐겨찾기"
      this.getLayout.bookmarkbtn = false;
      this.getLayout.bottomFooter = true;
      this.getLayout.addcontent = false;

      /**
       * 사용자 정보 업데이트
       */
      this.myFavoriteRecipe = this.getUser.myFavoriteRecipe;
      // this.userInfo({token : LocalStorage.getItem('t')});
      // this.fetchServer({path :'user/user/'+LocalStorage.getItem('t')})
      //   .then(success =>{
      //     if(success.status == 200){this.myFavoriteRecipe = success.user.myFavoriteRecipe;}
      //   })
      //   .catch(reason => console.log(reason))
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
