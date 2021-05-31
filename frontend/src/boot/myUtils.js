/**
 * 숫자에 ',' 찍어주기
 * @param num 숫자
 * @returns {string}
 */
exports.comma = (num) => {
  let regexp = /\B(?=(\d{3})+(?!\d))/g;
  return num.toString().replace(regexp, ',');
}
/**
 * 공지 띄우기
 * @param vue 현재 vue 페이지
 * @param message
 * @param color
 */
exports.notify = (vue,message,color) =>{
  vue.$q.notify({message : message,color:color});
}
/**
 *
 * @param vue 현재 vue 페이지
 * @param to 이동할 페이지
 * @param query {}, 파라미터
 */
exports.pageMove = (vue,to,query) =>{
  if(!to.toString().startsWith('/')) to ='/'+to;
  vue.$router.push({path : to, query : query})
}
/**
 * 쿼리로 받은 parater를 반환한다.
 * @param vue 현재 vue 페이지
 * @returns {*}
 */
exports.getQuery = (vue) =>{
  return vue.$router.history.current.query;
}