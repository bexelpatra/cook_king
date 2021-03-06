import axios from "axios";
import {myUtil} from "boot/myUtil";

// const HOST = "http://localhost:8081/"; // ngrok주소를 사용할때는 https로 사용합니다.
const HOST = "https://fbc6ec36e26a.ngrok.io/"; //ngrok http {{port}}
                                            // ex) ngrok http 8080
export function someAction (/* context */) {

}
const COMMON_HEADER = {"Access-Control-Allow-Origin": "*",
  "Access-Control-Allow-Methods": "GET, POST, PATCH, PUT, DELETE, OPTIONS",
  "Access-Control-Allow-Headers": "Origin, Content-Type, X-Auth-Token"};
const addQuery = (param) => {
  if(param == null) return '';
  let str = "?"
  str += Object.keys(param).map(key => {
    return key +"="+ param[key];
  }).join("&");
  return str;
}

export const sample = (state, args) => {
  axios({
      method :'get',
      url : HOST+'test/test1',
    headers: {
      'Content-Type': 'application/json',
      ...COMMON_HEADER
    },
  })
    .then((res)=>{
    args.onSuccess(res);
  }).catch((error)=>{
    args.onFail(error);
  });
};

export const test123 = (state, args) => {
  axios.get('http://localhost:8081/test/test123?nong=호로롤&number=1818')
    .then((res)=>{
      args.onSuccess(res);
    }).catch((error)=>{
      args.onFail(error);
  });
};

export const asyncTest = (state, args) => {
  let get = axios.get('http://localhost:8081/test/test123?nong=호로롤&number=1818');
  console.log(get)
  args.onSuccess(get);
};
export const duplicateCheck = (status,args) =>{
  axios.get(HOST+'user/mail-duplication'+addQuery({email:args.email}))
    .then(value => {
      console.log(value)
      args.onSuccess(value);
    })
    .catch(reason => args.onFail(reason))
}
export const sendCertificate = (status,args) =>{
  axios.post(HOST+'user/mail-certification'+addQuery({email:args.email}),{})
    .then(value => args.onSuccess(value))
    .catch(reason => args.onFail(reason))
}
/**
 * 메일 본인인증하기
 * @param status
 * @param args {emial : 이메일, number : 6자리 인증번호}
 */
export const certificate = (status,args) =>{
  axios.get(HOST+'user/mail-certification'+addQuery({email:args.email, number : args.number}),)
    .then(value => args.onSuccess(value))
    .catch(reason => args.onFail(reason))
}
export const signUp = (status,args) =>{
  axios.post(HOST+'user',{email : args.email,password : args.password,})
    .then(value => args.onSuccess(value))
    .catch(reason => args.onFail(reason))
}

export const signIn = (status,args) =>{
  axios.get(HOST+'user',{email : args.email,password : args.password,})
    .then(value => args.onSuccess(value))
    .catch(reason => args.onFail(reason))
}

export async function getMapping(state,args) {
  const url = HOST +args.path + addQuery(args.param);
  const requestInit = {
    method : 'get',
    headers : {
      "Content-Type" : "application/json",
      ...args.header,
        // ...COMMON_HEADER
    },
  };
  console.log(requestInit);
  const response = await fetch(url,requestInit);
  const data = await response.json();
  Object.assign(data,{status:response.status});
  if(response.ok){
    return data;
  }else{
    throw Error(data);
  }
}
// {path : , method : , body : , param : ,}
export async function fetchServer(state,args) {
  if(args.method == 'get' || args.method == null){
    return getMapping(state,args);
  }
  let contentType = args.header == null|| args.header.Content_Type == null ? 'application/json' : args.header.Content_Type;
  let body = contentType.endsWith('json') ?JSON.stringify(args.body) : args.body;
  if(!body) body = {}
  const url = HOST +args.path + addQuery(args.param);
  const requestInit = {
    method : args.method,
    headers: {
      'Content-Type': contentType,
      'Accept': '*/*',
      ...COMMON_HEADER,
      ...args.header
    },
    body : body,
  };
  console.log(url);
  console.log(requestInit);
  const response = await fetch(url,requestInit);
  const data = await response.json();
  Object.assign(data,{status:response.status});
  if(response.ok){
    return data;
  }else{
    throw Error(data);
  }
}

export async function updateImage(state,args) {

  const url = HOST +args.path + addQuery(args.param);
  const requestInit = {
    method : args.method,
    // headers: {
    //   'Access-Control-Allow-Origin' :'',
    //   ...args.header
    // },
    body : args.body,
  };
  console.log(url);
  console.log(requestInit);
  const response = await fetch(url,requestInit);
  const data = await response.json();
  Object.assign(data,{status:response.status});
  if(response.ok){
    return data;
  }else{
    throw Error(data);
  }
}
export const requestMapping = (state, args) =>{
  axios.get(HOST+'user/mail-certification'+addQuery({email:args.email, number : args.number}),)
    .then(value => args.onSuccess(value))
    .catch(reason => args.onFail(reason))
}

/**
 * 사용자의 모든 정보를 업데이트 한다.
 * @param state
 * @param args -> token을 넘긴다.
 * 사용처 : intro, 글쓰기, 즐겨찾기추가시에 수정한다.
 */
export const userInfo = (state,args) => {

  fetchServer(state,{path :'user/user/'+args.token})
    .then(success =>{
      if(success.status == 200){
        state.state.userInfo = success.user;
        state.state.myRecipe = success.user.myRecipe.map(value => value.id);
        state.state.favorite = success.user.myFavoriteRecipe.map(value => value.id);
        state.state.isLogIn = true;
      }
    })
    .catch(reason => console.log(reason))
}

export const logInCheck = (state, args) =>{
  let flag = false;
  fetchServer({path : 'user/user',param:{t:args.token, type : 0}})
    .then(success => {
      if(success.status==200){
        userInfo(state,{token : args.token})
        flag = true;
      }else{

      }
    })
    .catch(reason => {

    })
  return flag;
}
export  const c8= (state, args) =>{
  console.log(args.vue)
  args.vue.$router.push('main');
}
//===================================================================================================================

