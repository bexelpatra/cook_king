export const getLayout = (state) => {
  return state.PageLayout;
}
export const isLogIn = (state) => {
  return state.logIn;
}
/**
 * @Deprecated
 */
export const getFavorite = (state) => {
  return state.favorite;
}
/**
 * @Deprecated
 */
export const getMyRecipe = (state) => {
  return state.myRecipe;
}
export const getUser = (state) => {
  return state.userInfo;
}
