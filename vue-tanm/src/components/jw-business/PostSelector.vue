<template>
  <jw-tree-selector v-model="model" :size="size" :disabled="disabled" :selectorOptions="options"
                    @on-change="onChanged">
  </jw-tree-selector>
</template>


<script>
  export default {
    name: 'jwPostSelector',
    props: {
      value: {
        type: Array,
        default () {
          return null
        }
      },
      disabled: {type: Boolean, default: false},
      size: {type: String, default: null},
      selectorOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        model: null,
        options: {
          multiple: true,
          cacheKey: 'postList',
          idFiled: 'post_id',
          url: '/api/platform/index/selector/posts',
          isItemDisabled (post) {
            return post.post_id <= 0
          }
        }
      }
    },
    watch: {
      value (newval, oldval) {
        this._setModel(newval)
      }
    },
    created () {
      this.$lodash.merge(this.options, this.selectorOptions)
      this._setModel(this.value)
    },
    methods: {
      _setModel (val) {
        if (val) {
          this.model = val
          return
        }

        this.model = null
      },
      onChanged (val, selectedItems) {
        this.$emit('input', this.model)
        this.$emit('on-change', this.model, selectedItems)
      }
    }
  }
</script>
