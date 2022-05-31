package com.Graphics.Workspace.Component;

import com.Config;

/**
 * This class is used to animate components
 */
public class ComponentAnimation {
    /**
     * The animation state
     */
    AnimationState state = AnimationState.p1;

    /**
     * The time of start of animation
     */
    private long startTime;
    /**
     * The actual time of animation since the start
     */
    private long actualTime;

    /**
     * The normalised time of animation
     */
    private double time;

    /**
     * The actual size of the animation, between 0 and 1
     */
    private double size;

    public double getSize() {
        return size;
    }

    private static final double Px0 = 0;
    private static final double Px1 = 0.5;
    private static final double Px2 = 0.5;
    private static final double Px3 = 1;

    private static final double Py0 = 0;
    private static final double Py1 = 0;
    private static final double Py2 = 1;
    private static final double Py3 = 1;

    /**
     * The x coordinate of the animation
     */
    private final double[] xCoords;
    /**
     * The y coordinate of the animation
     */
    private final double[] yCoords;

    /**
     * The number of points of the bezier curve
     */
    public static int n = 50;


    /**
     * Constructor
     */
    public ComponentAnimation() {
        xCoords = new double[n];
        yCoords = new double[n];

        double nval = n;
        for (int i = 0; i < n; i++) {
            xCoords[i] = Px0 * (1 - i / nval) * (1 - i / nval) * (1 - i / nval) + Px1 * i / nval * (1 - i / nval) * (1 - i / nval) + Px2 * i / nval * i / nval * (1 - i / nval) + Px3 * i / nval * i / nval * i / nval;
            yCoords[i] = Py0 * (1 - i / nval) * (1 - i / nval) * (1 - i / nval) + Py1 * i / nval * (1 - i / nval) * (1 - i / nval) + Py2 * i / nval * i / nval * (1 - i / nval) + Py3 * i / nval * i / nval * i / nval;
        }

        startTime = System.currentTimeMillis();
        actualTime = 0;
    }

    /**
     * Update the animation state to the next state
     */
    public void setState(AnimationState state) {
        this.state = state;
        startTime = System.currentTimeMillis();
    }

    /**
     * Update the animation
     */
    public void animate() {
        switch (state) {
            case p1:
                time = 0;
                break;
            case p3:
                time = 1;
                break;
            case p2:
                actualTime = System.currentTimeMillis() - startTime;
                if (actualTime < Config.WSAnimationDurationInMs) {
                    time = actualTime / Config.WSAnimationDurationInMs;
                }
                else {
                    time = 1;
                    setState(AnimationState.p3);
                }
                break;
            case p4:
                actualTime = System.currentTimeMillis() - startTime;
                if (actualTime < Config.WSAnimationDurationInMs) {
                    time = (Config.WSAnimationDurationInMs - actualTime) / Config.WSAnimationDurationInMs;
                }
                else {
                    time = 0;
                    setState(AnimationState.p1);
                }
                break;
        }
        size = getValue(time);
    }

    /**
     * Gets the value of the size of the component.
     * @return The value of the size of the component.
     */
    private double getValue(double x) {
        return yCoords[getNearestPoint(x)];
    }

    /**
     * Gets the nearest point on the bezier curve to the given x coordinate with dichotomy method.
     * @param x The x coordinate.
     * @return The index of the nearest point.
     */
    private int getNearestPoint(double x) {
        // Use dichotomy method to find the nearest point in the xCoods array
        int left = 0;
        int right = n - 1;
        int mid;
        while (left < right) {
            mid = (left + right) / 2;
            if (xCoords[mid] < x) {
                left = mid + 1;
            }
            else {
                right = mid;
            }
        }
        return left;
    }
}
