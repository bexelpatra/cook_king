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
        <q-btn @click="test11" label="왜 안보이냐 싯팔"/>
        <q-btn @click="test12" label="test12"/>
        </div>
<!--        <v-btn type="button" hidden @click="onClickImageUpload">이미지 업로드</v-btn>-->
        <q-input type="file" v-model="file" @change="onChangeImages"/>
        <q-img :src="imageUrl"
               :ratio="3/4"
        ></q-img>
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
        file : null,
        imageUrl: '',
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
      // onClickImageUpload() {
      //   this.$refs.imageInput.click();
      // },
      onChangeImages(e) {
        // const file = e.target.files[0];
        const file = this.file

        // this.imageUrl = window.URL.createObjectURL(file);
        this.imageUrl = window.URL.createObjectURL(new Blob(file))
        console.log(this.imageUrl)
      },
      resizeImage(orgImage, reSize) {
        // 최대 기준을 1280으로 잡음.
        let canvas = document.createElement("canvas");
        let max_size = reSize;
        // let width = orgImage.width;
        let width = 120;
        // let height = orgImage.height;
        let height = 120;

        if (width > height) {
          // 가로가 길 경우
          if (width > max_size) {
            height *= max_size / width;
            width = max_size;
          }
        } else {
          // 세로가 길 경우
          if (height > max_size) {
            width *= max_size / height;
            height = max_size;
          }
        }
        canvas.width = width;
        canvas.height = height;
        canvas.getContext("2d").getImageData(orgImage, 0, 0, width, height);
        const dataUrl = canvas.toDataURL("image/jpeg");
        // const dataUrl = window.URL.createObjectURL(canvas);
        return dataUrl;
      },
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
      test11(){
        // array를 parameter로 넘기기
          this.fetchServer({path : 'test/test11',param:{s:[12,3,4,6]},method : 'post'})
            .then(value => console.log(value))
      },
      test12(){
        this.fetchServer({path : 'test/test16',param:{s : "얖얖얖",},method : 'post'})
        .then(value => console.log(value))
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
