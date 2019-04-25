/**
 * Created by 袁进勇 on 2019/04/09.
 */
export default {
  inject: ['lab360Component'],
  computed: {
    options () {
      return this.lab360Component.options
    },
    domain () {
      return this.lab360Component.domain
    }
  }
}
