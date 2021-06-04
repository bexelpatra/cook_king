<template>
    <q-page>
      <section>
        <div>마이유틸사용 예시입니다.</div>
        <div>콤마찍기</div>
        <q-input v-model = 'number' label="숫자입력"/>
        <div>{{util.comma(number)}}</div>

        <q-space class="q-my-md" style="border-bottom: 2px solid green"/>

        <div>문자열 줄이기</div>
        <div class="flex">
          <q-input v-model = 'str' label="문자입력"/>
          <q-input v-model = 'len' label="길이입력"/>
        </div>
        <div>{{len == 0 ? '길이를 입력하세요':util.strSummary(str,len)}}</div>

        <q-space class="q-my-md" style="border-bottom: 2px solid green"/>

        <div>안내 메시지</div>
        <q-input v-model = 'message' label="공지 입력"/>
        <q-btn label="노티" @click="util.notify(message,'info'),message=''"/>

        <q-space class="q-my-md" style="border-bottom: 2px solid green"/>

        <div>페이지 이동</div>
        <q-input v-model = 'to' label="이동할 페이지"/>
        <q-btn label="이동" @click="util.goTo(to),message=''"/>

        <q-space class="q-my-md" style="border-bottom: 2px solid green"/>

        <q-btn label="서버연결" @click="sample2()"/>
        <div>{{xx}}</div>
        <q-btn label="쿼링" @click="querying()"/>
      </section>
    </q-page>
</template>

<script>
  import {mapGetters,mapActions,mapMutations,mapState} from 'vuex';
  import {LocalStorage} from 'quasar';
  import {myUtil} from "boot/myUtil";

  export default {
    name: "TEST",
    computed :{
      ...mapGetters(['getLayout'])
    },
    data(){
      return {
        util :new myUtil(this),
        number : '',
        str:'',
        len:0,
        message:'',
        to:'',
        query :{},
        xx :{},
        xxx :'',
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions(['sample','addQuery']),
      sample2(){
        let self = this;
        this.sample({
          onSuccess :(res) =>{
            console.log(res)
            self.xx = res.data;
          },
          onFail :(error) =>{

          }
        }
        )
      },
      querying(){
        this.xxx = this.addQuery({name :"naa",id:5,nums :[1,2,3],maps :{a:1,b:2}});
        console.log(this.xxx);
      }
    },

    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
    },
    beforeMount() {
      this.getLayout.bottomFooter = true;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
