package com.company;
public class Position {
    private int Y_init;
    private int X_init;
    private int Y_pre;
    private int X_pre;
    public Position(int x_position, int y_position) {
        X_init = x_position;
        Y_init = y_position;
    }
    public void setY_init(int y_init) {
        Y_init = y_init;
    }
    public void setX_init(int x_init) {
        X_init = x_init;
    }
    public int getY_init() {
        return Y_init;
    }
    public int getX_init() {
        return X_init;
    }
    public int getY_pre() {
        return Y_pre;
    }
    public int getX_pre() {
        return X_pre;
    }
    public void init_swap_pre(){
        X_pre=X_init;
        Y_pre=Y_init;
    }
}
/**Done by Sobrattee Mohammad Muzzammil**/
