package com.Graphics.Workspace.Component;

public enum AnimationState {
    /**
     * Phase 1: The animation is waiting before the growth of the component.
     */
    p1,
    /**
     * Phase 2: The animation is growing the component.
     */
    p2,
    /**
     * Phase 3: The animation is waiting before the shrinking of the component.
     */
    p3,
    /**
     * Phase 4: The animation is shrinking the component.
     */
    p4
}
