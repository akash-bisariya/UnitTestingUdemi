package com.techyourchance.unittestingfundamentals.exercise1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class NegativeNumberValidatorTest {
    private NegativeNumberValidator negativeNumberValidator;

    @Before
    public void setUp(){
        negativeNumberValidator = new NegativeNumberValidator();
    }

    @Test
    public void test1(){
        boolean res = negativeNumberValidator.isNegative(-1);
        Assert.assertThat(res,is(true));
    }

    @Test
    public void test2(){
        boolean res = negativeNumberValidator.isNegative(1);
        Assert.assertThat(res,is(false));
    }

    @Test
    public void test3(){
        boolean res = negativeNumberValidator.isNegative(0);
        Assert.assertThat(res,is(false));
    }

    @Test
    public void test4(){
        boolean res = negativeNumberValidator.isNegative(-5);
        Assert.assertThat(res,is(true));
    }

    @Test
    public void test5(){
        boolean res = negativeNumberValidator.isNegative(-100);
        Assert.assertThat(res,is(true));
    }

    @Test
    public void test6(){
        boolean res = negativeNumberValidator.isNegative(100);
        Assert.assertThat(res,is(false));
    }


}