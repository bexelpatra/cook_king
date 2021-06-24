<!--fixme 상세정보-->
<template>
  <div class="q-pa-md">
    <q-table
      title="com.example.demo.chain.Transaction"
      :rows="rows"
      :columns="columns"
      row-key="name"
    />
  </div>
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
        columns : [
          // {
          //   name: 'name',
          //   required: true,
          //   label: 'Dessert (100g serving)',
          //   align: 'left',
          //   field: row => row.name,
          //   format: val => `${val}`,
          //   sortable: true
          // },
          { name: 'transaction_id', align: 'center', label: 'transaction_id', field: 'transaction_id', sortable: true },
          { name: 'from', label: 'from', field: 'from', sortable: true },
          { name: 'to', label: 'to', field: 'to' },
          { name: 'time', label: 'time', field: 'time' },
          { name: 'amount', label: 'amount', field: 'amount' },
          // { name: 'iron', label: 'Iron (%)', field: 'iron', sortable: true, sort: (a, b) => parseInt(a, 10) - parseInt(b, 10) }
        ],
        rows : [
          {
            name: 'Frozen Yogurt',
            from: 159,
            to: 24,
            time: 4.0,
            amount: 87,
          },
          {
            name: 'Frozen Yogurt',
            from: 159,
            to: 24,
            time: 4.0,
            amount: 87,
          },
          {
            name: 'Frozen Yogurt',
            from: 159,
            to: 24,
            time: 4.0,
            amount: 87,
          },
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
