/**
 * 给批量录入委托单界面用的store
 */
import Vue from 'vue'


export default {
  state: {
    itemCategories: [],
    expandedItemCategoryIds: [],
    itemCategoriesLoading: false,
    itemCategoryMap: {},
    itemCategory: null,
    itemCategoryLoading: false,
    operation: '增加',
    defaultOrderDomain: {
      id: null,
      orderEntity: {
        id: null,
        batch_no: null,
        seq_no: null,
        no: null,
        item_id: null,
        item_name: null,
        sample_id: null,
        report_id: null,
        project_id: null,
        project_name: null,
        part: null,
        customer_id: null,
        customer_name: null,
        region_code: null,
        city_code: null,
        county_code: null,
        countyFullPath: null,
        street: null,
        address: null,
        longitude: null,
        latitude: null,
        zip_code: null,
        fax: null,
        telephone: null,
        consigner_name: null,
        consigner_date: null,
        raw_data_print_template_code: [],
        report_print_template_code: null,
        report_print_count: 3,
        report_plan_date: null,
        report_delivery_type: 101,
        fee: 0.00,
        paid_status: 101,
        is_cancel: 102,
        creator_id: null,
        created_time: null,
        attachment_ids: [],
        evaluation_group: {},
        method_list: [],
        other: {},
        status: 101,
        remark: null,
        params: [],
        rawDatas: []
      },
      sampleEntity: {
        id: null,
        seq_no: null,
        no: null,
        project_id: null,
        code: null,
        name: null,
        model: null,
        brand: null,
        manufacturer: null,
        batch_no: null,
        quantity: null,
        volume: null,
        curing_days: 0,
        storage_desc: '无特殊要求',
        secret_desc: '无',
        desc: null,
        deal_type: 101,
        deal_result: null,
        retain_name: null,
        retain_no: null,
        retain_date: null,
        retain_type: 101,
        retain_address: null,
        retain_longitude: null,
        retain_latitude: null,
        construction_company: null,
        witness_company: null,
        witness_name: null,
        witness_no: null,
        witness_mark_status: 101,
        receiving_name: null,
        receiving_date: null,
        receiving_type: 101,
        experiment_date: null,
        is_compliance: 101,
        attachment_ids: [],
        other: {},
        is_trash: 102,
        testing_status: 101,
        status: 101,
        remark: null
      },
      reportEntity: {
        id: null,
        seq_no: null,
        no: null,
        project_id: null,
        original_id: null,
        last_id: null,
        conclusion_type: 101,
        conclusion_desc: null,
        conclusion: null,
        other: {},
        is_trash: 102,
        status: 101,
        remark: null
      }
    },
    currentOrderDomain: null,
    orderDomainList: [],
    sequence: 1,
    submitLoading: false
  },
  getters: {
    getOrderDomainTitle: (state, getters) => () => {
      if (!state.itemCategory) {
        return '请先选择检测项目'
      }

      let title = state.operation + '委托单 - ' + state.itemCategory.code + ' ' + state.itemCategory.name
      if (state.currentOrderDomain && state.currentOrderDomain.id) {
        title += ' - ' + state.currentOrderDomain.id
      }
      return title
    },
    getOrderParamsByCodes: (state, getters) => (orderEntity, parameterCodes) => {
      let itemCategory = state.itemCategory
      let item_id = itemCategory.id
      return itemCategory.itemParameterList.filter(parameter => parameterCodes.findIndex(parameterCode => parameterCode === parameter.parameter_code) > -1).map(parameter => {
        let equipmentList = itemCategory.itemEquipmentListMap[parameter.parameter_code]
        if (!equipmentList) {
          equipmentList = []
          console && console.warn('未查找到检测项目' + item_id + '的试验参数' + parameter.parameter_code + '所配置的试验设备！')
        }
        return {
          id: null,
          order_id: null,
          order_raw_data_id: null,
          parameter_code: parameter.parameter_code,
          parameter_name: parameter.parameter_name,
          method_standard_no: parameter.method_standard_no[0],
          method_standard_list: parameter.method_standard_no,
          fee: parameter.fee,
          raw_data_code: parameter.raw_data_code,
          raw_data_name: parameter.raw_data_name,
          other: {},
          equipmentList: equipmentList
        }
      })
    },
    _getExpandedItemCategoryIds: (state, getters) => (nodes) => {
      let expandedItemCategoryIds = []
      nodes.forEach(node => {
        expandedItemCategoryIds.push(node.id)
        if (node.children && node.children.length) {
          expandedItemCategoryIds = expandedItemCategoryIds.concat(getters._getExpandedItemCategoryIds(node.children))
        }
      })
      return expandedItemCategoryIds
    }
  },
  mutations: {
    initOrderingCache (state) {
      state.itemCategories = []
      state.itemCategories.length = 0
      state.expandedItemCategoryIds = []
      state.expandedItemCategoryIds.length = 0
      state.itemCategoryMap = {}
    },
    resetOrderingState (state) {
      state.itemCategory = null
    },
    resetOrderDomainState (state) {
      state.operation = '增加'
      state.currentOrderDomain = null
      state.orderDomainList = []
      state.sequence = 1
    },
    updateItemCategories (state, itemCategories) {
      state.itemCategories = itemCategories
    },
    updateExpandedItemCategoryIds (state, expandedItemCategoryIds) {
      state.expandedItemCategoryIds = expandedItemCategoryIds
    },
    updateItemCategory (state, {itemCategoryId, itemCategory}) {
      itemCategory.evaluationList = [].concat(itemCategory.itemEvaluationList)
      itemCategory.parameterList = [].concat(itemCategory.itemParameterList)
      if (itemCategoryId) {
        state.itemCategoryMap[itemCategoryId] = itemCategory
      }
      state.itemCategory = itemCategory

      state.defaultOrderDomain.orderEntity.item_id = itemCategory.id
      state.defaultOrderDomain.orderEntity.item_name = itemCategory.name
      let reportPrintTemplateList = itemCategory.item.reportPrintTemplateList
      state.defaultOrderDomain.orderEntity.report_print_template_code = reportPrintTemplateList.length ? reportPrintTemplateList[0].template_code : null
      state.defaultOrderDomain.sampleEntity.code = itemCategory.item.sample_code

      // Vue双向数据绑定，属性必须是存在的，不能为undefined，所以这里需要初始化为null
      state.defaultOrderDomain.orderEntity.evaluation_group = {}
      if (itemCategory.itemEvaluationList.length) {
        itemCategory.itemEvaluationList.forEach(itemEvaluation => {
          state.defaultOrderDomain.orderEntity.evaluation_group[itemEvaluation.code] = null
        })
      }
    },
    updateCurrentOrderDomain (state, orderDomain) {
      // Vue双向数据绑定，属性必须是存在的，不能为undefined，所以这里需要初始化为null
      let evaluationGroup = {}
      if (state.itemCategory && state.itemCategory.itemEvaluationList) {
        state.itemCategory.itemEvaluationList.forEach(itemEvaluation => {
          evaluationGroup[itemEvaluation.code] = null
        })
      }
      state.currentOrderDomain = Vue.lodash.merge({orderEntity: {evaluation_group: evaluationGroup}}, orderDomain)
      state.operation = state.currentOrderDomain.id ? '修改' : '增加'
    },
    updateOrderDomainList (state, orderDomain) {
      if (Array.isArray(orderDomain)) {
        state.orderDomainList = orderDomain
        return
      }

      let index = state.orderDomainList.findIndex(item => item.id === orderDomain.id)
      if (index > -1) {
        state.orderDomainList[index] = orderDomain
      } else {
        state.orderDomainList.push(orderDomain)
      }
    }
  },
  actions: {
    initOrdering ({commit}) {
      commit('initOrderingCache')
      commit('resetOrderDomainState')
    },
    loadItemCategories ({commit, state, getters}) {
      commit('resetOrderingState')
      commit('resetOrderDomainState')

      if (state.itemCategories.length) {
        return
      }

      state.itemCategoriesLoading = true
      Vue.http.get('/api/hnjky/testing/cfg/item/categories', {
        params: {
          orderBy: 'parent_path,order',
          tree: true
        }
      }).then(response => {
        let itemCategories = response.body.success ? response.body.data.items[0].children : []
        commit('updateItemCategories', itemCategories)
        commit('updateExpandedItemCategoryIds', getters._getExpandedItemCategoryIds(itemCategories))
      }).finally(() => {
        state.itemCategoriesLoading = false
      })
    },
    switchItemCategory ({dispatch, commit, state}, itemCategoryId) {
      commit('resetOrderDomainState')

      let itemCategory = state.itemCategoryMap[itemCategoryId]
      if (itemCategory) {
        commit('updateItemCategory', {itemCategory: itemCategory})
        return
      }

      state.itemCategoryLoading = true
      Vue.store.commit('loading', 1)
      Vue.http.get('/api/hnjky/testing/cfg/item/categories/' + itemCategoryId + '/items/' + itemCategoryId).then(response => {
        let data = response.body.success ? response.body.data : {}
        let itemEquipmentList = data.itemEquipmentList || []
        itemCategory = data.itemCategory || {}
        itemCategory.itemEvaluationList = data.itemEvaluationList || []
        itemCategory.itemParameterList = data.itemParameterList || []

        itemCategory.itemEquipmentListMap = {}
        itemEquipmentList.forEach(equipment => {
          let equipmentList = itemCategory.itemEquipmentListMap[equipment.parameter_code]
          if (!equipmentList) {
            equipmentList = []
            itemCategory.itemEquipmentListMap[equipment.parameter_code] = equipmentList
          }
          equipmentList.push(equipment)
        })

        Promise.all([
          dispatch('getDictItemMap', 'TemplateCodeName101'), // 检测报告打印模板
          dispatch('getDictItemMap', 'TemplateCodeName103')  // 原始数据打印模板
        ]).then(results => {
          if (itemCategory.item.reportPrintTemplateList) {
            itemCategory.item.reportPrintTemplateList.forEach(reportPrintTemplate => {
              reportPrintTemplate.template_name = results[0][reportPrintTemplate.template_code]
            })
          }
          if (itemCategory.item.rawDataPrintTemplateList) {
            itemCategory.item.rawDataPrintTemplateList.forEach(rawDataPrintTemplate => {
              rawDataPrintTemplate.template_name = results[1][rawDataPrintTemplate.template_code]
            })
          }

          itemCategory.sampleInputTemplateVersion = null
          if (itemCategory.item.sampleInputTemplate) {
            dispatch('loadCache', {
              key: 'template-' + itemCategory.item.sampleInputTemplate.template_code,
              url: '/api/platform/index/templates/' + itemCategory.item.sampleInputTemplate.template_code,
              convertHandler (templateVersion) {
                return templateVersion
              }
            }).then(templateVersion => {
              itemCategory.sampleInputTemplateVersion = templateVersion
              commit('updateItemCategory', {itemCategoryId: itemCategoryId, itemCategory: itemCategory})
            }).catch(e => {
              Vue.prototype.$alert(e.message || '获取样品信息录入模板失败！', '错误', {
                confirmButtonText: '关闭',
                type: 'error'
              })
            })
          } else {
            commit('updateItemCategory', {itemCategoryId: itemCategoryId, itemCategory: itemCategory})
          }
        })
      }).finally(() => {
        Vue.store.commit('loading', -1)
        state.itemCategoryLoading = false
      })
    },
    editOrderDomain ({dispatch, commit, state}, orderDomain) {
      commit('updateCurrentOrderDomain', orderDomain)
    },
    copyOrderDomain ({dispatch, commit, state}, refOrderDomain) {
      let orderDomain = Vue.lodash.merge({}, refOrderDomain)
      orderDomain.id = null
      return new Promise((resolve, reject) => {
        resolve(dispatch('_updateOrderDomain', {orderDomain: orderDomain, refOrderDomain: refOrderDomain}))
      })
    },
    saveOrderDomain ({dispatch, commit, state}, orderDomain) {
      return new Promise((resolve, reject) => {
        resolve(dispatch('_updateOrderDomain', {orderDomain: Vue.lodash.merge({}, orderDomain)}))
      })
    },
    _updateOrderDomain ({commit, state}, {orderDomain, refOrderDomain}) {
      // console.log('_updateOrderDomain', orderDomain)
      if (!orderDomain.id) {
        orderDomain.id = state.sequence++
      }

      let id = refOrderDomain ? refOrderDomain.id : orderDomain.id
      let no = '#' + id
      if (!orderDomain.orderEntity.no) {
        orderDomain.orderEntity.id = -id
        orderDomain.orderEntity.no = no
      }
      if (!orderDomain.sampleEntity.no) {
        orderDomain.sampleEntity.id = -id
        orderDomain.sampleEntity.no = no
      }
      if (!orderDomain.reportEntity.no) {
        orderDomain.reportEntity.id = -id
        orderDomain.reportEntity.no = no
      }

      commit('updateOrderDomainList', orderDomain)

      return orderDomain
    },
    removeOrderDomain ({commit, state}, orderDomain) {
      let index = state.orderDomainList.findIndex(domain => domain.id === orderDomain.id)
      let orderDomainList = []
      state.orderDomainList.forEach(domain => {
        if (domain.id !== orderDomain.id) {
          let id = domain.id
          let no = '#' + domain.id
          let oldNo = '#' + orderDomain.id
          if (domain.orderEntity.no === oldNo) {
            domain.orderEntity.id = -id
            domain.orderEntity.no = no
          }
          if (domain.sampleEntity.no === oldNo) {
            domain.sampleEntity.id = -id
            domain.sampleEntity.no = no
          }
          if (domain.reportEntity.no === oldNo) {
            domain.reportEntity.id = -id
            domain.reportEntity.no = no
          }

          orderDomainList.push(domain)
        }
      })

      if (!orderDomainList.length) {
        commit('resetOrderDomainState')
        return
      }

      commit('updateOrderDomainList', orderDomainList)

      // 删除的是当前委托单，则把下一个设置为当前委托单
      if (state.currentOrderDomain.id === orderDomain.id) {
        let currentOrderDomain = orderDomainList[index < (orderDomainList.length - 1) ? index : (orderDomainList.length - 1)]
        commit('updateCurrentOrderDomain', currentOrderDomain)
      } else {
        let no = '#' + orderDomain.id
        // 如果当前委托单引用了删除的，则需要更新当前委托单的属性
        if (state.currentOrderDomain.orderEntity.no === no ||
          state.currentOrderDomain.sampleEntity.no === no ||
          state.currentOrderDomain.reportEntity.no === no) {
          let currentOrderDomain = orderDomainList.find(domain => domain.id !== state.currentOrderDomain.id)
          commit('updateCurrentOrderDomain', currentOrderDomain)
        }
      }
    },
    loadOrderingSuggestions ({state}, {projectId, queryString, entityName}) {
      return new Promise((resolve, reject) => {
        // console.log('loadOrderingSuggestions', projectId, entityName, queryString)
        if (queryString && !queryString.startsWith('#')) {
          let urls = {
            orderEntity: '/api/hnjky/testing/lab/orders',
            sampleEntity: '/api/hnjky/testing/lab/samples',
            reportEntity: '/api/hnjky/testing/lab/reports'
          }
          Vue.http.get(urls[entityName], {
            params: {
              no_like: queryString,
              project_id: projectId,
              isSuggestions: true,
              pageSize: 5,
              orderBy: 'no desc'
            }
          }).then(response => {
            resolve(response.body.success ? response.body.data.items : [])
          }).catch(e => {
            console.error(e)
            resolve([])
          })
          return
        }

        resolve(state.orderDomainList //
          .filter(orderDomain => !queryString || ('#' + orderDomain.id).startsWith(queryString)) //
          .map(orderDomain => Vue.lodash.merge({}, orderDomain[entityName], {
            id: -orderDomain.id,
            no: '#' + orderDomain.id,
            item_id: orderDomain.orderEntity.item_id,
            item_name: orderDomain.orderEntity.item_name,
            project_id: orderDomain.orderEntity.project_id,
            project_name: orderDomain.orderEntity.project_name
          })) //
          .reverse())
      })
    },
    loadOrderingIdByNo ({state}, {projectId, no, entityName}) {
      return new Promise((resolve, reject) => {
        // console.log('loadOrderingIdByNo', projectId, entityName, no)
        if (!no) {
          resolve(null)
          return
        }

        if (no.startsWith('#')) {
          let orderDomain = state.orderDomainList.find(orderDomain => (!projectId || projectId === orderDomain.orderEntity.project_id) && no === '#' + orderDomain.id)
          if (orderDomain) {
            resolve(-orderDomain.id)
            return
          }
          if (projectId) {
            reject(new Error('当前工程无此编号【' + no + '】'))
          } else {
            reject(new Error('无效的编号【' + no + '】'))
          }
          return
        }

        let urls = {
          orderEntity: '/api/hnjky/testing/lab/orders',
          sampleEntity: '/api/hnjky/testing/lab/samples',
          reportEntity: '/api/hnjky/testing/lab/reports'
        }
        Vue.http.get(urls[entityName], {
          params: {
            no: no,
            project_id: projectId,
            isSuggestions: true
          }
        }).then(response => {
          let entityList = response.body.success ? response.body.data.items : []
          if (entityList && entityList.length) {
            resolve(entityList[0].id)
            return
          }

          if (projectId) {
            reject(new Error('当前工程无此编号【' + no + '】'))
          } else {
            reject(new Error('无效的编号【' + no + '】'))
          }
        }).catch(e => {
          reject(new Error('获取编号【' + no + '】失败'))
        })
      })
    },
    submitOrdering ({commit, state}) {
      return new Promise((resolve, reject) => {
        if (state.orderDomainList.length === 0) {
          Vue.prototype.$alert('请先录入委托单！', '错误', {
            confirmButtonText: '关闭',
            type: 'error'
          })
          reject(new Error('请先录入委托单！'))
        } else {
          state.submitLoading = true
          Vue.store.commit('loading', 1)
          Vue.http.post('/api/hnjky/testing/lab/order', state.orderDomainList, {showSuccessMessage: false}).then(response => {
            if (response.body.success) {
              commit('resetOrderDomainState')
              resolve(response.body.data)
            } else {
              reject(new Error(response.body.message))
            }
          }).catch(e => {
            reject(e)
          }).finally(() => {
            Vue.store.commit('loading', -1)
            state.submitLoading = false
          })
        }
      })
    },
    loadOrderDomain ({commit, state}, id) {
      return new Promise((resolve, reject) => {
        state.submitLoading = true
        Vue.store.commit('loading', 1)
        Vue.http.get('/api/hnjky/testing/lab/order/' + id).then(response => {
          if (response.body.success) {
            commit('updateCurrentOrderDomain', response.body.data)
            resolve(response.body.data)
          } else {
            reject(new Error(response.body.message))
          }
        }).catch(response => {
          console.error(response)
          reject(new Error(response.body.message || '加载委托单失败！'))
        }).finally(() => {
          Vue.store.commit('loading', -1)
          state.submitLoading = false
        })
      })
    },
    submitOrderDomain ({commit, state}, orderDomain) {
      return new Promise((resolve, reject) => {
        state.submitLoading = true
        Vue.store.commit('loading', 1)
        Vue.http.put('/api/hnjky/testing/lab/order/' + orderDomain.id, orderDomain).then(response => {
          if (response.body.success) {
            resolve(response.body.data)
          } else {
            reject(new Error(response.body.message))
          }
        }).catch(response => {
          console.error(response)
          reject(new Error(response.body.message || '保存委托单失败！'))
        }).finally(() => {
          Vue.store.commit('loading', -1)
          state.submitLoading = false
        })
      })
    }
  }
}
