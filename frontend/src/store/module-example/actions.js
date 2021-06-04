import axios from "axios";

// const HOST = "http://localhost:8081/"; // ngrok주소를 사용할때는 https로 사용합니다.
const HOST = "https://6b1cff00811a.ngrok.io/"; //ngrok http {{port}}
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
