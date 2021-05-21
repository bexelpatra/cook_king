<template>
  <q-page class="bg-white q-pa-md">
    <div>
      ddd
    </div>
  </q-page>
</template>

<script>
  import {mapActions,mapMutations,mapGetters,mapState} from 'vuex';
  import {LocalStorage} from 'quasar';
export default {
  name: 'Main',
  computed:{...mapGetters(['getLayout'])},
  data(){
    return {

    }
  },
  methods : {
    ...mapMutations([]),
    ...mapActions([]),
  },
  beforeCreate() {
  },
  created() {
  },
  beforeMount() {
    this.getLayout.title = 'test1';
    this.getLayout.menuList = false;
  },
  mounted() {
  },
  beforeUpdate() {
  },
  updated() {
  },
  beforeDestroy() {
  },
  destroyed() {
  },
}
</script>
