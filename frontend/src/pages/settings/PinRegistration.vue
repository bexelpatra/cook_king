<template>
  <q-page class="bg-white">
    <div class="q-mt-sm absolute-top text-weight-bold flex flex-center" style="font-size: 2rem">PIN 등록/변경</div>
    <q-btn
      class="q-pa-sm absolute-top-right"
      icon-right="close"
      flat
      dense
      size="lg"
      @click="pageMain"
    />

    <div class="absolute-bottom">
      <div v-if="Password" class="full-width justify-center items-center">
        <div class="q-mb-md text-weight-bold flex flex-center" style="font-size: 1.2rem">보안 비밀번호 6자리를 입력해주세요.</div>
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
                  label="다음"
                  flat
                  dense
                  size="xl"
                  @click=""
                />
              </q-footer>
            </template>
          </q-input>
        </div>
      </div>

      <div v-else class="full-width justify-center items-center">
        <div class="q-mb-md text-weight-bold flex flex-center text-blue" style="font-size: 1.2rem">보안 비밀번호 확인</div>
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
                  label="등록하기"
                  flat
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
  </q-page>
</template>

<script>
  import {LocalStorage} from 'quasar';
  import {mapGetters,mapActions,mapMutations} from "vuex";
  import Securitykeypad from "../../components/Securitykeypad";

  export default {
    name : "PinRegistration",
    computed : {
      ...mapGetters(['getLayout'])
    },
    components: {
      Securitykeypad
    },
    data(){
      return{
        //Pin등록 data
        Password: true,
        isPwd: true,
        password: '',
        number: '',
        nextNumber: '',
        maxLength: 6,
        showKeypad: true,

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

      /** Click 구간 */
      pageMain() {
        this.$router.push('main');
      }
    },
    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
    },
    beforeMount() {
      this.getLayout.headerLayout = false;
      this.getLayout.title = "";
      this.getLayout.backbotton = false;
      this.getLayout.bookmarkbtn = false;
      this.getLayout.bottomFooter = false;
    },
    mounted() {},
    beforeUpdate() {},
    updated() {},
    beforeDestroy() {},
    destroyed() {}
  }
</script>

<style scoped>

</style>
