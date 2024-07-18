package com.odisby.goldentomatoes.testutils.robot

/**
 * Each test Robot in our application (UI test Robot or Unit test Robot) should extend BaseRobot
 */
interface BaseRobot {
    fun setup()

    fun tearsDown()
}