package com.example.rentalchemy.database.api

import com.apollographql.apollo.ApolloClient

val apolloClient = ApolloClient.builder()
    //.serverUrl("http://localhost:3001")
    .serverUrl("http://10.0.2.2:3001")  //for emulator
    .build()