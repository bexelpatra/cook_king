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
