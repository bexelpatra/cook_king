import axios from "axios";

export function someAction (/* context */) {
}

export const sample = (state, args) => {
  axios.get('http://localhost:8081/test/test1?name=c8&number=4',)
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
