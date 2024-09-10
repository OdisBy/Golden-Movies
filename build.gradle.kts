import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

allprojects {

    tasks.withType<Test> {
        testLogging {
            lifecycle {
                /*
                 * TestLogEvent produces a lot of unnecessary log, but can be useful if necessary check all passed tests
                 */
                events = mutableSetOf(
                    TestLogEvent.FAILED,
                    TestLogEvent.SKIPPED
                )
                exceptionFormat = TestExceptionFormat.FULL
                showExceptions = true
                showCauses = true
                showStackTraces = true
                showStandardStreams = true
            }
            info.events = lifecycle.events
            info.exceptionFormat = lifecycle.exceptionFormat
        }

        val failedTests = mutableListOf<TestDescriptor>()
        val skippedTests = mutableListOf<TestDescriptor>()
        addTestListener(object : TestListener {
            override fun beforeSuite(suite: TestDescriptor) {}
            override fun beforeTest(testDescriptor: TestDescriptor) {}
            override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
                when (result.resultType) {
                    TestResult.ResultType.FAILURE -> failedTests.add(testDescriptor)
                    TestResult.ResultType.SKIPPED -> skippedTests.add(testDescriptor)
                    else -> Unit
                }
            }

            override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                if (suite.parent == null) { // root suite
                    val output = "Tests result on ${project.name}: ${result.resultType} | " +
                            "Test summary: ${result.testCount} tests, " +
                            "${result.successfulTestCount} succeeded, " +
                            "${result.failedTestCount} failed, " +
                            "${result.skippedTestCount} skipped"

                    val startItem = "|  "
                    val endItem = "  |"
                    val repeatLength = startItem.length + output.length + endItem.length

                    logger.lifecycle(
                        "\n" + ("-".repeat(repeatLength)) + "\n" + startItem + output + endItem + "\n" + ("-".repeat(
                            repeatLength
                        ))
                    )
                    failedTests.takeIf { it.isNotEmpty() }?.prefixedSummary("\tFailed Tests")
                    skippedTests.takeIf { it.isNotEmpty() }?.prefixedSummary("\tSkipped Tests:")

                }
            }

            private infix fun List<TestDescriptor>.prefixedSummary(subject: String) {
                logger.lifecycle(subject)
                forEach { test -> logger.lifecycle("\t\t${test.displayName()}") }
            }

            private fun TestDescriptor.displayName() = parent?.let { "${it.name} - $name" } ?: name
        })
    }

}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.compose.compiler) apply false
}

buildscript {
    extra.apply {
        set("compileSdk", 34)
        set("minSdk", 26)
        set("targetSdk", 34)
    }
}
