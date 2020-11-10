package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

public class Canvas_creation  extends JFrame {
    Canvas c;
    ArrayList<Position> position;
    boolean up_press,down_press,left_press,right_press;
    boolean collision;
    JFrame f1;
    int food_x,food_y;
    boolean food;
    private int canvas_size=500;
    int score;
    JLabel score_dis;
    boolean agent_use;
    Canvas_creation() throws InterruptedException {
        score=0;
        agent_use=false;
        food=false;
        up_press=false;
        down_press=false;
        left_press=false;
        right_press=false;
        collision=false;
        position=new ArrayList<>();
        position.add(new Position(160,160));
        paint_canvas();
        Create_Frame();
        while (!collision){
            try {
                if(agent_use){
                    Thread.sleep(50);
                }else{
                    Thread.sleep(100);
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                if(right_press){
                    position.get(0).init_swap_pre();
                    position.get(0).setX_init(position.get(0).getX_init()+20);
                }else if(left_press) {
                    position.get(0).init_swap_pre();
                    position.get(0).setX_init(position.get(0).getX_init() - 20);
                }else if(up_press){
                    position.get(0).init_swap_pre();
                    position.get(0).setY_init(position.get(0).getY_init() - 20);
                }else if(down_press){
                    position.get(0).init_swap_pre();
                    position.get(0).setY_init(position.get(0).getY_init() + 20);
                }
                if(Game_over()){
                    collision=true;
                    if(right_press){
                        position.get(0).setX_init(position.get(0).getX_init() - 20);
                    }else if(left_press){
                        position.get(0).setX_init(position.get(0).getX_init() + 20);
                    }else if(up_press) {
                        position.get(0).setY_init(position.get(0).getY_init() + 20);
                    }else{
                        position.get(0).setY_init(position.get(0).getY_init() - 20);
                    }
                    c.repaint();
                    score_dis.setText("score = "+score);
                  ///  Thread.sleep(5000);
                   // f1.dispose();
                    break;
                } else if(position.get(0).getX_init()==food_x && position.get(0).getY_init()==food_y)  {//eating food
                    food=false;
                    score++;
                    score_dis.setText("SCORE: "+score);
                    position.add(new Position(position.get(position.size()-1).getX_pre(),position.get(position.size()-1).getY_pre()));//increment body size
                }
                for (int i = 1; i < position.size(); i++) {//body swap location with each other
                    position.get(i).init_swap_pre();
                    position.get(i).setX_init(position.get(i - 1).getX_pre());
                    position.get(i).setY_init(position.get(i - 1).getY_pre());
                }
                c.repaint();
        }
    }
    public boolean Game_over(){
        boolean game_over=false;
        for(int i=1;i<position.size();i++){
            if(position.get(0).getY_init()==position.get(i).getY_init() && position.get(0).getX_init()==position.get(i).getX_init()){//COLLISION WITH BODY
                game_over=true;
                break;
            }
        }
        if(position.get(0).getY_init()<0||position.get(0).getX_init()<0||position.get(0).getY_init()>canvas_size||position.get(0).getX_init()>canvas_size){//GOING OUTSIDE CANVAS
            game_over=true;
        }
        return game_over;
    }
    public void paint_canvas(){
        c=new Canvas(){
            @Override
            public void paint(Graphics g) {
                for(int i=0;i<canvas_size+20;i+=20){//boarder
                    g.setColor(Color.WHITE);
                    g.drawOval(i,0,20,20);
                    g.drawOval(i,canvas_size,20,20);
                    g.drawOval(0,i,20,20);
                    g.drawOval(canvas_size,i,20,20);
                }
                for(int i=0;i<position.size();i++){//rendering canvas
                    if(i==0){
                        g.setColor(Color.RED);
                    }else {
                        g.setColor(Color.BLUE);
                    }
                    g.fillRect(position.get(i).getX_init(),position.get(i).getY_init(),20,20);
                }
                if(!food){//generating food
                    Random r=new Random();
                    food_x=r.nextInt()%(canvas_size);
                    food_y=r.nextInt()%(canvas_size);
                    if(food_y<0){
                        food_y*=-1;
                    }
                    if(food_x<0){
                        food_x*=-1;
                    }
                    if(food_x%20!=0){
                        food_x-=(food_x%20);
                    }
                    if(food_y%20!=0){
                        food_y-=(food_y%20);
                    }
                    food=true;
                   }
                if(collision){
                    g.setColor(Color.RED);
                    Font f = new Font("Dialog", Font.BOLD, 40);
                    g.setFont(f);
                    g.drawString("GAME OVER",canvas_size/2,canvas_size/2);
                }
                g.setColor(Color.green);
                g.fillOval(food_x,food_y,20,20);
                }
        };
    }
    public  void Create_Frame(){
        c.setBackground(Color.darkGray);
        c.setSize(canvas_size,canvas_size);
        f1=new JFrame();
        score_dis=new JLabel("SCORE: ");
        KeyBoard key=new KeyBoard();
        c.addKeyListener(key);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        JPanel p1=new JPanel(new BorderLayout());
        p1.setPreferredSize(new Dimension(canvas_size,canvas_size));
        p1.add(c,BorderLayout.CENTER);
        score_dis.setForeground(Color.BLACK);
        f1.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f1.setLayout(new BorderLayout());
        f1.setSize(new Dimension(1000,1000));
        f1.add(p1,BorderLayout.CENTER);
        f1.add(score_dis,BorderLayout.BEFORE_FIRST_LINE);
        f1.setVisible(true);
    }
    public boolean setUp_press() {
        if(!down_press){
            this.up_press=true;
            this.down_press=false;
            this.right_press=false;
            this.left_press=false;
            return true;
        }
        return false;
    }
    public boolean setDown_press() {
        if(!up_press){
            this.up_press=false;
            this.down_press=true;
            this.right_press=false;
            this.left_press=false;
            return true;
        }
        return false;
    }
    public boolean setLeft_press() {
        if(!right_press){
            this.up_press=false;
            this.down_press=false;
            this.right_press=false;
            this.left_press=true;
            return true;
        }
        return false;
    }
    public boolean setRight_press() {
        if(!left_press){
            this.up_press=false;
            this.down_press=false;
            this.right_press=true;
            this.left_press=false;
            return true;
        }
        return false;
    }

    private class KeyBoard implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_ENTER){
              Thread t1=new Thread(new Runnable() {
                  @Override
                  public void run() {
                      agent_use=true;
                      setDown_press();
                      String movement="";
                      while(!collision){
                          if(!food){
                              try {
                                  Thread.sleep(5);
                              } catch (InterruptedException interruptedException) {
                                  interruptedException.printStackTrace();
                              }
                          }
                              int direction=-1;
                              if(right_press){
                                  direction=0;
                              }else if(left_press){
                                  direction=1;
                              }else if(up_press){
                                  direction=2;
                              }else if(down_press){
                                  direction=3;
                              }
                              movement=goal_base_agent.utility(food_x,food_y,position,canvas_size,direction);
                              if(movement.equals("0")){
                                  setRight_press();
                              }else if(movement.equals("1")){
                                    setLeft_press();
                              }else if(movement.equals("2")){
                                    setUp_press();
                              }else if(movement.equals("3")){
                                    setDown_press();
                              }
                          try {
                              Thread.sleep(10);
                          } catch (InterruptedException interruptedException) {
                              interruptedException.printStackTrace();
                          }
                    }
                  }
              });
              t1.start();
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_UP && !down_press){
                    up_press=true;
                    down_press=false;
                    right_press=false;
                    left_press=false;
            }else if(e.getKeyCode()==KeyEvent.VK_DOWN && !up_press){
                    up_press=false;
                    down_press=true;
                    right_press=false;
                    left_press=false;
            }else if(e.getKeyCode()==KeyEvent.VK_RIGHT && !left_press){
                    up_press=false;
                    down_press=false;
                    right_press=true;
                    left_press=false;
            }else if(e.getKeyCode()==KeyEvent.VK_LEFT && !right_press){
                    up_press=false;
                    down_press=false;
                    right_press=false;
                    left_press=true;
            }
        }
    }
}
/**Done by Sobrattee Mohammad Muzzammil**/
