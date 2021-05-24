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
      {path: '/board', name: 'board', component: () => import('pages/Board.vue')},
      {path: '/boardlist', name: 'boardlist', component: () => import('pages/BoardList.vue')},
      {path: '/recipe', name: 'recipe', component: () => import('pages/Recipe.vue')},
      //내 정보
      {path: '/my', name: 'my', component: () => import('pages/MyInfo.vue')},
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
