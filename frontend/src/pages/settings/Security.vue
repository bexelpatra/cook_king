<!--fixme 보안 -->
<template>
  <q-page class="bg-white q-pa-sm">
    <section>
      <div class="absolute-bottom">
        <div class="full-width justify-center items-center">
          <div class="q-mb-md text-weight-bold flex flex-center" style="font-size: 1.2rem">보안 비밀번호 6자리를 입력해주세요.<br>
          </div>
          <div class="bg-grey-3 flex flex-center text-weight-bold">
            <q-input
              class="full-width"
              style="font-size: 1.2rem"
              readonly
              filled
              :type="isPwd ? 'password' : 'text'"
              v-model="number"
            >
              <template v-slot:append>
                <q-icon
                  :name="isPwd ? 'visibility_off' : 'visibility'"
                  class="q-mr-md cursor-pointer"
                  @click="isPwd = !isPwd"
                />
                <q-footer class="bg-white full-width">
                  <q-btn
                    class="full-width text-grey-7 text-weight-bold bg-blue-2"
                    label="확인"
                    dense
                    size="xl"
                    @click=""
                  />
                </q-footer>
              </template>
            </q-input>
          </div>
        </div>

        <Securitykeypad
          :onInput="onInput"
          :onDelete="onDelete"
          :onReset="onReset"
          :show="showKeypad"
        />
      </div>
    </section>
  </q-page>
</template>

<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';
  import Securitykeypad from "../../components/Securitykeypad";

  export default {
    name: 'Security',
    computed:{
      ...mapGetters(['getLayout'])
    },
    components: {
      Securitykeypad
    },
    data(){
      return{
        //키패드 출력
        showKeypad: true,

        isPwd: true,
        password : '',

        number : "",
        maxLegth : 6,
        hasP : false,

      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions([]),

      /** KeyPad */
      onInput(key){
        console.log(key)
        this.number = (this.number + key).slice(0, this.maxLength);
        if(this.number.length == this.maxLength) {
        }
      },
      onDelete() {
        this.number = this.number.slice(0, this.number.length - 1);
      },
      onReset() {
        this.number = "";
      },

    },

    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.title = "핀 인증";
      this.getLayout.backbotton = true;
      this.getLayout.mainbackbotton = false;
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
