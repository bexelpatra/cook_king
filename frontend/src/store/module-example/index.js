import state from './state'
import * as getters from './getters'
import * as mutations from './mutations'
import * as actions from './actions'

export default {
  // namespaced: true,
  namespaced: false, // 뭔지 알아보자
  getters,
  mutations,
  actions,
  state
}
