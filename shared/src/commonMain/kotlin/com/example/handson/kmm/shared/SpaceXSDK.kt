package com.example.handson.kmm.shared

import com.example.handson.kmm.shared.cache.Database
import com.example.handson.kmm.shared.cache.DatabaseDriverFactory
import com.example.handson.kmm.shared.entity.RocketLaunch
import com.example.handson.kmm.shared.network.SpaceXApi

class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    suspend fun getLaunches(forceReload:Boolean):List<RocketLaunch>{
        val cachedLaunches = database.getAllLaunches()
        return if(cachedLaunches.isNotEmpty() && !forceReload){
            cachedLaunches
        }else{
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }
}