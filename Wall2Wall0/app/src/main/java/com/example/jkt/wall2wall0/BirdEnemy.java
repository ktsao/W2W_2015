package com.example.jkt.wall2wall0;

import android.util.Log;

import com.example.jkt.wall2wall0.math.Rectangle;
import com.example.jkt.wall2wall0.math.Shape;
import com.example.jkt.wall2wall0.math.Vector2;

import java.util.ArrayList;

/**
 * Created by James on 4/16/2015.
 */
public class BirdEnemy extends falling_enemy {
    private Rectangle bounds_right;
    private Rectangle bounds2_right;
    private Rectangle bounds3_right;
    private Rectangle bounds4_right;
    private Rectangle bounds5_right;
    private Rectangle bounds_left;
    private Rectangle bounds2_left;
    private Rectangle bounds3_left;
    private Rectangle bounds4_left;
    private Rectangle bounds5_left;
    private boolean start_moving_left;
    private ArrayList<Shape> bounds_right_tsil = new ArrayList();
    private ArrayList<Shape> bounds_left_tsil = new ArrayList();

    public BirdEnemy(float x, float y, float width, float height, int enemy_num) {
        super(x, y, width, height, enemy_num);
        this.velocity = new Vector2(4f, 4f);

        this.bounds_right = new Rectangle(x+2, y+2, 10, 31);
        this.bounds2_right = new Rectangle(x+40, y+4, 12, 63);
        this.bounds3_right = new Rectangle(x+14, y+23, 24, 48);
        this.bounds4_right = new Rectangle(x+31, y+74, 15, 12);
        this.bounds5_right = new Rectangle(x+6, y+76, 24, 21);

        this.bounds_left = new Rectangle(x+45, y+2, 10, 31);
        this.bounds2_left = new Rectangle(x+5, y+4, 12, 63);
        this.bounds3_left = new Rectangle(x+19, y+23, 24, 48);
        this.bounds4_left = new Rectangle(x+11, y+74, 15, 12);
        this.bounds5_left = new Rectangle(x+27, y+76, 24, 21);

        this.bounds_right_tsil.add(bounds_right);
        this.bounds_right_tsil.add(bounds2_right);
        this.bounds_right_tsil.add(bounds3_right);
        this.bounds_right_tsil.add(bounds4_right);
        this.bounds_right_tsil.add(bounds5_right);

        this.bounds_left_tsil.add(bounds_left);
        this.bounds_left_tsil.add(bounds2_left);
        this.bounds_left_tsil.add(bounds3_left);
        this.bounds_left_tsil.add(bounds4_left);
        this.bounds_left_tsil.add(bounds5_left);

        this.bounds_tsil = this.bounds_right_tsil;
        this.start_moving_left = false;
    }

    @Override
    public void update_enemy() {
        Log.i("BirdEnemy", String.valueOf(this.x_pos + "," + this.y_pos));
        this.x_change = 0f;
        this.y_change = 0f;
        float old_y_pos = this.y_pos;
        float old_x_pos = this.x_pos;

        if (this.player_jumping) {
            this.y_pos += (velocity.getY() + 1.5f);
        } else {
            this.y_pos += velocity.getY();
        }
        this.x_pos += velocity.getX();

        float new_y_pos = this.y_pos;
        float new_x_pos = this.x_pos;
        this.y_change = (new_y_pos - old_y_pos);
        this.x_change = (new_x_pos - old_x_pos);

        Log.i("BirdEnemy", String.valueOf(this.x_pos + "," + this.y_pos));
        Log.i("BirdEnemy", String.valueOf(this.x_change+","+this.y_change));

        this.update_bounds();
        this.y_height_thresh_change = 0f;

        // Handle updating of x velocity
        if (this.velocity.getX() == 4f) {
            this.start_moving_left = true;
        } else if (this.velocity.getX() == -4f) {
            this.start_moving_left = false;
        }
        if (this.start_moving_left) {
            float updated_x1 = this.velocity.getX() - 0.25f;
            this.velocity.set(updated_x1, this.velocity.getY());
        } else if (!this.start_moving_left) {
            float updated_x2 = this.velocity.getX() + 0.25f;
            this.velocity.set(updated_x2, this.velocity.getY());
        }

        // Update bounds_tsil based on image blitted
        if (this.velocity.getX() >= 0) {
            this.bounds_tsil = this.bounds_right_tsil;
            // BirdEnemy is moving left
        } else {
            this.bounds_tsil = this.bounds_left_tsil;
        }
    }

    @Override
    public void update_bounds() {
        Log.i("BirdEnemyHeightY", String.valueOf(this.y_height_thresh_change));
        for (int i=0; i < this.bounds_left_tsil.size(); i++) {
            Rectangle curr_rect;
            curr_rect = (Rectangle) this.bounds_left_tsil.get(i);
            Log.i("BirdEnemyUpdateA1", String.valueOf(curr_rect.getLowerLeft().getX()+","+
                    curr_rect.getLowerLeft().getY()+"..."+curr_rect.width+","+curr_rect.height));
            this.bounds_left_tsil.get(i).setLowerLeft(curr_rect.getLowerLeft().getX() + this.x_change,
                    curr_rect.getLowerLeft().getY() + this.y_change + this.y_height_thresh_change);
            Log.i("BirdEnemyUpdateA2", String.valueOf(curr_rect.getLowerLeft().getX() + "," +
                    curr_rect.getLowerLeft().getY() + "..." + curr_rect.width + "," + curr_rect.height));
        }
        for (int i=0; i < this.bounds_right_tsil.size(); i++) {
            Rectangle curr_rect;
            curr_rect = (Rectangle) this.bounds_right_tsil.get(i);
            Log.i("BirdEnemyUpdateB1", String.valueOf(curr_rect.getLowerLeft().getX()+","+
            curr_rect.getLowerLeft().getY()+"..."+curr_rect.width+","+curr_rect.height));
            this.bounds_right_tsil.get(i).setLowerLeft(curr_rect.getLowerLeft().getX() + this.x_change,
                    curr_rect.getLowerLeft().getY() + this.y_change + this.y_height_thresh_change);
            Log.i("BirdEnemyUpdateB2", String.valueOf(curr_rect.getLowerLeft().getX() + "," +
                    curr_rect.getLowerLeft().getY() + "..." + curr_rect.width + "," + curr_rect.height));
        }
    }

    public String getImageName() {
        // BirdEnemy is moving right
        if (this.velocity.getX() >= 0) {
            return "BirdEnemyhighres_reverse.png";
            // BirdEnemy is moving left
        } else {
            return "BirdEnemyhighres.png";
        }
    }
}
