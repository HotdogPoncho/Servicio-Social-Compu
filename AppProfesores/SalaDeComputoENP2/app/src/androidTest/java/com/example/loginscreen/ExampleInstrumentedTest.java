package com.example.loginscreen;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    /*@Rule
    public ActivityScenarioRule<MainActivity> mainActivityActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void prueba(){
        onView(withId(R.id.txtNumTrab)).perform(typeText("115000957"));
        onView(withId(R.id.cmdIniciarSesion)).perform(click()).check(matches(isDisplayed()));
    }*/
    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<RegistroInvalido> registroInvalidoActivityTestRule = new ActivityTestRule<>(RegistroInvalido.class);

    @Test
    public void prueba(){
        onView(withId(R.id.txtNumTrab)).perform(typeText("11223155"));
        onView(withId(R.id.cmdIniciarSesion)).perform(click());
        onView(withId(R.id.Registro)).check(matches(withText("REGISTRO INVÁLIDO")));
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.loginscreen", appContext.getPackageName());
    }
}