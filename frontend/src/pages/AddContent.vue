<!--fixme 레시피 게시물 작성-->
<template>
  <q-page class="bg-grey-2">
    <!--fixme 메인 이미지 -->
    <section class="full-width bg-white" style="height: 50vw">
      <q-input
        dense
        style="width: 40vw; z-index: 1"
        class=" absolute-top-right bg-white"
        outlined
        filled
        v-model="thumb"
        @input="createTitleImg(thumb)"
        type="file"
      />
      <div v-if="titleImage != null && titleImage.dataUrl!=undefined" class="full-width">
        <q-img style="height: 50vw;" :src="titleImage != null && titleImage.dataUrl == undefined ? '': titleImage.dataUrl"></q-img>
      </div>
    </section>

    <q-separator style="height: 3px"/>

    <!--fixme 1차분류 2차분류 -->
    <section class="row">
      <q-select
        label="1차 분류"
        class="q-px-md col"
        borderless
        v-model="oneselect"
        :options="options1"
      />
      <span style="border: solid 1px rgba(128,128,128,0.37)"/>
      <q-select
        label="2차 분류"
        class="q-px-md col"
        borderless
        v-model="twoselect"
        :options="options2"
      />
    </section>

    <q-separator style="height: 3px"/>

    <!--fixme 타이틀 -->
    <section class="q-px-md q-py-sm bg-white">
      <div class="q-mb-xs text-h6 text-weight-bold">타이틀</div>
      <q-input
        v-model="title"
        dense
        standout
        outlined
        style="border-radius: 30px"
      />
    </section>

    <!--fixme 한줄 설명 -->
    <section class="q-px-md q-py-sm bg-white">
      <div class="q-mb-xs text-h6 text-weight-bold">한줄 설명</div>
      <q-input
        v-model="description"
        dense
        standout
        outlined
        style="border-radius: 30px"
      />
    </section>

    <!--fixme 재료 -->
    <section class="q-px-md q-py-sm bg-white">
      <div class="q-mb-xs text-h6 text-weight-bold">재료/용량</div>
      <div class="row q-gutter-x-sm q-mt-sm" v-for="row in rows">
        <q-input
          v-model="row.foodname"
          class="col"
          dense
          standout
          outlined
          style="border-radius: 30px"
        />
        <q-input
          v-model="row.volume"
          class="col"
          dense
          standout
          outlined
          style="border-radius: 30px"
        />
        <q-btn
          dense
          label="삭제"
          class="text-weight-thin text-white bg-blue"
          @click="removeRow(row)"
        />
      </div>
      <div class="flex flex-center q-mb-md">
        <q-btn
          class="q-mt-lg"
          icon="add"
          @click="addRow"
          size="md"
          color="grey-7"
          text-color="white"
          dense label="추가하기"></q-btn>
      </div>
    </section>

    <q-separator style="height: 3px"/>

    <!--fixme 레시피 내용 -->
    <section class="q-pa-sm bg-white">
      <div class="q-my-xs text-h6 text-weight-bold">레시피</div>
      <div v-for="(add,index) in adds" :key="index">
        <q-btn
          dense
          flat
          class="q-pr-sm full-width flex justify-end items-end"
          label="삭제"
          icon="close"
          @click="rcRemoveRow(add)"
        />
        <q-card class="q-my-sm">

          <div class="full-width" style="height: 50vw">
            <q-input
              dense
              style="width: 40vw; z-index: 1"
              class=" absolute-top-right bg-white"
              outlined
              filled
              @input="createImg(add)"
              v-model="add.file"
              type="file"
            />
            <div v-if="add != null && add.dataUrl!=undefined" class="full-width">
              <q-img style="height: 50vw;" :src="add != null && add.dataUrl == undefined ? '': add.dataUrl"></q-img>
            </div>
          </div>

          <div class="q-mx-sm q-pb-sm text-left">
            <div class="q-my-sm text-h5 text-grey-7">{{ index+1 }}.</div>
            <q-input
              v-model="add.text"
              filled
              class="bg-grey-3"
              label="내용을 적어주세요."
              type="textarea"
            />
          </div>
        </q-card>

        <q-separator class="full-width" style="height: 3px"/>
      </div>
      <div class="q-pt-md q-pb-md flex flex-center">
        <q-btn
          icon="add"
          @click="rcRow"
          size="md"
          color="grey-7"
          text-color="white"
          dense
          label="추가하기"/>
      </div>
    </section>
<!--    <q-btn @click="test1">얖얖ㅇ퍄</q-btn>-->
    <!--fixme 등록하기 버튼 -->
    <q-footer>
      <q-btn dense class="full-width text-h6" @click="recipeChack">등록하기</q-btn>
    </q-footer>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';
  import {myUtil} from "boot/myUtil";

  export default {
    name: 'AddContent',
    computed:{
      ...mapGetters(['getLayout'])
    },
    watch:{

    },
    data(){
      return{

        util : new myUtil(this),
        recipe :{},
        //타이틀 이미지
        titleImage:[{dataUrl : '',order :0,text :'타이툴',file :null, kind : 2}],
        thumb :null,

        //1차 2차 분류
        oneselect: '',
        options1: [{val: 0, label: '한식'},{val: 1, label: '일식'},{val: 2, label: '중식'},{val: 3, label: '양식'}],
        twoselect:'',
        options2: [{val: 0, label: '기타'},{val: 1, label: '볶음'},{val: 2, label: '튀김'},{val: 3, label: '구이'},{val: 4, label: '찜'},{val: 5, label: '국물'},],

        //text input
        title: '',
        description: '',

        // 재료/용량
        rows:[{}],

        //레시피 섹션
        adds:[{dataUrl : '',order :1,text :'',file :null, kind :0}],
        order : 2,
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions(['updateImage','userInfo']),

      /**======================================
       * 이미지  Kind : 0번 = 레시피 이미지 , 2번 = 타이틀이미지
       =======================================*/
      createTitleImg(file){
        let dataUrl = window.URL.createObjectURL(new Blob(file));
        this.titleImage.dataUrl = dataUrl;
        this.titleImage.file = file;
        this.titleImage.order = 0;
        this.titleImage.kind = 2;

      },
      createImg(add){
        let dataUrl = window.URL.createObjectURL(new Blob(add.file));
        add.dataUrl = dataUrl;
        add.kind = 0;
      },

      /**======================================
       * 클릭 이벤트
       =======================================*/

      /** 재료 행 추가, 제거 */
      addRow(){this.rows.push({foodname:"",volume:""})},
      removeRow(row){this.rows.splice(row, 1);},

      /** 레시피 행 추가, 제거 */
      rcRow(){this.adds.push({order :this.order++, img:"",kind : 0})},
      rcRemoveRow(add){this.adds.splice(add, 1);},

      /** 등록하기  */
      recipeChack(){
        if (this.thumb == null || this.thumb == undefined || this.thumb == ""){
          this.$q.notify({
            message : "타이틀 이미지가 없습니다.",
            type : "negative"
          })
          return;
        } else if (this.oneselect == null || this.oneselect == undefined || this.oneselect == ""){
          this.$q.notify({
            message : "1차 분류를 선택해주세요.",
            type : "negative"
          })
          return;
        } else if (this.twoselect == null || this.twoselect == undefined || this.twoselect == "") {
          this.$q.notify({
            message: "2차 분류를 선택해주세요.",
            type: "negative"
          })
          return;
        } else if (this.title == null || this.title == undefined || this.title == ""){
          this.$q.notify({
            message : "타이틀을 적어주세요.",
            type : 'negative'
          })
          return;
        } else if (this.description == null || this.description == undefined || this.description == ""){
          this.$q.notify({
            message : "한줄 설명을 적어주세요.",
            type : "negative"
          })
          return;
        } else if (this.rows.filter(value =>( value.foodname == null||value.foodname == ''||value.volume == null || value.volume=='' || value.volume == undefined)).length>0){
          this.$q.notify({
            message : "재료/용량을 적어주세요.",
            type : "negative"
          })
          return;
        } else if (this.adds.filter(value => ( value.file == null || value.file == '' || value.text == null || value.text == '' || value.text == undefined)).length>0){
          this.$q.notify({
            message : "사진 또는 내용을 입력해주세요.",
            type : "negative"
          })
          return;
        }
        this.server();
      },

      /**=======================================
       * sever 통신구간
       =========================================*/

      server(){
        let form = new FormData();
        //타이틀이미지
        this.adds.sort((a, b) => a.order-b.order);
        form.append("file",this.titleImage.file[0])
        form.append("order",this.titleImage.order)
        form.append("text",this.titleImage.text)
        form.append("kind",this.titleImage.kind)
        //카테고리 1차 분류
        form.append("firstcategoryInt",this.oneselect.val)
        //카테고리 2차 분류
        form.append("secondcategoryInt",this.twoselect.val)
        //타이틀 제목
        form.append("title",this.title);
        //한줄 설명
        form.append("description",this.description);
        //재료  str을 이용하여 String으로 변경 (한줄 출력)
        let str = '';
        for (let row of this.rows) {
          str +=row.foodname +':' + row.volume+"#"
        }
        str = str.substring(0,str.length-1);
        form.append("stuffs",str)
        //레시피 이미지
        this.adds.forEach((add, index) => {
          form.append("file",add.file[0])
          form.append("order",index+1)
          form.append("text",add.text)
          form.append("kind",add.kind)
        })

        // 서버 통신 url
        this.updateImage({path : 'recipe/recipe',method :'post',param :{token :LocalStorage.getItem('t')},body :form})
          // then value 200 성공 코드
        .then(value =>{
          if(value.status==200){
            this.userInfo({token : LocalStorage.getItem('t')})
            this.$q.notify('성공적으로 등록 되었습니다.','info')
            this.util.goTo('main',{})
            //메인으로 가는 것보다 내 게시물로 가는 방향으로 이동
          }
        })
          //catch 실패 코드 200외 전부 처리
        .catch(reason => {

        })

      },
      // test1(){
      //   let str = '';
      //   for (let row of this.rows) {
      //     str +=row.foodname +':' + row.volume+"#"
      //   }
      //   str = str.substring(0,str.length-1);
      //   console.log(str)
      //   let str2 = str.split("#");
      //   console.log(str2)
      //   for (let string of str2) {
      //     let x = string.split(":")
      //     console.log(x[0])
      //     console.log(x[1])
      //   }
      // }

    },

    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.backbotton = true;
      this.getLayout.title = "레시피"
      this.getLayout.bookmarkbtn = false;
      this.getLayout.bottomFooter = false;
      this.getLayout.addcontent = false;

      this.recipe = this.util.getQuery().recipe;
      console.log(this.recipe)
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
