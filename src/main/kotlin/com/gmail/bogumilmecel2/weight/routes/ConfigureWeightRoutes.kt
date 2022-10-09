package com.gmail.bogumilmecel2.weight.routes

import com.gmail.bogumilmecel2.weight.domain.use_case.WeightUseCases
import io.ktor.server.routing.*

fun Route.configureWeightRoutes(weightUseCases:WeightUseCases){
    route("/weightEntries"){
        configureAddWeightEntryRoute(weightUseCases.addWeightEntry)
        configureGetLatestWeightEntriesRoute(weightUseCases.getLatestWeightEntries)
    }
}