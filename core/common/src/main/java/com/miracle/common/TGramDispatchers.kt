package com.miracle.common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val tgramDispatcher: TGramDispatchers)

enum class TGramDispatchers {
    Default,
    IO,
}