<!--fixme 상세정보-->
<template>
  <q-page>
    <q-table
      title="트랜잭션"
      :data="rows"
      :columns="columns"
      row-key="name"
    />
    <q-btn @click="getTransactions" class="full-width" label="더보기"/>
  </q-page>
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
          {name: 'Transacion_Id', required: true, label: 'Id', align: 'left', field: row => row.name, format: val => `${val}`, sortable: true},
          { name: 'from', align: 'left', label: 'Sender', field: 'from', sortable: true },
          { name: 'to',align: 'left', label: 'Receiver', field: 'to', sortable: true },
          { name: 'value', label: 'Value', field: 'amount' },
          // { name: 'time', label: 'Time', field: 'protein' },
        ],
        rows: [
          // {name: 'Frozen Yogurt', from: 159, to: 6.0, amount: 24},
        ]
      }
    },
    methods:{
      ...mapMutations([]),
      ...mapActions(['fetchServer']),
      getTransactions(){
        let self = this;
        this.fetchServer({path : 'chain/transaction'})
        .then(result =>{
          console.log(result);
          let list = [];
          if(result.status ==200){
            list = result.transactions;
            list.filter(value =>self.rows.push({name : value.transactionId =='0'?"Genesis":value.transactionId,from : value.f,to:value.t,amount : value.value}) )
          }
        })
        .catch(reason => {console.log(reason)})
      },
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
