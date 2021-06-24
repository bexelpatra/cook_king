const routes = [
  {
    path: '/',
    component: () => import('layouts/IntroLayout.vue'),
    children: [
      {path: '', name: 'intro', component: () => import('pages/Intro/Intro.vue')},
    ]
  },
  {
    path: '',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      {path: '/main', name: 'main', component: () => import('pages/Main.vue')},
      //게시물
      {path: '/content', name: 'content', component: () => import('pages/Content.vue')},
      //게시물 작성
      {path: '/addcontent', name: 'addcontent', component: () => import('pages/AddContent.vue')},
      // 게시물 수정
      {path: '/updatecontent', name: 'updatecontent', component: () => import('pages/UpdateContent.vue')},
      //레시피 리스트
      {path: '/boardlist', name: 'boardlist', component: () => import('pages/BoardList.vue')},
      //즐겨찾기
      {path: '/bookmark', name: 'bookmark', component: () => import('pages/Bookmark.vue')},

      //myUtil 샘플
      {path: '/test', name: 'test', component: () => import('pages/TEST.vue')},
      {path: '/test2', name: 'test2', component: () => import('pages/TEST2.vue')},

      //fixme User
      //Login
      {path: '/login', name: 'login', component: () => import('pages/Login.vue')},
      //회원가입
      {path: '/signup', name: 'signup', component: () => import('pages/SignUp.vue')},
      //비밀번호 찾기
      {path: '/findpw', name: 'findpw', component: () => import('pages/FindPW.vue')},
      //내 정보
      {path: '/myinfo', name: 'myinfo', component: () => import('pages/MyInfo.vue')},

      //fixme Settings
      //핀
      {path: '/security', name: 'security', component: () => import('pages/settings/Security.vue')},
      //핀 등록
      {path: '/pinregistration', name: 'pinregistration', component: () => import('pages/settings/PinRegistration.vue')},
      //핀/생체보안 설정
      {path: '/personer', name: 'personer', component: () => import('pages/settings/Personal.vue')},
      //내 게시물
      {path: '/mycontent', name: 'mycontent', component: () => import('pages/settings/MyConTent.vue')},
      //코인
      {path: '/cookcoin', name: 'cookcoin', component: () => import('pages/settings/Cook_Coin.vue')},
      //내지갑
      {path: '/mywallet', name: 'mywallet', component: () => import('pages/settings/MyWallet.vue')},
      //선물
      {path: '/present', name: 'present', component: () => import('pages/settings/Present.vue')},
      //코인리스트
      {path: '/coinlist', name: 'coinlist', component: () => import('pages/settings/CoinList.vue')},
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/system/Error404.vue')
  }
]

export default routes
