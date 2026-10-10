package com.casz.prettyfrogs.control;

/** Server-authoritative controller actions on a ridden vanilla frog. */
public interface FrogControlAccess {
    void prettyfrogs$controlledCroak();
    void prettyfrogs$controlledTongue();
    void prettyfrogs$controlledHop();
    /** Client prediction for vertical swimming; server uses synced Player Input. */
    void prettyfrogs$setSwimInputs(boolean up, boolean down);
}
