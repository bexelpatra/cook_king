<!--fixme 상세정보-->
<template>
    <q-table
      title="Treats"
      :data="rows"
      :columns="columns"
      row-key="name"
    />
</template>


<script>
  import {mapGetters,mapMutations,mapActions} from 'vuex';
  import {LocalStorage} from 'quasar';
  import {myUtil} from 'boot/myUtil'

  export default {
    name: 'CoinList',
    computed:{
      ...mapGetters(['getLayout'])
    },
    data(){
      return{
        util : new myUtil(this),
        columns: [
          {name: 'name', required: true, label: 'Dessert (100g serving)', align: 'left', field: row => row.name, format: val => `${val}`, sortable: true},
          { name: 'calories', align: 'center', label: 'Calories', field: 'calories', sortable: true },
          { name: 'fat', label: 'Fat (g)', field: 'fat', sortable: true },
          { name: 'carbs', label: 'Carbs (g)', field: 'carbs' },
          { name: 'protein', label: 'Protein (g)', field: 'protein' },
          { name: 'sodium', label: 'Sodium (mg)', field: 'sodium' },
        ],
        rows: [
          {name: 'Frozen Yogurt', calories: 159, fat: 6.0, carbs: 24, protein: 4.0, sodium: 87, calcium: '14%', iron: '1%'},
        ]
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions([]),

      /**======================================
       * 클릭 이벤트
       ========================================*/
    },

    beforeCreate() {},
    created() {
      window.onpopstate = ()=>{}
    },
    beforeMount() {
      this.getLayout.headerLayout = true;
      this.getLayout.title = "상세 내역";
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
