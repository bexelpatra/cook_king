<!--fixme 즐겨찾기-->
<template>
  <q-page class="q-pa-md">
    <section class="flex justify-between">
      <div v-for="(recipe,index) in myFavoriteRecipe" :key="index">
        <q-btn flat dense @click="contentPage(recipe)">
          <q-card style="width: 42vw; height: 50vw">
            <img class="justify-between" :src="recipe.url">
            <div class="flex q-ml-sm">
              <div class="text-h6">{{recipe.title}}</div>
              <div class="text-subtitle2">{{recipe.description}}</div>
            </div>
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
      ...mapGetters(['getLayout'])
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
      window.onpopstate = ()=>{}
      /**
       * 로그인 정보 확인하기
       */
      this.fetchServer({path : 'user/user',param:{t:LocalStorage.getItem('t'), type : 0}})
        .then(success => {
          console.log(success)
          if(success.status==200){
            this.setFavorite(success.user.favorite);
            this.setLogIn(true);

          }else{
            let timer = setTimeout(function () {
              console.log(this.data)
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
      this.getLayout.title = "즐겨찾기"
      this.getLayout.bookmarkbtn = false;
      this.getLayout.bottomFooter = true;
      this.getLayout.addcontent = false;

      /**
       * 사용자 정보 업데이트
       */
      this.userInfo({token : LocalStorage.getItem('t')});
      this.fetchServer({path :'user/user/'+LocalStorage.getItem('t')})
        .then(success =>{
          if(success.status == 200){this.myFavoriteRecipe = success.user.myFavoriteRecipe;}
        })
        .catch(reason => console.log(reason))
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
