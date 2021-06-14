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
        @input="fileSelect"
        type="file"
      />
      <div v-if="imageName[0] != null && imageName[0].dataUrl!=undefined" class="full-width">
        <q-img style="height: 50vw;" :src="imageName[0] != null && imageName[0].dataUrl == undefined ? '': imageName[0].dataUrl"></q-img>
      </div>
    </section>

    <q-separator style="height: 3px"/>

    <!--fixme 대분류 중분류 소분류 -->
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
      <!--      <q-select-->
      <!--        dense-->
      <!--        label="소분류"-->
      <!--        class="q-pl-md col"-->
      <!--        style="width: 28vw"-->
      <!--        borderless-->
      <!--        v-model="Threeselect"-->
      <!--        :options="options3"-->
      <!--      />-->
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
          v-model="row.Volume"
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
      <div v-for="index in 1" :key="index">
        <div v-for="add in adds">
          <q-btn
            dense
            flat
            class="q-pr-sm full-width flex justify-end items-end"
            label="삭제"
            icon="close"
            @click="rcRemoveRow(add)"
          />
          <q-card class="q-my-sm">

            <div v-model="add.img" class="full-width" style="height: 50vw">
              <q-input
                dense
                style="width: 40vw; z-index: 1"
                class=" absolute-top-right bg-white"
                outlined
                filled
                @input="fileSelect"
                type="file"
              />
              <div v-if="imageName[0] != null && imageName[0].dataUrl!=undefined" class="full-width">
                <q-img style="height: 50vw;" :src="imageName[0] != null && imageName[0].dataUrl == undefined ? '': imageName[0].dataUrl"></q-img>
              </div>
            </div>

            <div class="q-mx-sm q-pb-sm text-left">
              <div class="q-my-sm text-h5 text-grey-7">{{ index ++}}.</div>
              <q-input
                v-model="contenttest"
                filled
                class="bg-grey-3"
                label="내용을 적어주세요."
                type="textarea"
              />
            </div>
          </q-card>

          <q-separator class="full-width" style="height: 3px"/>
        </div>
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

    <!--fixme 등록하기 버튼 -->
    <q-footer>
      <q-btn dense class="full-width text-h6" @click="Add">등록하기</q-btn>
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
    data(){
      return{
        imageName: [],
        //대분류 중분류 소분류
        oneselect: '',
        options1: [{val: 1, label: '한식'},{val: 2, label: '양식'},{val: 3, label: '일식'},{val: 4, label: '중식'}],
        twoselect:'',
        options2: [{val: 1, label: '찌개'},{val: 2, label: '볶음'},,{val: 3, label: '찜'},,{val: 4, label: '구이'}],
        // threeselect: '',
        // options3: [{}],

        title: '',
        description: '',
        ingredients: '',
        capacity: '',

        contenttest: '',
        rows:[{}],
        adds:[{}],


      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions([]),

      /**======================================
       * 이미지
       =======================================*/
      createImage(index, file) {
        let self = this;

        let reader = new FileReader();
        let formData = new FormData();
        formData.append('file', file);
        reader.onload = (e) => {
          const image = new Image();
          image.className = "img-item"; // 스타일 적용을 위해
          image.src = e.target.result;
          image.onload = imageEvent => {
            // 이미지가 로드가 되면! 리사이즈 함수가 실행되도록 합니다.
            let re = this.resizeImage(image, 512);
            let form = new FormData();
            form.append('file', this.dataURLtoBlob(re), "entLicense.jpeg");
            this.imageName.push({
              id: index,
              name: form.get('file').name,
              dataUrl: re,
              highlight: 1,
              default: 1
            });
            this.entLicense = form;
            console.log("name : ", form.get('file').name)
          };
        };
        reader.readAsDataURL(file);
      },
      resizeImage(orgImage, reSize) {
        // 최대 기준을 1280으로 잡음.
        let canvas = document.createElement("canvas");
        let max_size = reSize;
        let width = orgImage.width;
        let height = orgImage.height;

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
        canvas.getContext("2d").drawImage(orgImage, 0, 0, width, height);
        const dataUrl = canvas.toDataURL("image/jpeg");
        return dataUrl;
      },

      /** DATA_URL 정보를 Blob File로 변환 **/
      dataURLtoBlob(dataurl) {
        let arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1], bstr = atob(arr[1]), n = bstr.length,
          u8arr = new Uint8Array(n);
        while (n--) {
          u8arr[n] = bstr.charCodeAt(n);
        }

        return new Blob([u8arr], {type: mime});
      },

      fileSelect(files) {
        let self = this;
        this.imageName = [];
        //todo : ftp에저장, 성공여부에따라 signUp api 호출

        files.forEach((data, index) => {
          this.createImage(index, data);
        });
        console.log(this.imageName);
      },

      /** 재료 행 추가, 제거 */
      addRow(){this.rows.push({foodname:"",Volume:""})},
      removeRow(row){this.rows.splice(row, 1);},

      /** 레시피 행 추가, 제거 */
      rcRow(){this.adds.push({img:""})},
      rcRemoveRow(add){this.adds.splice(add, 1);},

      /** 등록하기  */
      Add(){
        if (this.imageName == null || this.imageName == undefined || this.imageName == ""){
          this.$q.notify({
            message : "이미지가 없습니다.",
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
        } else if (this.rows){
          if (this.rows[0].foodname == null || this.rows[0].foodname == undefined || this.rows[0].foodname == ""){
            this.$q.notify({
              message : "재료를 적어주세요.",
              type : "negative"
            })
            return;
          } else if (this.rows[0].Volume == null || this.rows[0].Volume == undefined || this.rows[0].Volume == ""){
            this.$q.notify({
              message : "용량을 적어주세요.",
              type : "negative"
            })
            return;
          }
        }
      }
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
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>
