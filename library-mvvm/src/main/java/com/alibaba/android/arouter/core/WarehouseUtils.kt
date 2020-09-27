package com.alibaba.android.arouter.core

import com.alibaba.android.arouter.facade.model.RouteMeta

/**
 * desc
 * author: 朱子楚
 * time: 2020/9/27 3:20 PM
 * since: v 1.0.0
 */
class WarehouseUtils {
    companion object {
        fun getRouteMeta(path: String): RouteMeta? {
            return Warehouse.routes[path]
        }
    }
}