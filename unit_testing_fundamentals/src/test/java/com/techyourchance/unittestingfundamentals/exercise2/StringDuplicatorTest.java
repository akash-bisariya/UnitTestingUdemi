package com.techyourchance.unittestingfundamentals.exercise2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class StringDuplicatorTest {
    StringDuplicator stringDuplicator;

    @Before
    public void setUp() throws Exception {
        stringDuplicator = new StringDuplicator();
    }

    @Test
    public void test1() {
        String val = stringDuplicator.duplicate("akash");
        Assert.assertThat(val, is("akashakash"));
    }

    @Test
    public void test2() {
        String val  = stringDuplicator.duplicate("1");
        Assert.assertThat(val,is("11"));
    }

    @Test
    public void duplicate_emptyString_emptyStringReturned() {
        String val  = stringDuplicator.duplicate("");
        Assert.assertThat(val,is(""));
    }

    @Test
    public void duplicate_longString_duplicatedStringReturned() {
        String val  = stringDuplicator.duplicate("Akash shankar Bisariya");
        Assert.assertThat(val,is("Akash shankar BisariyaAkash shankar Bisariya"));
    }


}