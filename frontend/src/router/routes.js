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
      {path: '/board', name: 'board', component: () => import('pages/Content.vue')},
      {path: '/JaisungTest', name: 'JaisungTest', component: () => import('pages/JaisungTest.vue')},
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
