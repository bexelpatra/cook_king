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
      //레시피 리스트
      {path: '/boardlist', name: 'boardlist', component: () => import('pages/BoardList.vue')},
      //레시피 작성
      {path: '/recipe', name: 'recipe', component: () => import('pages/Recipe.vue')},
      //즐겨찾기
      {path: '/bookmark', name: 'bookmark', component: () => import('pages/Bookmark.vue')},
      //내 정보
      {path: '/my', name: 'my', component: () => import('pages/MyInfo.vue')},
      //회원가입
      {path: '/signup', name: 'signup', component: () => import('pages/SignUp.vue')},
      //myUtil 샘플
      {path: '/test', name: 'test', component: () => import('pages/TEST.vue')},

      //fixme Settings
      //핀
      {path: '/security', name: 'security', component: () => import('pages/settings/Security.vue')},
      //핀 등록
      {path: '/pinregistration', name: 'pinregistration', component: () => import('pages/settings/PinRegistration.vue')},
      //핀/생체보안 설정
      {path: '/personer', name: 'personer', component: () => import('pages/settings/Personal.vue')},
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
