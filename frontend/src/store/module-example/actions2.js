import axios from 'axios'
import { LocalStorage } from 'quasar';

const HOST = ""; // 서버

const URL = (path , param) =>{
  let url = HOST + path;
  if(param){
    Object.keys(param).map(value => {

    })
  }
  return
}
export function someAction (/* context */) {
}
