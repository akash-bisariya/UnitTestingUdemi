package com.techyourchance.unittestingfundamentals.exercise3;

import com.techyourchance.unittestingfundamentals.example3.Interval;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IntervalsAdjacencyDetectorTest {

    IntervalsAdjacencyDetector intervalsAdjacencyDetector;

    @Before
    public void setUp() throws Exception {
        intervalsAdjacencyDetector = new IntervalsAdjacencyDetector();
    }


    //interval1 is before interval2 with overlap/without overlap
    @Test
    public void IsAdjacent_Interval1BeforeInterval2WithOutOverlap_trueReturned() {
        Interval interval1 = new Interval(1,3);
        Interval interval2 = new Interval(3,4);
        Assert.assertThat(intervalsAdjacencyDetector.isAdjacent(interval1,interval2),is(true));
    }

    //interval1 start from end of interval2 with overlap/without overlap
    @Test
    public void IsAdjacent_Interval1BeforeInterval2WithOverlap_falseReturned() {
        Interval interval1 = new Interval(1,3);
        Interval interval2 = new Interval(2,4);
        Assert.assertThat(intervalsAdjacencyDetector.isAdjacent(interval1,interval2),is(false));
    }

    //interval2 start after interval1
    @Test
    public void IsAdjacent_Interval1StartAfterInterval2_falseReturned() {
        Interval interval1 = new Interval(1,3);
        Interval interval2 = new Interval(4,5);
        Assert.assertThat(intervalsAdjacencyDetector.isAdjacent(interval1,interval2),is(false));
    }
    //interval2 start within interval1
    @Test
    public void IsAdjacent_Interval1StartWithinInterval2_falseReturned() {
        Interval interval1 = new Interval(1,3);
        Interval interval2 = new Interval(2,5);
        Assert.assertThat(intervalsAdjacencyDetector.isAdjacent(interval1,interval2),is(false));
    }

    //interval2 is before interval1 WithoutOverlap
    @Test
    public void IsAdjacent_Interval2StartBeforeInterval1WithoutOverlap_trueReturned() {
        Interval interval1 = new Interval(4,7);
        Interval interval2 = new Interval(1,4);
        Assert.assertThat(intervalsAdjacencyDetector.isAdjacent(interval1,interval2),is(true));
    }

    //interval2 is before interval1 With Overlap
    @Test
    public void IsAdjacent_Interval2StartBeforeInterval1WithOverlap_falseReturned() {
        Interval interval1 = new Interval(4,7);
        Interval interval2 = new Interval(1,5);
        Assert.assertThat(intervalsAdjacencyDetector.isAdjacent(interval1,interval2),is(false));
    }

    //interval2 is before interval1
    @Test
    public void IsAdjacent_Interval2StartBeforeInterval1_falseReturned() {
        Interval interval1 = new Interval(5,7);
        Interval interval2 = new Interval(1,3);
        Assert.assertThat(intervalsAdjacencyDetector.isAdjacent(interval1,interval2),is(false));
    }

    //interval2 is within interval1
    @Test
    public void IsAdjacent_Interval2WithinInterval1_falseReturned() {
        Interval interval1 = new Interval(5,9);
        Interval interval2 = new Interval(5,8);
        Assert.assertThat(intervalsAdjacencyDetector.isAdjacent(interval1,interval2),is(false));
    }

    @Test
    public void IsAdjacent_SameInterval_falseReturned() {
        Interval interval1 = new Interval(4,7);
        Interval interval2 = new Interval(4,7);
        Assert.assertThat(intervalsAdjacencyDetector.isAdjacent(interval1,interval2),is(false));
    }

    @Test
    public void isAdjacent_interval1BeforeAndAdjacentToInterval2_trueReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(5, 12);
        boolean result = intervalsAdjacencyDetector.isAdjacent(interval1, interval2);
        assertThat(result, is(true));
    }

    @Test
    public void isAdjacent_interval1AfterInterval2_falseReturned() throws Exception {
        Interval interval1 = new Interval(-1, 5);
        Interval interval2 = new Interval(-10, -3);
        boolean result = intervalsAdjacencyDetector.isAdjacent(interval1, interval2);
        assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_interval1AfterAndAdjacentToInterval2_trueReturned() throws Exception {
        Interval interval1 = new Interval(12, 15);
        Interval interval2 = new Interval(5, 12);
        boolean result = intervalsAdjacencyDetector.isAdjacent(interval1, interval2);
        assertThat(result, is(true));
    }
}