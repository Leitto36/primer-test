package com.primer.demo.util

import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.core.LoggerContext

inline val <T : Any> T.log: Logger
    get() = LoggerContext.getContext().getLogger(this.javaClass.canonicalName)
