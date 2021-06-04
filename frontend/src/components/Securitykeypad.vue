<!-- fixme #707-->
<template>
  <div class="bg-white" :class="'keypad-dialog ' + animation">
    <div class="keypad-container">
      <template v-for="n in shuffle()">
        <div :key="n" class="keypad-flex  keypad-class">
          <q-btn flat class="keypad" v-if="n == 10 && onReset" @click="onReset">
            <div class="keypad-center">
              <strong class="keypad-delete">&copy;</strong>
            </div>
          </q-btn>
          <q-btn
            class="keypad"
            flat
            :ripple="true"
            v-if="n != 10 && n != 12"
            @click="onInput(n == 11 ? 0 : n);"
          >
            <div v-if="n < 10" class="keypad-center text-weight-bold text-black">{{ n }}</div>
            <div v-if="n == 11" class="keypad-center text-weight-bold text-black">0</div>
          </q-btn>
          <q-btn class="keypad" flat v-if="n == 12 && onDelete" @click="onDelete(n);">
            <div v-if="n == 12" class="keypad-center">
              <strong class="keypad-delete">&laquo;</strong>
            </div>
          </q-btn>
        </div>
      </template>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Securitypad",
    props: {
      keypadClass: { type: String, default: "keypad-class", required: false },
      onInput: { type: Function, required: true },
      onDelete: { type: Function, required: false },
      onReset: { type: Function, required: false }
    },
    data(){
      return{
        n: 0,
        animation: "keyapd-hide,",
        randomList : [1,2,3,4,5,6,7,8,9,10,11,12],
      }
    },

    methods: {

      shuffle() {
        let array = [1,2,3,4,5,6,7,8,9,11];
        for (let i = array.length - 1; i > 0; i--) {
          let j = Math.floor(Math.random() * (i + 1));
          [array[i], array[j]] = [array[j], array[i]];
        }
        if(array.length <11){
          array.splice(-1, 0, 10);
          array.splice(11, 0, 12);
        }
        // array.splice(array.length - 1, 0, 12);
        // console.log(array)
        // this.randomList = array;
        return array;
      },

    },
    watch: {
      show() {
        this.animation === "slideInUp"
          ? (this.animation = "slideOutDown")
          : (this.animation = "slideInUp");
      }
    },
    mounted() {
      this.show ? (this.animation = "slideInUp") : (this.animation = "hide");
    }
  };
</script>

<style>

  .keypad-class {
    color: #888;
    background: #fafafa;
    border: 0.004rem solid #eaeaea;
  }

  .keypad-container {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-flex: 1;
    -ms-flex: 1 1 auto;
    flex: 1 1 auto;
    -ms-flex-wrap: wrap;
    flex-wrap: wrap;
    min-width: 0;
    flex-direction: row;
  }

  .keypad-flex {
    flex-basis: 33.3%;
    -webkit-box-flex: 0;
    -ms-flex-positive: 0;
    flex-grow: 0;
    max-width: 33.3%;
    min-height: 4rem;
  }

  .keypad {
    width: 100%;
    height: 100%;
    text-align: center;
    vertical-align: center;
    margin: 0 auto;
  }

  .keypad-center {
    position: relative;
    /*top: 50%;*/
    /*transform: translateY(-50%);*/
    font-size: 1.3rem;
  }

  .keypad-delete {
    font-size: 1.5rem;
  }

  @-webkit-keyframes slideInUp {
    0% {
      -webkit-transform: translateY(100%);
      transform: translateY(100%);
      visibility: visible;
    }
    100% {
      -webkit-transform: translateY(0);
      transform: translateY(0);
    }
  }
  @keyframes slideInUp {
    0% {
      -webkit-transform: translateY(100%);
      transform: translateY(100%);
      visibility: visible;
    }
    100% {
      -webkit-transform: translateY(0);
      transform: translateY(0);
    }
  }

  @-webkit-keyframes slideOutDown {
    0% {
      -webkit-transform: translateY(0);
      transform: translateY(0);
    }
    100% {
      visibility: hidden;
      -webkit-transform: translateY(100%);
      transform: translateY(100%);
    }
  }
  @keyframes slideOutDown {
    0% {
      -webkit-transform: translateY(0);
      transform: translateY(0);
    }
    100% {
      visibility: hidden;
      -webkit-transform: translateY(100%);
      transform: translateY(100%);
    }
  }
</style>
