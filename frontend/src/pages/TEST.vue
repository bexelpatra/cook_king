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

        <q-space class="q-my-md" style="border-bottom: 2px solid green"/>

        <q-btn label="서버연결" @click="nong123()"/>
        <div>{{nong.nong}}{{nong.number}}</div>

        <q-btn label="아싱크 앤 아웨이트" @click="asyncTestServer"/>
        <q-btn label="요거슨 페칭" @click="fetchingTest"/>
        <q-btn label="test7" @click="test7"/>
        <q-btn label="test8" @click="test8"/>

        <div>
          <q-btn label="test9" @click="test9"/>
          <q-btn label="test10" @click="test10"/>
          <div v-for="n in 3">
            <q-input
              dense
              style="width: 40vw; z-index: 1"
              outlined
              filled
              v-model="form.imgs[n]"
              @change="addImg"
              type="file"
            />
          </div>
        </div>
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
        nong: {},
        img :'',
        form :{
          imgs:[]
        }
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions(['sample','test123','asyncTest','fetchServer']),
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
      addImg(e){
        console.log(e.target.results)
        console.log(e.target.result)
      },
      nong123(){
        let self = this;
        this.test123({
          onSuccess : (res) =>{
            console.log(res);
            self.nong = res.data;
          },
          onFail :(error) =>{

          }
        })
      },
      asyncTestServer(){
        // this.asyncTest({name : 'name',onSuccess(),onFail()})
      },
      async fetchingTest(){
        this.fetchServer({path : 'test/test2',method : 'post',body :{id : 1,email : 'anananan'},})
        .then(value => console.log(value))
        .catch(reason => console.log(reason))

      },
      test7(){
        this.fetchServer({path : "test/test7",method:'post',body: {}})
          .then(value => console.log(value))
      },
       async test8(){
        let param ='info';

         await this.fetchServer({path : "test/test10",method:'post',param:{param :'warning'},body :{}})
        .then(value => param = value.param );

        this.util.notify("나와라 얖",param)
         setTimeout(function () {
          console.log('타임아웃안쪽에',param);
          console.log(param);
        },1000)

         console.log('타임아웃바깥',param);

      },
      test9(){
        console.log(this.img);
        console.log(this.img[0]);
        let form = new FormData();

        // form.append("files",new Blob(this.img))
        form.append("multipart",this.img[0])
        // this.fetchServer({path : 'test/file1',method :'post',body :{files : this.img[0]}})
        // this.fetchServer({path : 'test/file2',method :'post',body :{files : new Blob(this.img).arrayBuffer()}})
        // this.fetchServer({path : 'test/file2',method :'post',body :{files : filestr}})
        // this.fetchServer({path : 'test/file3',method :'post',body : form })
        // this.fetchServer({path : 'test/file3',method :'post',body :form,header :{"Content-Type":"multipart/form-data"}})
        // this.fetchServer({path : 'test/file1',method :'post',body :{multipartFile : this.img[0]}})
        let init = {
          body : form,
          headers :{ "Content-Type":"multipart/form-data"}
        };
        fetch("http://localhost:8081/test/file2",init)
      },
      test10 () {
        let form = new FormData();
        this.form.imgs.forEach((value, index) => {
          console.log(value)
          form.append("multipartFile",value[0])
          form.append("name",index)
        })

        let init = {
          body : form,
          // headers :{ "Content-Type":"multipart/form-data"},
          // headers :{ "Content-Type":"application/json"},
          method : 'post'
        };

        fetch("http://localhost:8081/test/file3",init)
        // fetch("http://localhost:8081/test/file3",init)
      },
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
