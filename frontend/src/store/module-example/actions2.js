import axios from 'axios'
import { LocalStorage } from 'quasar';

const HOST = "http://localhost:8081/"; // ngrok주소를 사용할때는 https로 사용합니다.
// const HOST = "https://00ce877b277b.ngrok.io/"; //ngrok http {{port}}
// ex) ngrok http 8080
export function someAction (/* context */) {
}

const addQuery = (param) => {
  let str = "?"
  str += Object.keys(param).map(key => {
    return key +"="+ param[key];
  }).join("&");
  return str;
}

export const checkDuplicate = (status,args) =>{
  axios.get(HOST+addQuery({email:args.email}))
    .then(value => {
      args.onSuccess(value);
    })
    .catch(reason => args.onFail(reason))
}
