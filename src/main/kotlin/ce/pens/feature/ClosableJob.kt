package ce.pens.feature

import io.ktor.utils.io.core.*

interface ClosableJob : Closeable, Runnable