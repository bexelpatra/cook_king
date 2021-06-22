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
    let category ={};
    switch (str) {
      case 'KOREA' :
        category = {id : 0,name :'한식'}
        break;
      case 'JAPAN' :
        category = {id : 1,name :'일식'}
        break;
      case 'CHINA' :
        category = {id : 2,name :'중식'}
        break;
      case 'WESTER' :
        category = {id : 3,name :'양식'}
        break;

      case 'ETC' :
        category = {id : 0,name :'기타'}
        break;
      case 'FRY_STIR' :
        category = {id : 1,name :'볶음'}
        break;
      case 'fry_deep'.toUpperCase() :
        category = {id : 2,name :'튀김'}
        break;
      case 'GRILL' :
        category = {id : 3,name :'구이'}
        break;
      case 'STEAM' :
        category = {id : 4,name :'찜'}
        break;
      case 'SOUP' :
        category = {id : 5,name :'국물'}
        break;
    }
    return category;
  }

  dataURLtoFile(dataurl, fileName){

    let arr = dataurl.split(','),
    mime = arr[0].match(/:(.*?);/)[1],
    bstr = atob(arr[1]),
    n = bstr.length,
    u8arr = new Uint8Array(n);

    while(n--){
    u8arr[n] = bstr.charCodeAt(n);
   }

    return new File([u8arr], fileName, {type:mime});
  }
}
