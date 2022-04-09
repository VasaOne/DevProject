package com.Graphics.Workspace.Application;

/**
 * The current action of the user on the workspace.
 */
public enum CurrentAction {
    pressOnOutputNode,
    pressOnInputNode,
    pressOnComponent,
    pressOnWire,
    wireDragFromOutput,
    wireDragFromInput,
    componentDrag,
    none
}
