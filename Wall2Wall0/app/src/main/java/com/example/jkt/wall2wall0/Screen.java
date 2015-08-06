package com.example.jkt.wall2wall0;


public abstract class Screen {
    protected Game game;

    public Screen(Game game) {
        this.game = game;
    }

    public abstract void update(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();

    public abstract void paint(float deltaTime);
}
