import state from './state'
import * as getters from './getters'
import * as mutations from './mutations'
import * as actions from './actions'
import * as actions2 from './actions2'

export default {
  namespaced: false,
  getters,
  mutations,
  actions,
  state
}
