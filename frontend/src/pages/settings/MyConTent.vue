<!--fixme 내 게시물-->
<template>
  <q-page class="q-pa-md">
    <section class="flex justify-between">
      <div v-for="(recipe,index) in myRecipe" :key="index">
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
    name: 'MyConTent',
    computed:{
      ...mapGetters(['getLayout'])
    },
    data(){
      return{
        util : new myUtil(this),
        myRecipe :[],

      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions(['fetchServer']),

      /**======================================
       * 클릭 이벤트
       ========================================*/
      contentPage(recipe){
        this.util.goTo('/content',{recipe : recipe})
      },
    },

    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
      this.fetchServer({path :'user/user/'+LocalStorage.getItem('t')})
      .then(success =>{
        if(success.status == 200){
          this.myRecipe = success.user.myRecipe;
          console.log(this.myRecipe)
        }
      })
      .catch(reason => console.log(reason))
    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.backbotton = true;
      this.getLayout.title = "내 게시물"
      this.getLayout.bookmarkbtn = false;
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
