import axios from "axios";

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

export const sample = (state, args) => {
  axios({
      method :'get',
      url : HOST+'test/test1',
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

//===================================================================================================================
