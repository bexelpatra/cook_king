import Vue from 'vue'
import VueRouter from 'vue-router'

import routes from './routes'
import {LocalStorage} from 'quasar'

Vue.use(VueRouter)

/*
 * If not building with SSR mode, you can
 * directly export the Router instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Router instance.
 */

export default function (/* { store, ssrContext } */) {

  // LocalStorage 저장 데이터 맵핑
  const data = {
    userName:     LocalStorage.getItem("US_NA"),
    userPhone:    LocalStorage.getItem("US_PN"),
    userToken:    LocalStorage.getItem("US_TK"),
    pushToken:    LocalStorage.getItem("US_PT"),
    userId:       LocalStorage.getItem("US_ID"),
    userPwd:      LocalStorage.getItem("US_PW"),
  };

  let appVersion = '';

  const Router = new VueRouter({
    scrollBehavior: () => ({ x: 0, y: 0 }),
    routes,

    // Leave these as they are and change in quasar.conf.js instead!
    // quasar.conf.js -> build -> vueRouterMode
    // quasar.conf.js -> build -> publicPath
    mode: process.env.VUE_ROUTER_MODE,
    base: process.env.VUE_ROUTER_BASE
  })


  // /**
  //  * Router history 변경 시작전 호출
  //  */
  // Router.beforeEach((to, from, next) => {
  //   // const homePage = to.matched.some(record => record.meta.homeRequires);
  //   // const requiresAuth = to.matched.some(record => record.meta.nonRequiresAuth);
  //   // const isLoginPage = to.matched.some(record => record.meta.loginPage);
  //   // const isAuthenticated = LocalStorage.get.item("auth");
  //
  //   // 모바일[cordova]일경우
  //   if (window.cordova) {
  //     // alert("router index : " +  from.name + " => " + to.name);
  //     // fixme : #100 -  앱버전 확인을 위한 appVersion확인 ex) 1.0.1
  //     cordova.getAppVersion.getVersionNumber(function (version) {
  //       appVersion = version;
  //       LocalStorage.set("US_VS", version);
  //     });
  //   }
  //
  // });
  //
  /**
   * Router history 변경후 호출
   * */
  Router.afterEach((to, from)=> {

  });

  return Router
}
