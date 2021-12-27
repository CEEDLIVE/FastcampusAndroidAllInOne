package ceed.live.android.kotlin.fastcampus.instagram.logger

import android.util.Log

class Log4k {

    companion object {
        private const val TAG_DEFAULT:String = "ceedlive"

        private const val TWO:Int = Log.VERBOSE // 2
        private const val THREE:Int = Log.DEBUG // 3
        private const val FOUR:Int = Log.INFO // 4
        private const val FIVE:Int = Log.WARN // 5
        private const val SIX:Int = Log.ERROR // 6
        private const val SEVEN:Int = Log.ASSERT // 7

        private const val LOG_PRINT = 1
        private const val LOG_NOT_PRINT = 0

        private const val LOG_STACK_POSITION_DEFAULT = 5
        private const val LOG_STACK_POSITION = LOG_STACK_POSITION_DEFAULT

        private val LOG_LEVEL: Level = Level.DEBUG

        // ASSERT
        fun wtf(_tag: String?, _message: String?, _stackPosition: Int, _throwable: Throwable?) {
            if (isLogPrint(Level.ERROR)) {
                Log.wtf(getLogTag(_tag), getLogMessage(_message, _stackPosition), _throwable)
            }
        }

        // ERROR
        fun e(_tag: String?, _message: String?, _stackPosition: Int, _throwable: Throwable?) {
            if (isLogPrint(Level.ERROR)) {
                Log.e(getLogTag(_tag), getLogMessage(_message, _stackPosition), _throwable)
            }
        }

        fun e(_tag: String?, _message: String?, _throwable: Throwable?) {
            e(_tag, _message, LOG_STACK_POSITION, _throwable)
        }

        fun e(_tag: String?, _message: String?) {
            e(_tag, _message, LOG_STACK_POSITION, null)
        }

        fun e(_message: String?) {
            e(null, _message, LOG_STACK_POSITION, null)
        }

        // WARN
        fun w(_tag: String?, _message: String?, _stackPosition: Int, _throwable: Throwable?) {
            if (isLogPrint(Level.WARN)) {
                Log.w(getLogTag(_tag), getLogMessage(_message, _stackPosition), _throwable)
            }
        }

        fun w(_tag: String?, _message: String?, _throwable: Throwable?) {
            w(_tag, _message, LOG_STACK_POSITION, _throwable)
        }

        fun w(_tag: String?, _message: String?) {
            w(_tag, _message, LOG_STACK_POSITION, null)
        }

        fun w(_message: String?) {
            w(null, _message, LOG_STACK_POSITION, null)
        }

        // INFO
        fun i(_tag: String?, _message: String?, _stackPosition: Int, _throwable: Throwable?) {
            if (isLogPrint(Level.INFO)) {
                Log.i(getLogTag(_tag), getLogMessage(_message, _stackPosition), _throwable)
            }
        }

        fun i(_tag: String?, _message: String?, _throwable: Throwable?) {
            i(_tag, _message, LOG_STACK_POSITION, _throwable)
        }

        fun i(_tag: String?, _message: String?) {
            i(_tag, _message, LOG_STACK_POSITION, null)
        }

        fun i(_message: String?) {
            i(null, _message, LOG_STACK_POSITION, null)
        }

        // DEBUG
        fun d(_tag: String?, _message: String?, _stackPosition: Int, _throwable: Throwable?) {
            if (isLogPrint(Level.DEBUG)) {
                Log.d(getLogTag(_tag), getLogMessage(_message, _stackPosition), _throwable)
            }
        }

        fun d(_tag: String?, _message: String?, _throwable: Throwable?) {
            d(_tag, _message, LOG_STACK_POSITION, _throwable)
        }

        fun d(_tag: String?, _message: String?) {
            d(_tag, _message, LOG_STACK_POSITION, null)
        }

        fun d(_message: String?) {
            d(null, _message, LOG_STACK_POSITION, null)
        }

        // VERBOSE
        fun v(_tag: String?, _message: String?, _stackPosition: Int, _throwable: Throwable?) {
            if (isLogPrint(Level.VERBOSE)) {
                Log.v(getLogTag(_tag), getLogMessage(_message, _stackPosition), _throwable)
            }
        }

        fun v(_tag: String?, _message: String?, _throwable: Throwable?) {
            v(_tag, _message, LOG_STACK_POSITION, _throwable)
        }

        fun v(_tag: String?, _message: String?) {
            v(_tag, _message, LOG_STACK_POSITION, null)
        }

        fun v(_message: String?) {
            v(null, _message, LOG_STACK_POSITION, null)
        }

        private fun isLogPrint(_level: Level): Boolean {
            return Level.comparePriorities(LOG_LEVEL, _level) > LOG_NOT_PRINT
        }

        private fun getLogTag(_tag: String?): String {
            return _tag ?: TAG_DEFAULT
        }

        private fun getLogMessage(_message: String?, _stackPosition: Int): String? {
            val elements = Thread.currentThread().stackTrace // 현재 실행 중인 스레드의 스택 트레이스 : 전체 Call Stack 정보를 가지고 있음
            if (_stackPosition >= elements.size) {
                Log.e(null, "-- _stackPosition = " + _stackPosition + "  --elements.length = " + elements.size)
                return ""
            }
            val element = elements[_stackPosition]
            val fileName = element.fileName
            val lineNumber = element.lineNumber
            val p1CustomMessage = _message ?: ""
            val p2ClassPath = element.className
            val p3MethodName = element.methodName
            val p4ThreadName = Thread.currentThread().name
            val p5ThreadPriority = Thread.currentThread().priority
            val p6ThreadId = Thread.currentThread().id
            val p7Link = "($fileName:$lineNumber)"
            val reformedClassName = getClassName(element)
            val reformedMethodName = getMethodName(element)
            return "[ $p1CustomMessage ] at $reformedClassName.$reformedMethodName '$p4ThreadName/priority:$p5ThreadPriority/tid:$p6ThreadId' $p7Link"
        }

        private fun getClassName(currentStack: StackTraceElement): String {
            val className = currentStack.className // ex) ceed.live.android.percent95.lifecycle.MainActivity$1$1
            val indexOfDot = className.lastIndexOf(".")
            val indexOfDollar = className.indexOf("$", indexOfDot)
            return if (indexOfDollar < 0) {
                className.substring(indexOfDot + 1)
            } else {
                className.substring(indexOfDot + 1, indexOfDollar)
            }
        }

        private fun getMethodName(currentStack: StackTraceElement): String {
            var methodName = currentStack.methodName
            val methodParts = methodName.split("\\$").toTypedArray()
            for (methodPart in methodParts) {
                if (methodPart != "lambda") {
                    methodName = methodPart
                    break
                }
            }
            return methodName
        }

        private fun getLogMessage(_message: String?): String? {
            val elements = Thread.currentThread().stackTrace // 현재 실행 중인 스레드의 스택 트레이스
            var p1CustomMessage = ""
            var p2ClassPath = ""
            var p3MethodName = ""
            var p4ThreadName: String? = ""
            var p5ThreadPriority = -1
            var p6ThreadId: Long = -1
            var p7Link = ""
            for (element in elements) {
                val fileName = element.fileName
                val lineNumber = element.lineNumber
                p1CustomMessage = _message ?: ""
                p2ClassPath = element.className
                p3MethodName = element.methodName
                p4ThreadName = Thread.currentThread().name
                p5ThreadPriority = Thread.currentThread().priority
                p6ThreadId = Thread.currentThread().id
                p7Link = "($fileName:$lineNumber)"
            }
            return "[ $p1CustomMessage ] at $p2ClassPath.$p3MethodName '$p4ThreadName/priority:$p5ThreadPriority/tid:$p6ThreadId' $p7Link"
        }
    }

    private enum class Level(val priority: Int) {

        VERBOSE(TWO), DEBUG(THREE), INFO(FOUR), WARN(FIVE), ERROR(SIX), ASSERT(SEVEN);

        companion object {
            fun findEnumByPriority(priority: Int): Level {
                for (level in Level.values()) {
                    if (priority == level.priority) {
                        return level
                    }
                }
                return VERBOSE
            }

            fun comparePriorities(o1: Level, o2: Level): Int {
                return if (o1.priority <= o2.priority) {
                    LOG_PRINT
                } else LOG_NOT_PRINT
            }
        }
    }

}