export class myUtil {
  constructor(vue) {
    this.vue = vue;
  }

  comma =(num) => {
  let regexp = /\B(?=(\d{3})+(?!\d))/g;
  let trim = /(\s*)/g;
  if(num.toString().startsWith('0') && !num.toString().startsWith('0.') ){
    num = num.substr(1,num.length)
  }
  num = num.toString().replace(regexp, ',');
  return num.replace(trim,"");
  }

  goTo = (to, query) =>{
    this.vue.$router.push({path: to,query : query})
  }

  getQuery = () =>{
    return this.vue.$router.history.current.query;
  }

  strSummary = (str, len) =>{
    str = str.toString();

    if(len<=0) return '';
    if(str.length < len) return str;

    return str.substr(0,len)+"...";
  }

  notify = (message,color) =>{
    this.vue.$q.notify({message : message,color:color})
  }
  dot = (len) =>{
    let str = '';
    let size = len/3;
    for (let i=0; i<size; i++) {
      str +='.'
    }
    return str;
  }
  category = (str) =>{
    str = str.toString().toLocaleLowerCase();
    switch (str) {
      case 'korea' :
        str = 0
        break;
      case 'japan' :
        str = 1
        break;
      case 'china' :
        str = 2
        break;
      case 'western' :
        str = 4
        break;

      case 'etc' :
        str = 0
        break;
      case 'fry_stir' :
        str = 1
        break;
      case 'fry_deep' :
        str = 2
        break;
      case 'grill' :
        str = 3
        break;
      case 'steam' :
        str = 4
        break;
      case 'soup' :
        str = 5
        break;
    }
    return parseInt(str);
  }
}
